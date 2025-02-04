package org.elis.manutenzione.dto.response;

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO che rappresenta la risposta alla richiesta di filtraggio delle manutenzioni.
 */
@Data
public class FiltraManutenzioneResponse {

    /**
     * L'ID della manutenzione.
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
     * La data di richiesta della manutenzione.
     */
    private LocalDate dataRichiesta;

    /**
     * L'email dell'utente che ha richiesto la manutenzione.
     */
    private String richiedente;

    /**
     * L'email del manutentore assegnato alla manutenzione.
     */
    private String manutentore;

    /**
     * La priorità della manutenzione.
     */
    private int priorita;
}