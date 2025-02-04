package org.elis.manutenzione.dto.response;

import lombok.Data;

/**
 * DTO che rappresenta la risposta alla richiesta di ottenere la lista dei bagni.
 */
@Data
public class GetBagniResponse {

    /**
     * L'ID del bagno.
     */
    private long id;

    /**
     * Il nucleo a cui appartiene il bagno.
     */
    private String nucleo;
}