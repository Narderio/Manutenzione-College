package org.elis.manutenzione.dto.response;

import lombok.Data;

/**
 * DTO che rappresenta la risposta alla richiesta di ottenere la lista delle stanze.
 */
@Data
public class GetStanzeResponse {

    /**
     * L'ID della stanza.
     */
    private long id;

    /**
     * Il nome della stanza.
     */
    private String nome;

    /**
     * Il nucleo a cui appartiene la stanza.
     */
    private String nucleo;

    /**
     * Il piano in cui si trova la stanza.
     */
    private int piano;

    /**
     * La capienza massima della stanza.
     */
    private int capienza;

    /**
     * Il numero di residenti attualmente presenti nella stanza.
     */
    private int residenti;
}