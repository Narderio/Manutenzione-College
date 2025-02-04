package org.elis.manutenzione.dto.response;

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO che rappresenta la risposta alla richiesta di accettazione di una manutenzione.
 */
@Data
public class AccettaManutenzioneResponse {

    /**
     * L'ID della manutenzione accettata.
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
     * La priorità della manutenzione.
     */
    private int priorita;

    /**
     * La data prevista per l'esecuzione della manutenzione.
     */
    private LocalDate dataPrevista;

    /**
     * Il nome della manutenzione.
     */
    private String nome;
}
