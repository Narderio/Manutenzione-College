package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.elis.manutenzione.model._enum.Ruolo;

/**
 * DTO per la richiesta di aggiornamento del ruolo di un utente.
 * Contiene l'email dell'utente e il nuovo ruolo da assegnare.
 */
@Data
public class AggiornaRuoloRequest {

    /**
     * L'email dell'utente di cui si vuole aggiornare il ruolo.
     * Non può essere vuota o nulla.
     */
    @NotBlank(message = "Il campo 'email' non può essere nullo o vuoto")
    private String email;

    /**
     * Il nuovo ruolo da assegnare all'utente.
     * Non può essere nullo.
     */
    @NotNull(message = "Il campo 'ruoloNuovo' non può essere nullo o vuoto")
    private Ruolo ruoloNuovo;
}