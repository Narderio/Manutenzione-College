package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO per la richiesta di modifica di un messaggio.
 * Contiene l'ID del messaggio da modificare e il nuovo testo.
 */
@Data
public class ModificaMessaggioDTO {
    /**
     * L'ID del messaggio da modificare.
     * Deve essere maggiore di 0.
     */
    @Min(value = 1, message = "L'id del messaggio deve essere maggiore di 0")
    private long id;

    /**
     * Il nuovo testo del messaggio.
     * Non può essere vuoto o nullo.
     */
    @NotBlank(message = "Il campo 'testo' non può essere nullo o vuoto")
    private String testo;
}