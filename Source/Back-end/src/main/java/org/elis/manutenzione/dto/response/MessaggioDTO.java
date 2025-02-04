package org.elis.manutenzione.dto.response;

import lombok.Data;
import org.elis.manutenzione.model._enum.TipoMessaggioInviato;

/**
 * DTO che rappresenta un messaggio.
 */
@Data
public class MessaggioDTO {

	/**
	 * Identificativo del messaggio.
	 */
	private long id;

	/**
	 * Lo username del mittente del messaggio.
	 */
	private String mittente;

	/**
	 * Il nome del mittente del messaggio.
	 */
	private String nomeMittente;

	/**
	 * Il cognome del mittente del messaggio.
	 */
	private String cognomeMittente;

	/**
	 * Il testo del messaggio.
	 */
	private String testo;

	/**
	 * La data di invio del messaggio.
	 */
	private String data;


	/**
	 * Identificativo del messaggio di riferimento.
	 */
	private long idRiferimentoMessaggio;

	/**
	 * Il tipo del messaggio. Immagine o testo
	 */
	private TipoMessaggioInviato tipoMessaggio;
}