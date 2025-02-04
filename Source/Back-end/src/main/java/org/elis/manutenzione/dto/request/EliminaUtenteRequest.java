package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO per la richiesta di eliminazione di un utente.
 * Contiene l'email dell'utente da eliminare.
 */
@Data
public class EliminaUtenteRequest {

    /**
     * L'email dell'utente da eliminare.
     * Non può essere nulla.
     */
    @NotNull(message = "Il campo 'email' non può essere nullo")
    private String email;
}