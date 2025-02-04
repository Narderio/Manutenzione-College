package org.elis.manutenzione.dto.response;

import lombok.Data;
import org.elis.manutenzione.model._enum.TipoMessaggio;
import org.elis.manutenzione.model._enum.TipoMessaggioInviato;

/**
 * Rappresenta un messaggio personalizzato.
 */
@Data
public class CustomMessage {
    /**
     * Contenuto del messaggio.
     */
    private String content;
    /**
     * Mittente del messaggio.
     */
    private String sender;
    /**
     * ID del messaggio.
     */
    private long idMessage;
    /**
     * Tipo del messaggio.
     */
    private TipoMessaggio type;
    /**
     * ID del messaggio di riferimento.
     */
    private long idRiferimentoMessaggio;

    /**
     * Tipo del messaggio inviato. Immagine o testo.
     */
    private TipoMessaggioInviato tipoMessaggio;


}