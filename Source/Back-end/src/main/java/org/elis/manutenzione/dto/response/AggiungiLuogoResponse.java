package org.elis.manutenzione.dto.response;

import lombok.Data;

/**
 * DTO che rappresenta la risposta alla richiesta di aggiunta di un luogo.
 */
@Data
public class AggiungiLuogoResponse {

    /**
     * L'ID del luogo aggiunto.
     */
    private long id;

    /**
     * Il nome del luogo.
     */
    private String nome;

    /**
     * Il tipo di luogo (es. Bagno, Stanza, Luogo Comune).
     */
    private String tipo;

    /**
     * Il nucleo a cui appartiene il luogo.
     */
    private String nucleo;

    /**
     * La capienza del luogo.
     */
    private int capienza;
}