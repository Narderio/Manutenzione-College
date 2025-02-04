package org.elis.manutenzione.dto.request;

import lombok.Data;

/**
 * DTO per la richiesta di modifica di una manutenzione.
 * Contiene l'ID della manutenzione, la nuova descrizione e il nuovo nome.
 */
@Data
public class ModificaManutenzioneRequest {

    /**
     * L'ID della manutenzione da modificare.
     */
    private long idManutenzione;

    /**
     * La nuova descrizione della manutenzione.
     */
    private String descrizione;

    /**
     * Il nuovo nome della manutenzione.
     */
    private String nome;

    /**
     * L'immagine allegata alla manutenzione.
     */
    private String immagine;
}