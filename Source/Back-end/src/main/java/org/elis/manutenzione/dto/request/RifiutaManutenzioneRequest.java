package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * DTO che rappresenta la richiesta di rifiuto di una manutenzione.
 */
@Data
public class RifiutaManutenzioneRequest {

    /**
     * L'ID della manutenzione da rifiutare.
     * Deve essere un valore numerico maggiore o uguale a 1.
     */
    @Min(value = 1, message = "L'id della manutenzione non può essere nullo o minore di 1")
    private long idManutenzione;
}