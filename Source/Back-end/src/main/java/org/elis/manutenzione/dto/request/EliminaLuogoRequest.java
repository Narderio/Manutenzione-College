package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * DTO per la richiesta di eliminazione di un luogo.
 * Contiene l'ID del luogo da eliminare.
 */
@Data
public class EliminaLuogoRequest {

    /**
     * L'ID del luogo da eliminare.
     * Deve essere maggiore o uguale a 1.
     */
    @Min(value = 1, message = "L'id del luogo non può essere nullo o minore di 1")
    private int luogoId;
}