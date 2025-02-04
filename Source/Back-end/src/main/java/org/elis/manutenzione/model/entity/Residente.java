package org.elis.manutenzione.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.elis.manutenzione.builderPattern.ResidenteBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Entità che rappresenta un residente della struttura.
 * Questa classe estende la classe {@link Utente} e eredita tutti i suoi attributi.
 */
@Data
@Entity
@DiscriminatorValue("Residente")
public class Residente extends Utente {

    /**
     * La lista dei luoghi a cui è assegnato il residente (stanza-bagno-luoghicomuni).
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "residente_luogo",
            joinColumns = @JoinColumn(name = "residente_email"),
            inverseJoinColumns = @JoinColumn(name = "luogo_id")
    )
    private List<Luogo> luoghi = new ArrayList<>();

    /**
     * Costruttore di default richiesto da JPA.
     */
    public Residente() {
        super();
    }

    /**
     * Costruttore che utilizza un {@link ResidenteBuilder} per creare un'istanza di Residente.
     * @param builder Il builder utilizzato per la costruzione dell'oggetto.
     */
    public Residente(ResidenteBuilder builder) {
        super(builder);
    }
}