package org.elis.manutenzione.dto.response;

import lombok.Data;
import org.elis.manutenzione.model._enum.Ruolo;

import java.time.LocalDate;

/**
 * DTO che rappresenta la risposta per la richiesta di ottenere i dettagli degli utenti.
 */
@Data
public class GetUtentiResponse {

    /**
     * L'indirizzo email dell'utente.
     */
    private String email;

    /**
     * Il nome dell'utente.
     */
    private String nome;

    /**
     * Il cognome dell'utente.
     */
    private String cognome;

    /**
     * Il ruolo dell'utente.
     */
    private Ruolo ruolo;

    /**
     * La data di nascita dell'utente.
     */
    private LocalDate dataDiNascita;
}