package org.elis.manutenzione.dto.response;

import lombok.Data;

import java.time.LocalDate;


@Data
public class GetManutenzioneDTO {

    private long idManutenzione;
    private String nome;
    private String descrizione;
    private String utenteRichiedente;
    private String luogo;
    private String stato;
    private String manutentore;
    private LocalDate dataRichiesta;
    private LocalDate dataRiparazione;
    private LocalDate dataPrevista;
    private int priorita;
    private String immagine;
}
