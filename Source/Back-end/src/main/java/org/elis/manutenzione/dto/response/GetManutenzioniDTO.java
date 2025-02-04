package org.elis.manutenzione.dto.response;

import lombok.Data;

@Data
public class GetManutenzioniDTO {
    private long idManutenzione;
    private String nome;
    private String descrizione;
}
