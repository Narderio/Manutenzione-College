package org.elis.manutenzione.dto.response;

import lombok.Data;
import org.elis.manutenzione.model.entity.Utente;

import java.time.LocalDate;

/**
 * DTO che rappresenta una manutenzione.
 */
@Data
public class ManutenzioneDTO {

    /**
     * L'ID della manutenzione.
     */
    private Long id;

    private String nome;

    /**
     * La descrizione della manutenzione.
     */
    private String descrizione;

    /**
     * La data in cui è stata richiesta la manutenzione.
     */
    private LocalDate dataRichiesta;

    /**
     * La data in cui la manutenzione è stata riparata.
     */
    private LocalDate dataRiparazione;

    /**
     * La data prevista per la riparazione della manutenzione.
     */
    private LocalDate dataPrevista;

    /**
     * La priorità della manutenzione.
     */
    private int priorita;

    /**
     * L'email dell'utente che ha richiesto la manutenzione.
     */
    private String utenteRichiedente;

    /**
     * Il manutentore assegnato alla manutenzione.
     */
    private String manutentore;

    /**
     * Lo stato della manutenzione (es. In attesa, In corso, Completata).
     */
    private String statoManutenzione;

    /**
     * L'immagine allegata alla manutenzione.
     */
    private String immagine;

    /**
     * Il luogo in cui si effettua la manutenzione.
     */
    private String luogo;
}