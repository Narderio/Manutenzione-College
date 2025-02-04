package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO per la richiesta di eliminazione di un residente da una stanza.
 * Contiene l'email del residente da eliminare.
 */
@Data
public class EliminaResidenteDaStanzaRequest {

    /**
     * L'email del residente da eliminare dalla stanza.
     * Non può essere vuota o nulla.
     */
    @NotBlank(message = "Il campo 'email' non può essere nullo o vuoto")
    private String email;
}