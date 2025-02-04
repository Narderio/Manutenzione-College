package org.elis.manutenzione.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import org.elis.manutenzione.builderPattern.SupervisoreBuilder;

/**
 * Questa classe rappresenta un Supervisore nel sistema di manutenzione.
 * Un Supervisore è un tipo di {@link Utente} con responsabilità di gestione
 * delle segnalazioni e assegnazione dei lavori ai manutentori.
 */
@Data
@Entity
@DiscriminatorValue("Supervisore")
public class Supervisore extends Utente {

    /**
     * Costruttore senza argomenti richiesto da JPA.
     */
    public Supervisore() {
        super();
    }

    /**
     * Costruttore privato che utilizza il pattern Builder per creare un'istanza di Supervisore.
     *
     * @param builder Il builder contenente i dati per la creazione del Supervisore.
     * @see SupervisoreBuilder
     */
    public Supervisore(SupervisoreBuilder builder) {
        super(builder);
    }


}