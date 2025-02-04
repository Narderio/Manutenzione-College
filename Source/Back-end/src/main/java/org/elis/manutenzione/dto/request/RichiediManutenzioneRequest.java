package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO che rappresenta la richiesta di una nuova manutenzione.
 */
@Data
public class RichiediManutenzioneRequest {

    /**
     * L'ID del luogo in cui si richiede la manutenzione.
     * Deve essere un valore numerico maggiore o uguale a 1.
     */
    @Min(value = 1, message = "L'id del luogo non può essere nullo o minore di 1")
    private long idLuogo;

    /**
     * La descrizione della manutenzione richiesta.
     * Non può essere vuota.
     */
    @NotBlank(message = "La descrizione della manutenzione non può essere vuota")
    private String descrizione;

    /**
     * Il nome della manutenzione richiesta.
     * Non può essere vuoto.
     */
    @NotBlank(message = "Il nome della manutenzione non può essere vuoto")
    private String nome;

    /**
     * L'immagine allegata alla manutenzione.
     */
    private String immagine;
}