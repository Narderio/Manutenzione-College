package org.elis.manutenzione.dto.response;

import lombok.Data;

/**
 * DTO che rappresenta la risposta alla richiesta di rifiuto di una manutenzione.
 */
@Data
public class RifiutaManutenzioneResponse {

    /**
     * L'ID della manutenzione rifiutata.
     */
    private long id;

    /**
     * La descrizione della manutenzione.
     */
    private String descrizione;

    /**
     * Il nome del luogo in cui si sarebbe dovuta effettuare la manutenzione.
     */
    private String luogo;

    /**
     * Il nucleo a cui appartiene il luogo.
     */
    private String nucleo;

    /**
     * La priorità della manutenzione.
     */
    private int priorita;
}