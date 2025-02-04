package org.elis.manutenzione.dto.response;

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO che rappresenta la risposta alla richiesta di una nuova manutenzione.
 */
@Data
public class RichiediManutenzioneResponse {

    /**
     * L'ID della manutenzione richiesta.
     */
    private long id;

    /**
     * La descrizione della manutenzione.
     */
    private String descrizione;

    /**
     * Il nome del luogo in cui si effettua la manutenzione.
     */
    private String luogo;

    /**
     * Il nucleo a cui appartiene il luogo.
     */
    private String nucleo;

    /**
     * La data in cui è stata richiesta la manutenzione.
     */
    private LocalDate dataRichiesta;

    /**
     * Il nome della manutenzione.
     */
    private String nome;
}