package org.elis.manutenzione.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.elis.manutenzione.builderPattern.ManutentoreBuilder;
import java.util.List;
import java.util.Objects;

/**
 * Entità che rappresenta un manutentore del sistema.
 * Questa classe estende la classe {@link Utente} e eredita tutti i suoi attributi.
 */
@Data
@Entity
@DiscriminatorValue("Manutentore")
public class Manutentore extends Utente {

    /**
     * La lista delle manutenzioni effettuate dal manutentore.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "manutentore")
    private List<Manutenzione> manutenzioniEffettuate;

    /**
     * Il tipo di manutentore (es. idraulico, elettricista, ecc.).
     */
    private String tipoManutentore;

    /**
     * Costruttore di default richiesto da JPA.
     */
    public Manutentore() {
        super();
    }

    /**
     * Costruttore che utilizza un {@link ManutentoreBuilder} per creare un'istanza di Manutentore.
     *
     * @param builder Il builder utilizzato per la costruzione dell'oggetto.
     */
    public Manutentore(ManutentoreBuilder builder) {
        super(builder);
        this.tipoManutentore = builder.getTipoManutentore();
    }

}