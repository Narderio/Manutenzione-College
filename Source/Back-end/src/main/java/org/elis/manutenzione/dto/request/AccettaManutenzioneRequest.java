package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

/**
 * DTO per la richiesta di accettazione di una manutenzione.
 * Contiene l'ID della manutenzione e la data prevista per la riparazione.
 */
@Data
public class AccettaManutenzioneRequest {

    /**
     * L'ID della manutenzione da accettare.
     * Deve essere maggiore o uguale a 1.
     */
    @Min(value = 1, message = "L'id della manutenzione non può essere nullo o minore di 1")
    private long idManutenzione;

    /**
     * La data prevista per la riparazione della manutenzione.
     * Deve essere una data nel futuro.
     */
    @NotNull(message = "Inserire la data prevista")
    @Future(message = "La data prevista deve essere nel futuro")
    private LocalDate dataPrevista;
}