package org.elis.manutenzione.dto.response;

import lombok.Data;

/**
 * DTO che rappresenta la risposta alla richiesta di ottenere la lista dei luoghi comuni.
 */
@Data
public class GetLuoghiComuniResponse {

    /**
     * L'ID del luogo comune.
     */
    private long id;

    /**
     * Il nome del luogo comune.
     */
    private String nome;
}