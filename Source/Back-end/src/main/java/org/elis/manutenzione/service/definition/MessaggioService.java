package org.elis.manutenzione.service.definition;

import org.elis.manutenzione.model.entity.Messaggio;

/**
 * Interfaccia del servizio per la gestione dei messaggi.
 * Questa interfaccia definisce i metodi per le operazioni relative ai messaggi,
 * come l'aggiunta, il recupero e l'eliminazione.
 */
public interface MessaggioService {


	/**
	 * Aggiunge un nuovo messaggio.
	 *
	 * @param m L'oggetto Messaggio da aggiungere.
	 */
	void aggiungiMessaggio(Messaggio m);

	/**
	 * Recupera un messaggio specifico dato il suo ID.
	 *
	 * @param id L'ID del messaggio da recuperare.
	 * @return L'oggetto Messaggio corrispondente all'ID fornito.
	 */
	Messaggio getMessaggioById(Long id);

	/**
	 * Elimina un messaggio specifico.
	 *
	 * @param m L'oggetto Messaggio da eliminare.
	 * @return true se l'eliminazione ha avuto successo, false altrimenti.
	 */
	boolean eliminaMessaggio(Messaggio m);
}