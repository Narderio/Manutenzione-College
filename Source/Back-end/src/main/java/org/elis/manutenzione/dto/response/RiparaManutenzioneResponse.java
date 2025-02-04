package org.elis.manutenzione.dto.response;

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO che rappresenta la risposta alla richiesta di riparazione di una manutenzione.
 */
@Data
public class RiparaManutenzioneResponse {

    /**
     * L'ID della manutenzione riparata.
     */
    private long id;

    /**
     * La descrizione della manutenzione.
     */
    private String descrizione;

    /**
     * Il nome del luogo in cui è stata effettuata la manutenzione.
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

    /**
     * La data prevista per la riparazione della manutenzione.
     */
    private LocalDate dataPrevista;

    /**
     * La data effettiva di riparazione della manutenzione.
     */
    private LocalDate dataRiparazione;
}