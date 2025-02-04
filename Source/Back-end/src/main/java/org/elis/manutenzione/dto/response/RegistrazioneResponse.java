package org.elis.manutenzione.dto.response;

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO che rappresenta la risposta alla richiesta di registrazione di un utente.
 */
@Data
public class RegistrazioneResponse {

    /**
     * L'indirizzo email dell'utente registrato.
     */
    private String email;

    /**
     * Il nome dell'utente registrato.
     */
    private String nome;

    /**
     * Il cognome dell'utente registrato.
     */
    private String cognome;

    /**
     * La data di nascita dell'utente registrato.
     */
    private LocalDate dataDiNascita;
}