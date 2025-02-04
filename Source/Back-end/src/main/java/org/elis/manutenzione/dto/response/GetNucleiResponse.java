package org.elis.manutenzione.dto.response;

import lombok.Data;

/**
 * DTO per la risposta contenente il nome di un nucleo.
 * Utilizzato per rappresentare le informazioni di un nucleo in una risposta.
 */
@Data
public class GetNucleiResponse {
    /**
     * Il nome del nucleo.
     */
    private String nome;
}