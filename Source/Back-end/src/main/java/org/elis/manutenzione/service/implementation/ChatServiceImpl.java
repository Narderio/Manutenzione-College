package org.elis.manutenzione.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.elis.manutenzione.builderPattern.ChatBuilder;
import org.elis.manutenzione.builderPattern.MessaggioBuilder;
import org.elis.manutenzione.dto.request.EliminaMessaggioDTO;
import org.elis.manutenzione.dto.request.InviaMessaggioDTO;
import org.elis.manutenzione.dto.request.ModificaMessaggioDTO;
import org.elis.manutenzione.dto.response.CustomMessage;
import org.elis.manutenzione.dto.response.GetMessaggioResponse;
import org.elis.manutenzione.handler.BadRequestException;
import org.elis.manutenzione.handler.ForbiddenException;
import org.elis.manutenzione.handler.NoContentException;
import org.elis.manutenzione.mapper.MessaggioMapper;
import org.elis.manutenzione.model._enum.TipoMessaggio;
import org.elis.manutenzione.model.entity.*;
import org.elis.manutenzione.repository.ChatRepository;
import org.elis.manutenzione.service.definition.ChatService;
import org.elis.manutenzione.service.definition.MessaggioService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementazione del servizio per la gestione delle chat.
 */
@Service
public class ChatServiceImpl implements ChatService {

	private final ChatRepository repo;
	private final MessaggioService messaggioService;
	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;
	private final MessaggioMapper messaggioMapper;

