package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.elis.manutenzione.model._enum.Ruolo;

import java.time.LocalDate;

/**
 * DTO che rappresenta la richiesta di registrazione di un nuovo utente.
 */
@Data
public class RegistrazioneRequest {

    /**
     * Il nome dell'utente.
     * Non può essere nullo o vuoto.
     */
    @NotBlank(message = "Il campo 'nome' non può essere nullo o vuoto")
    private String nome;

    /**
     * Il cognome dell'utente.
     * Non può essere nullo o vuoto.
     */
    @NotBlank(message = "Il campo 'cognome' non può essere nullo o vuoto")
    private String cognome;

    /**
     * L'indirizzo email dell'utente.
     * Non può essere nullo o vuoto e deve essere un indirizzo email valido.
     */
    @NotBlank(message = "Il campo 'email' non può essere nullo o vuoto")
    @Email(message = "Il campo 'email' deve essere un indirizzo email valido")
    private String email;

    /**
     * La data di nascita dell'utente.
     * Non può essere nullo e deve essere una data passata.
     */
    @NotNull(message = "Il campo 'dataDiNascita' non può essere nullo")
    @Past(message = "Il campo 'dataDiNascita' deve essere una data passata")
    private LocalDate dataDiNascita;

    /**
     * Il ruolo dell'utente.
     * Non può essere nullo.
     */
    @NotNull(message = "Il campo 'ruolo' non può essere nullo")
    private Ruolo ruolo;

    /**
     * Indica se l'utente è bloccato o meno.
     */
    private boolean bloccato;

    /**
     * Il tipo di manutentore, se applicabile.
     */
    private String tipoManutentore;
}