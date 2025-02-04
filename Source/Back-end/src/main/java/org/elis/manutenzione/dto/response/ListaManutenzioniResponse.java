package org.elis.manutenzione.dto.response;

import lombok.Data;

import java.time.LocalDate;

public class ListaManutenzioniResponse {

    /**
     * La descrizione della manutenzione.
     */
    private String descrizione;

    /**
     * Il nome del luogo in cui si effettua la manutenzione.
     */
    private String luogo;


    /**
     * La data di richiesta della manutenzione.
     */
    private LocalDate dataRichiesta;

    /**
     * La data di riparazione prevista della manutenzione.
     */
    private LocalDate dataPrevista;

    /**
     * La data di riparazione effettiva della manutenzione.
     */
    private LocalDate dataRiparazione;
    /**
     * L'email dell'utente che ha richiesto la manutenzione.
     */
    private String richiedente;

    /**
     * L'email del manutentore assegnato alla manutenzione.
     */
    private String manutentore;

    /**
     * La priorità della manutenzione.
     */
    private int priorita;

    /**
     * Il nome della manutenzione.
     */
    private String nome;

    /**
     * Stato della manutenzione.
     */
    private String stato;
}