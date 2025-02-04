package org.elis.manutenzione.builderPattern;

import org.elis.manutenzione.model.entity.Supervisore;

/**
 * Classe builder per la creazione di oggetti Supervisore.
 * Estende la classe astratta {@link UtenteBuilder}.
 */
public class SupervisoreBuilder extends UtenteBuilder<SupervisoreBuilder> {

    /**
     * Metodo che restituisce l'istanza corrente del builder.
     * Questo metodo è necessario per permettere il concatenamento dei metodi del builder.
     *
     * @return L'istanza corrente del builder.
     */
    @Override
    protected SupervisoreBuilder self() {
        return this;
    }

    /**
     * Costruisce un oggetto Supervisore con i parametri impostati nel builder.
     *
     * @return Un nuovo oggetto Supervisore.
     */
    @Override
    public Supervisore build() {
        return new Supervisore(this);
    }
}