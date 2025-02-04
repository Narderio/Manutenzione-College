package org.elis.manutenzione.dto.response;

import lombok.Data;

import java.time.LocalDate;

/**
 * DTO che rappresenta la risposta per la richiesta di ottenere i dettagli dei supervisori.
 */
@Data
public class GetSupervisoriResponse {

    /**
     * L'indirizzo email del supervisore.
     */
    private String email;

    /**
     * Il nome del supervisore.
     */
    private String nome;

    /**
     * Il cognome del supervisore.
     */
    private String cognome;

    /**
     * La data di nascita del supervisore.
     */
    private LocalDate dataDiNascita;
}