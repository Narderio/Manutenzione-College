package org.elis.manutenzione.dto.response;

import lombok.Data;

import java.time.LocalDate;

/**
 * DTO che rappresenta la risposta per la richiesta di ottenere i dettagli degli amministratori.
 */
@Data
public class GetAdminResponse {

    /**
     * L'indirizzo email dell'amministratore.
     */
    private String email;

    /**
     * Il nome dell'amministratore.
     */
    private String nome;

    /**
     * Il cognome dell'amministratore.
     */
    private String cognome;

    /**
     * La data di nascita dell'amministratore.
     */
    private LocalDate dataDiNascita;
}