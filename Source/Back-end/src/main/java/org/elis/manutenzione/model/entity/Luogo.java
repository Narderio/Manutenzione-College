package org.elis.manutenzione.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.elis.manutenzione.model._enum.Tipo;

import java.util.ArrayList;
import java.util.List;

/**
 * Entità che rappresenta un luogo all'interno della struttura.
 */
@Data
@Entity
public class Luogo {

    /**
     * L'ID del luogo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Il nome del luogo.
     */
    private String nome;

    /**
     * Il nucleo a cui appartiene il luogo.
     */
    private String nucleo;

    /**
     * Il tipo di luogo (es. Bagno, Stanza, Luogo Comune).
     */
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    /**
     * Il piano in cui si trova il luogo.
     */
    private int piano;

    /**
     * La capienza massima del luogo.
     */
    private int capienza;

    /**
     * La lista dei residenti assegnati al luogo (solo per le stanze).
     */
    @ManyToMany(mappedBy = "luoghi", fetch = FetchType.EAGER)
    private List<Residente> residenti = new ArrayList<>();

    /**
     * Costruttore di default per JPA.
     */
    public Luogo() {}

    /**
     * Costruttore che accetta tutti i parametri.
     *
     * @param nome Il nome del luogo.
     * @param nucleo Il nucleo a cui appartiene il luogo.
     * @param tipo Il tipo di luogo.
     * @param piano Il piano in cui si trova il luogo.
     * @param capienza La capienza massima del luogo.
     */
    public Luogo(String nome, String nucleo, Tipo tipo, int piano, int capienza) {
        this.nome = nome;
        this.nucleo = nucleo;
        this.tipo = tipo;
        this.piano = piano;
        this.capienza = capienza;
    }

}