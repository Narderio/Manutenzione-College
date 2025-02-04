package org.elis.manutenzione.dto.response;

import lombok.Data;

/**
 * DTO per la risposta contenente i dati di un messaggio.
 * Include l'ID, il mittente e il testo del messaggio.
 */
@Data
public class GetMessaggioResponse {
    /**
     * L'ID univoco del messaggio.
     */
    private Long id;
    /**
     * L'email del mittente del messaggio.
     */
    private String mittente;
    /**
     * Il testo del messaggio.
     */
    private String testo;
}