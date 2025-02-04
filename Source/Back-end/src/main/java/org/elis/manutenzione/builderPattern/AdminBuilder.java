package org.elis.manutenzione.builderPattern;

import org.elis.manutenzione.model.entity.Admin;

/**
 * Classe builder per la creazione di oggetti Admin.
 * Estende la classe astratta {@link UtenteBuilder}.
 */
public class AdminBuilder extends UtenteBuilder<AdminBuilder> {

    /**
     * Metodo che restituisce l'istanza corrente del builder.
     * Questo metodo è necessario per permettere il concatenamento dei metodi del builder.
     *
     * @return L'istanza corrente del builder.
     */
    @Override
    protected AdminBuilder self() {
        return this;
    }

    /**
     * Costruisce un oggetto Admin con i parametri impostati nel builder.
     *
     * @return Un nuovo oggetto Admin.
     */
    @Override
    public Admin build() {
        return new Admin(this);
    }
}