	/**
	 * Costruttore della classe.
	 *
	 * @param repo             Il repository delle chat.
	 * @param messaggioService Il servizio per la gestione dei messaggi.
	 * @param rabbitTemplate   Il template per l'invio di messaggi tramite RabbitMQ.
	 * @param objectMapper     L'oggetto per la serializzazione/deserializzazione JSON.
	 * @param messaggioMapper  Il mapper per la conversione tra entità Messaggio e DTO.
	 */
	public ChatServiceImpl(ChatRepository repo, MessaggioService messaggioService, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, MessaggioMapper messaggioMapper) {
		this.repo = repo;
		this.messaggioService = messaggioService;
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
		this.messaggioMapper = messaggioMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Chat> getAllByUsername(String username) {
		return repo.findAllByUsername(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Chat getByUsernameAndAltroNome(String username, String secondoUsername) {
		return repo.findAllByUsername(username, secondoUsername).orElse(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Chat salva(Chat c) {
		return repo.save(c);
	}

	/**
	 * Implementazione del metodo per l'invio di un messaggio.
	 *
	 * @param u1      L'utente mittente del messaggio.
	 * @param u2      L'utente destinatario del messaggio.
	 * @param request Il DTO contenente i dati del messaggio da inviare.
	 * @throws JsonProcessingException Se si verifica un errore durante la serializzazione del messaggio in JSON.
	 * @throws NoContentException       Se l'utente destinatario non viene trovato.
	 * @throws BadRequestException     Se l'utente mittente tenta di inviare un messaggio a se stesso.
	 * @throws ForbiddenException      Se l'utente mittente non ha l'autorizzazione per inviare messaggi all'utente destinatario.
	 */
	@Override
	public void inviaMessaggio(Utente u1, Utente u2, InviaMessaggioDTO request) throws JsonProcessingException {
		if (u2 == null)
			throw new NoContentException("Destinatario non trovato");
		if (u1.getEmail().equalsIgnoreCase(u2.getEmail()))
			throw new BadRequestException("Non puoi inviare messaggi a te stesso");
		if (u1 instanceof Residente && (u2 instanceof Admin || u2 instanceof Manutentore))
			throw new ForbiddenException("Non puoi inviare messaggi a questo utente");
		if (u1 instanceof Manutentore && (u2 instanceof Residente || u2 instanceof Admin))
			throw new ForbiddenException("Non puoi inviare messaggi a questo utente");
		if (u1 instanceof Supervisore && u2 instanceof Admin)
			throw new ForbiddenException("Non puoi inviare messaggi a questo utente");
		Chat c = getByUsernameAndAltroNome(u1.getUsername(), u2.getUsername());
		if (c == null) {
			c = new ChatBuilder()
					.utenteUno(u1)
					.utenteDue(u2)
					.build();
			c = salva(c);
		}

		Messaggio m = new MessaggioBuilder()
				.testo(request.getTesto())
				.primoUtente(c.getUtenteUno().getUsername().equals(u1.getUsername()))
				.chat(c)
				.idRiferimentoMessaggio(request.getIdRiferimentoMessaggio())
				.tipoMessaggio(request.getTipoMessaggio())
				.build();

		messaggioService.aggiungiMessaggio(m);
		CustomMessage customMessage = new CustomMessage();
		customMessage.setContent(m.getTesto());
		customMessage.setSender(u1.getEmail());
		customMessage.setType(TipoMessaggio.INVIO);
		customMessage.setIdRiferimentoMessaggio(request.getIdRiferimentoMessaggio());
		customMessage.setTipoMessaggio(request.getTipoMessaggio());
		rabbitTemplate.convertAndSend("TopicTestExchange", u2.getEmail(), objectMapper.writeValueAsString(customMessage));
	}

	/**
	 * Implementazione del metodo per l'eliminazione di un messaggio.
	 *
	 * @param m       Il messaggio da eliminare.
	 * @param u       L'utente che richiede l'eliminazione.
	 * @param request Il DTO contenente i dati per l'eliminazione del messaggio.
	 * @throws JsonProcessingException Se si verifica un errore durante la serializzazione del messaggio in JSON.
	 * @throws NoContentException       Se il messaggio da eliminare non viene trovato.
	 * @throws ForbiddenException      Se l'utente che richiede l'eliminazione non è l'autore del messaggio.
	 */
	@Override
	public void eliminaMessaggio(Messaggio m, Utente u, @Valid EliminaMessaggioDTO request) throws JsonProcessingException {
		if (m == null) {
			throw new NoContentException("Messaggio non trovato");
		}
		GetMessaggioResponse mr = messaggioMapper.toGetMessaggioDTO(m);
		if ((mr.getMittente().equals(u.getEmail())))
			messaggioService.eliminaMessaggio(m);
		else
			throw new ForbiddenException("Non puoi eliminare il messaggio di un altro utente");
		Chat chat = m.getChat();
		Utente u2;
		if (chat.getUtenteUno() == u)
			u2 = chat.getUtenteDue();
		else
			u2 = chat.getUtenteUno();

		CustomMessage customMessage = new CustomMessage();
		customMessage.setIdMessage(m.getId());
		customMessage.setType(TipoMessaggio.ELIMINAZIONE);
		rabbitTemplate.convertAndSend("TopicTestExchange", u2.getEmail(), objectMapper.writeValueAsString(customMessage));
	}

	/**
	 * Implementazione del metodo per la modifica di un messaggio.
	 *
	 * @param m       Il messaggio da modificare.
	 * @param u       L'utente che richiede la modifica.
	 * @param request Il DTO contenente i dati per la modifica del messaggio.
	 * @throws JsonProcessingException Se si verifica un errore durante la serializzazione del messaggio in JSON.
	 * @throws NoContentException       Se il messaggio da modificare non viene trovato.
	 * @throws ForbiddenException      Se l'utente che richiede la modifica non è l'autore del messaggio.
	 */
	public void modificaMessaggio(Messaggio m, Utente u, @Valid ModificaMessaggioDTO request) throws JsonProcessingException {
		if (m == null) {
			throw new NoContentException("Messaggio non trovato");
		}
		GetMessaggioResponse mr = messaggioMapper.toGetMessaggioDTO(m);
		if ((mr.getMittente().equals(u.getEmail()))) {
			m.setTesto(request.getTesto());
			messaggioService.aggiungiMessaggio(m);
		} else
			throw new ForbiddenException("Non puoi modificare il messaggio di un altro utente");

		Chat chat = m.getChat();
		Utente u2;
		if (chat.getUtenteUno() == u)
			u2 = chat.getUtenteDue();
		else
			u2 = chat.getUtenteUno();

		CustomMessage customMessage = new CustomMessage();
		customMessage.setIdMessage(m.getId());
		customMessage.setType(TipoMessaggio.MODIFICA);

		rabbitTemplate.convertAndSend("TopicTestExchange", u2.getEmail(), objectMapper.writeValueAsString(customMessage));

	}


}