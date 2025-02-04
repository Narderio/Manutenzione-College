package org.elis.manutenzione.dto.response;

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO che rappresenta la risposta per la richiesta di ottenere i dettagli dei manutentori.
 */
@Data
public class GetManutentoriResponse {

    /**
     * L'indirizzo email del manutentore.
     */
    private String email;

    /**
     * Il nome del manutentore.
     */
    private String nome;

    /**
     * Il cognome del manutentore.
     */
    private String cognome;

    /**
     * La data di nascita del manutentore.
     */
    private LocalDate dataDiNascita;

    /**
     * Il numero di manutenzioni effettuate dal manutentore.
     */
    private int manutenzioniEffettuate;

    /**
     * Il tipo di manutentore (es. idraulico, elettricista, ecc.).
     */
    private String tipoManutentore;
}