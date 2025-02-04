package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO che rappresenta la richiesta di login di un utente.
 */
@Data
public class LoginRequest {

    /**
     * L'indirizzo email dell'utente.
     * Non può essere nullo o vuoto.
     */
    @NotBlank(message = "Il campo 'email' non può essere nullo o vuoto")
    private String email;

    /**
     * La password dell'utente.
     * Non può essere nullo o vuoto.
     */
    @NotBlank(message = "Il campo 'password' non può essere nullo o vuoto")
    private String password;
}