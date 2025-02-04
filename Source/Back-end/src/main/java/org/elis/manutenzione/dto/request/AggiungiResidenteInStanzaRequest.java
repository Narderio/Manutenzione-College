package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO per la richiesta di aggiunta di un residente in una stanza.
 * Contiene l'email del residente e il nome della stanza.
 */
@Data
public class AggiungiResidenteInStanzaRequest {

    /**
     * L'email del residente da aggiungere alla stanza.
     * Non può essere vuota o nulla.
     */
    @NotBlank(message = "Il campo 'email' non può essere nullo o vuoto")
    private String email;

    /**
     * Il nome della stanza in cui aggiungere il residente.
     * Non può essere vuoto o nullo.
     */
    @NotBlank(message = "Il campo 'stanza' non può essere nullo o vuoto")
    private String stanza;
}