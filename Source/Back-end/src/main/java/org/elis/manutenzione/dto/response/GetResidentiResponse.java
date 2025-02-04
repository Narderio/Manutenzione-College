package org.elis.manutenzione.dto.response;

import lombok.Data;

import java.time.LocalDate;

/**
 * DTO che rappresenta la risposta per la richiesta di ottenere i dati dei residenti.
 *
 */
@Data
public class GetResidentiResponse {

    /**
     * Il nome del residente.
     */
    private String nome;

    /**
     * Il cognome del residente.
     */
    private String cognome;

    /**
     * Il nome della stanza assegnata al residente.
     */
    private String stanza;

    /**
     * L'indirizzo email del residente.
     */
    private String email;

    /**
     * La data di nascita del residente.
     */
    private LocalDate dataDiNascita;
}