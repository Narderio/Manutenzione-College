package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO che rappresenta la richiesta di feedback per una manutenzione.
 */
@Data
public class FeedbackManutenzioneRequest {

        /**
         * L'ID della manutenzione a cui si riferisce il feedback.
         * Deve essere un valore numerico maggiore o uguale a 1.
         */
        @Min(value = 1, message = "L'id della manutenzione non può essere nullo o minore di 1")
        private long idManutenzione;

        /**
         * Indica se il problema è stato risolto o meno.
         * Non può essere nullo.
         */
        @NotNull(message = "Il campo 'risolto' non può essere nullo")
        private boolean risolto;

        /**
         * Una descrizione del problema riscontrato, se presente.
         */
        private String problema;
}