package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO per la richiesta di cambio password di un utente.
 * Contiene la vecchia password, la nuova password e la conferma della nuova password.
 */
@Data
public class CambioPasswordRequest {

    /**
     * La password attuale dell'utente.
     * Non può essere vuota o nulla.
     */
    @NotBlank(message = "Il campo 'ultimaPassword' non può essere nullo o vuoto")
    private String ultimaPassword;

    /**
     * La nuova password che l'utente vuole impostare.
     * Non può essere vuota o nulla e deve contenere almeno 8 caratteri.
     */
    @NotBlank(message = "Il campo 'nuovaPassword' non può essere nullo o vuoto")
    @Size(min = 8, message = "La password deve contenere almeno 8 caratteri")
    private String nuovaPassword;

    /**
     * La conferma della nuova password.
     * Non può essere vuota o nulla.
     */
    @NotBlank(message = "Il campo 'confermaPassword' non può essere nullo o vuoto")
    private String confermaPassword;
}