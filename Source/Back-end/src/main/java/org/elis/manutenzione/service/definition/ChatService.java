package org.elis.manutenzione.service.definition;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.elis.manutenzione.dto.request.EliminaMessaggioDTO;
import org.elis.manutenzione.dto.request.InviaMessaggioDTO;
import org.elis.manutenzione.dto.request.ModificaMessaggioDTO;
import org.elis.manutenzione.model.entity.Chat;
import org.elis.manutenzione.model.entity.Messaggio;
import org.elis.manutenzione.model.entity.Utente;

import java.util.List;

/**
 * Interfaccia del servizio per la gestione delle chat.
 * Questa interfaccia definisce i metodi per le operazioni relative alle chat,
 * come il recupero, l'invio, l'eliminazione e la modifica dei messaggi.
 */
public interface ChatService {


	/**
	 * Recupera tutte le chat associate a un determinato utente, identificato dal suo username.
	 *
	 * @param username L'username dell'utente per cui recuperare le chat.
	 * @return Una lista di oggetti Chat, o una lista vuota se non ci sono chat.
	 */
	List<Chat> getAllByUsername(String username);


	/**
	 * Recupera una specifica chat basata sugli username di due utenti.
	 *
	 * @param username       L'username del primo utente.
	 * @param secondoUsername L'username del secondo utente.
	 * @return Un oggetto Chat, o null se la chat non viene trovata.
	 */
	Chat getByUsernameAndAltroNome(String username, String secondoUsername);


	/**
	 * Salva una nuova chat nel database o aggiorna una esistente.
	 *
	 * @param c L'oggetto Chat da salvare o aggiornare.
	 * @return L'oggetto Chat salvato o aggiornato.
	 */
	Chat salva(Chat c);

	/**
	 * Invia un nuovo messaggio.
	 *
	 * @param u1      L'utente mittente del messaggio.
	 * @param u2      L'utente destinatario del messaggio.
	 * @param request Il DTO contenente i dati del messaggio da inviare.
	 * @throws JsonProcessingException Se si verifica un errore durante la serializzazione del messaggio in JSON.
	 */
	void inviaMessaggio(Utente u1, Utente u2, InviaMessaggioDTO request) throws JsonProcessingException;

	/**
	 * Elimina un messaggio.
	 *
	 * @param m       Il messaggio da eliminare.
	 * @param u       L'utente che richiede l'eliminazione del messaggio.
	 * @param request Il DTO contenente i dati per l'eliminazione del messaggio.
	 * @throws JsonProcessingException Se si verifica un errore durante la serializzazione del messaggio in JSON.
	 */
	void eliminaMessaggio(Messaggio m, Utente u, @Valid EliminaMessaggioDTO request) throws JsonProcessingException;

	/**
	 * Modifica un messaggio esistente.
	 *
	 * @param m       Il messaggio da modificare.
	 * @param u       L'utente che richiede la modifica del messaggio.
	 * @param request Il DTO contenente i dati per la modifica del messaggio.
	 * @throws JsonProcessingException Se si verifica un errore durante la serializzazione del messaggio in JSON.
	 */
	void modificaMessaggio(Messaggio m, Utente u, @Valid ModificaMessaggioDTO request) throws JsonProcessingException;
}