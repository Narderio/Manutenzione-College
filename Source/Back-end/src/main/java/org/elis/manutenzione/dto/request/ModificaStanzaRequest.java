package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * DTO per la richiesta di modifica di una stanza.
 * Contiene l'ID della stanza da modificare e la nuova capienza.
 */
@Data
public class ModificaStanzaRequest {

    /**
     * L'ID della stanza da modificare.
     * Deve essere maggiore o uguale a 1.
     */
    @Min(value = 1, message = "L'id della stanza non può essere nullo o minore di 1")
    private long idStanza;

    /**
     * La nuova capienza della stanza.
     * Deve essere maggiore o uguale a 1.
     */
    @Min(value = 1, message = "La capienza del luogo non può essere nullo o minore di 1")
    private int capienza;
}