package org.elis.manutenzione.dto.response;

import lombok.Data;
import org.elis.manutenzione.model._enum.Ruolo;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO che rappresenta la risposta alla richiesta di login di un utente.
 */
@Data
public class LoginResponse {

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
     * La data di nascita dell'utente.
     */
    private LocalDate dataDiNascita;

    /**
     * Il ruolo dell'utente.
     */
    private Ruolo ruolo;

    /**
     * Luoghi associati all'utente.
     */
    private List<String> luoghi;

    /**
     * Indica se l'utente vuole ricevere le email facoltative
     */
    private boolean emailBloccate;

    /**
     * Il token di autenticazione dell'utente.
     */
    private String token;
}