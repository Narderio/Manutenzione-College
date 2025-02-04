package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

/**
 * DTO che rappresenta la richiesta di password dimenticata.
 */
@Data
public class PasswordDimenticataRequest {

    /**
     * L'indirizzo email dell'utente.
     */
    @Email(message = "Il campo 'email' deve essere un indirizzo email valido")
    private String email;
}