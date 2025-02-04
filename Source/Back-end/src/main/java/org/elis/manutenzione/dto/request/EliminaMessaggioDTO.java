package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * DTO per la richiesta di eliminazione di un messaggio.
 * Contiene l'ID del messaggio da eliminare.
 */
@Data
public class EliminaMessaggioDTO {
    /**
     * L'ID del messaggio da eliminare.
     * Deve essere maggiore di 0.
     */
    @Min(value = 1, message = "L'id del messaggio deve essere maggiore di 0")
    private long id;
}