package org.elis.manutenzione.dto.request;

import lombok.Data;

/**
 * DTO che rappresenta i filtri per la ricerca delle manutenzioni.
 */
@Data
public class FiltroManutenzione {

    /**
     * Descrizione della manutenzione.
     */
    private String descrizione;

    /**
     * Data di richiesta della manutenzione.
     */
    private String dataRichiesta;

    /**
     * Data di riparazione della manutenzione.
     */
    private String dataRiparazione;

    /**
     * Data prevista per la riparazione della manutenzione.
     */
    private String dataPrevista;

    /**
     * Priorità della manutenzione.
     */
    private int priorita;

    /**
     * Email del manutentore assegnato alla manutenzione.
     */
    private String manutentore;

    /**
     * Stato della manutenzione (es. In attesa, In corso, Completata).
     */
    private String statoManutenzione;

    /**
     * Email dell'utente che ha richiesto la manutenzione.
     */
    private String utenteRichiedente;

    /**
     * Nome della manutenzione.
     */
    private String nome;
}