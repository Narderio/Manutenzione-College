package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * DTO per la richiesta di aggiunta di un luogo.
 * Contiene i dati necessari per la creazione di un nuovo luogo.
 */
@Data
public class AggiungiLuogoRequest {

    /**
     * Il nome del luogo.
     * Non può essere vuoto.
     */
    @NotBlank(message = "Il nome del luogo non può essere vuoto")
    private String nome;

    /**
     * Il nucleo del luogo (ad esempio, il nome del piano o dell'area).
     * Può essere nullo se il luogo è di tipo LuogoComune.
     */
    private String nucleo;

    /**
     * Il tipo del luogo.
     * Deve essere uno tra "Bagno", "Stanza", "LuogoComune".
     */
    @NotBlank(message = "Il tipo del luogo non può essere vuoto")
    @Pattern(regexp = "Bagno|Stanza|LuogoComune", message = "Il tipo del luogo deve essere uno tra Bagno, Stanza, LuogoComune")
    private String tipo;

    /**
     * Il piano in cui si trova il luogo.
     * Deve essere compreso tra -1 e 5.
     */
    @Min(value = -1, message = "Il piano del luogo non può essere negativo")
    @Max(value = 5, message = "Il piano del luogo non può essere maggiore di 5")
    private int piano;

    /**
     * La capienza massima del luogo.
     * Deve essere maggiore o uguale a 1.
     */
    @Min(value = 1, message = "La capienza del luogo non può essere minore di 1")
    private int capienza;
}