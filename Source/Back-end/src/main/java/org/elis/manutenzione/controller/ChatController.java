package org.elis.manutenzione.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.elis.manutenzione.dto.request.EliminaMessaggioDTO;
import org.elis.manutenzione.dto.request.ModificaMessaggioDTO;
import org.elis.manutenzione.dto.request.InviaMessaggioDTO;
import org.elis.manutenzione.dto.response.MessaggioDTO;
import org.elis.manutenzione.handler.NoContentException;
import org.elis.manutenzione.mapper.ChatMapper;
import org.elis.manutenzione.model.entity.Chat;
import org.elis.manutenzione.model.entity.Messaggio;
import org.elis.manutenzione.model.entity.Utente;
import org.elis.manutenzione.service.definition.ChatService;
import org.elis.manutenzione.service.definition.MessaggioService;
import org.elis.manutenzione.service.definition.UtenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST per la gestione delle chat e dei messaggi.
 * Questo controller espone endpoint per l'invio, la ricezione, l'eliminazione e la modifica dei messaggi,
 * nonché per la visualizzazione delle chat tra utenti.
 * Utilizza i servizi {@link UtenteService}, {@link ChatService} e {@link MessaggioService} per la logica di business.
 */
@RestController
public class ChatController {

    private final UtenteService utenteService;
    private final ChatService chatService;
    private final MessaggioService messaggioService;
    private final ChatMapper chatMapper;

    /**
     * Costruttore della classe.
     * Inizializza le dipendenze necessarie per la gestione delle chat.
     *
     * @param utenteService   Il servizio per la gestione degli utenti, per operazioni relative agli utenti.
     * @param chatService     Il servizio per la gestione delle chat, per operazioni relative alle chat.
     * @param messaggioService Il servizio per la gestione dei messaggi, per operazioni relative ai messaggi.
     * @param chatMapper     Il mapper per la conversione tra entità Chat e DTO, per la gestione dei dati.
     */
    public ChatController(UtenteService utenteService, ChatService chatService, MessaggioService messaggioService, ChatMapper chatMapper) {
        this.utenteService = utenteService;
        this.chatService = chatService;
        this.messaggioService = messaggioService;
        this.chatMapper = chatMapper;
    }

    /**
     * Endpoint per l'invio di un messaggio.
     * Riceve i dati del messaggio e l'username del destinatario, recupera gli utenti coinvolti e delega l'invio al servizio.
     *
     * @param request DTO contenente i dati del messaggio da inviare e l'username del destinatario.
     * @param upat    Token di autenticazione dell'utente che sta inviando il messaggio.
     * @return ResponseEntity con status 200 OK in caso di successo.
     * @throws JsonProcessingException Se si verifica un errore durante la serializzazione del messaggio in JSON.
     */
    @PostMapping("/messaggio/invia")
    public ResponseEntity<Void> inviaMessaggio(@Valid @RequestBody InviaMessaggioDTO request, UsernamePasswordAuthenticationToken upat) throws JsonProcessingException {
        Utente u1 = (Utente) upat.getPrincipal();
        u1 = utenteService.getUtenteByEmailAndBloccatoIsFalse(u1.getEmail());
        Utente u2 = utenteService.getUtenteByEmailAndBloccatoIsFalse(request.getUsernameDestinatario());
        chatService.inviaMessaggio(u1, u2, request);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint per la visualizzazione di una chat specifica.
     * Riceve l'username dell'altro utente coinvolto nella chat e restituisce la lista dei messaggi.
     *
     * @param username L'username dell'altro utente nella chat.
     * @param upat     Token di autenticazione dell'utente che sta richiedendo la chat.
     * @return ResponseEntity contenente la lista dei messaggi in formato DTO o status 404 NOT_FOUND se la chat non esiste.
     * @throws NoContentException Se la chat non viene trovata.
     */
    @GetMapping("/chat/{username}")
    public ResponseEntity<List<MessaggioDTO>> getChat(@PathVariable String username, UsernamePasswordAuthenticationToken upat) {
        Utente u = (Utente) upat.getPrincipal();
        Chat c = chatService.getByUsernameAndAltroNome(u.getUsername(), username);
        if (c == null)
            throw new NoContentException("Chat non trovata");
        return ResponseEntity.ok(chatMapper.toMessaggioDTOList(c));
    }

    /**
     * Endpoint per la visualizzazione di tutte le chat di un utente.
     * Restituisce una lista di username degli altri utenti con cui l'utente ha una chat attiva.
     *
     * @param upat Token di autenticazione dell'utente che sta richiedendo la lista di chat.
     * @return ResponseEntity contenente la lista degli username o una lista vuota se non ci sono chat.
     */
    @GetMapping("/chat/getAll")
    public ResponseEntity<List<String>> getChat(UsernamePasswordAuthenticationToken upat) {
        Utente u = (Utente) upat.getPrincipal();
        List<Chat> chat = chatService.getAllByUsername(u.getUsername());
        return ResponseEntity.ok(chatMapper.toUsername(u, chat));
    }

    /**
     * Endpoint per l'eliminazione di un messaggio.
     * Riceve l'id del messaggio da eliminare e delega l'operazione al servizio, previa verifica dell'utente.
     *
     * @param request DTO contenente l'id del messaggio da eliminare.
     * @param upat    Token di autenticazione dell'utente che sta richiedendo l'eliminazione.
     * @return ResponseEntity con status 200 OK in caso di successo.
     * @throws JsonProcessingException Se si verifica un errore durante la serializzazione del messaggio in JSON.
     */
    @PostMapping("/chat/eliminaMessaggio")
    public ResponseEntity<Boolean> eliminaMessaggio(@Valid @RequestBody EliminaMessaggioDTO request, UsernamePasswordAuthenticationToken upat) throws JsonProcessingException {
        Utente u = (Utente) upat.getPrincipal();
        Messaggio m = messaggioService.getMessaggioById(request.getId());
        chatService.eliminaMessaggio(m, u, request);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint per la modifica di un messaggio.
     * Riceve l'id del messaggio da modificare e il nuovo testo, delega l'operazione al servizio, previa verifica dell'utente.
     *
     * @param request DTO contenente l'id del messaggio da modificare e il nuovo testo.
     * @param upat    Token di autenticazione dell'utente che sta richiedendo la modifica.
     * @return ResponseEntity con status 200 OK in caso di successo.
     * @throws JsonProcessingException Se si verifica un errore durante la serializzazione del messaggio in JSON.
     */
    @PatchMapping("/chat/modificaMessaggio")
    public ResponseEntity<Boolean> modificaMessaggio(@Valid @RequestBody ModificaMessaggioDTO request, UsernamePasswordAuthenticationToken upat) throws JsonProcessingException {
        Utente u = (Utente) upat.getPrincipal();
        Messaggio m = messaggioService.getMessaggioById(request.getId());
        chatService.modificaMessaggio(m, u, request);
        return ResponseEntity.ok().build();
    }
}