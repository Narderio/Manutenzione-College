package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO che rappresenta la richiesta di filtraggio delle manutenzioni.
 */
@Data
public class FiltraManutenzioneRequest {

    /**
     * L'ID della manutenzione.
     * Deve essere un valore numerico maggiore o uguale a 1.
     */
    @Min(value = 1, message = "L'id della manutenzione non può essere nullo o minore di 1")
    private long idManutenzione;

    /**
     * L'indirizzo email del manutentore.
     * Non può essere vuoto.
     */
    @NotBlank(message = "L'email del manutentore non può essere vuota")
    private String emailManutentore;

    /**
     * La priorità della manutenzione.
     * Deve essere un valore compreso tra 1 e 5.
     */
    @Min(value = 1, message = "La priorità della manutenzione non può essere minore di 1")
    @Max(value = 5, message = "La priorità della manutenzione non può essere maggiore di 5")
    private int priorita;

    /**
     * Il nome della manutenzione (da inserire nel caso il supervisore voglia modificare quello inserito dall'utente).
     */
    private String nomeManutenzione;
}