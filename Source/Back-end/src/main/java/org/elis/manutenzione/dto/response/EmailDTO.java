package org.elis.manutenzione.dto.response;

import lombok.Data;
import java.util.List;

/**
 * DTO che rappresenta un'email.
 */
@Data
public class EmailDTO {

    /**
     * La lista dei destinatari dell'email.
     */
    private List<String> destinatario;

    /**
     * L'oggetto dell'email.
     */
    private String oggetto;

    /**
     * Il corpo del testo dell'email.
     */
    private String testo;
}