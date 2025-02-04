package org.elis.manutenzione.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import org.elis.manutenzione.builderPattern.AdminBuilder;

/**
 * Entità che rappresenta un amministratore del sistema.
 * Questa classe estende la classe {@link Utente} e eredita tutti i suoi attributi.
 */
@Data
@Entity
@DiscriminatorValue("Admin")
public class Admin extends Utente {

    /**
     * Costruttore di default.
     */
    public Admin() {
        super();
    }

    /**
     * Costruttore che utilizza un {@link AdminBuilder} per creare un'istanza di Admin.
     *
     * @param builder Il builder utilizzato per la costruzione dell'oggetto.
     */
    public Admin(AdminBuilder builder) {
        super(builder);
    }

}