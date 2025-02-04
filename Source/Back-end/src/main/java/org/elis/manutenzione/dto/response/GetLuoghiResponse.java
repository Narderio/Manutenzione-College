package org.elis.manutenzione.dto.response;

import lombok.Data;
import org.elis.manutenzione.model._enum.Tipo;

/**
 * DTO che rappresenta la risposta alla richiesta di ottenere la lista dei luoghi.
 */
@Data
public class GetLuoghiResponse {

    /**
     * L'ID del luogo.
     */
    private long id;

    /**
     * Il nome del luogo.
     */
    private String nome;

    /**
     * Il nucleo a cui appartiene il luogo.
     */
    private String nucleo;

    /**
     * Il tipo di luogo (es. Bagno, Stanza, Luogo Comune).
     */
    private Tipo tipo;

    /**
     * Il piano in cui si trova il luogo.
     */
    private int piano;
}