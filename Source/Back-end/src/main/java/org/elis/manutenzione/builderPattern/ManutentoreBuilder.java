package org.elis.manutenzione.builderPattern;

import lombok.Data;
import org.elis.manutenzione.model.entity.Manutentore;

/**
 * Classe builder per la creazione di oggetti Manutentore.
 * Estende la classe astratta {@link UtenteBuilder}.
 */
@Data
public class ManutentoreBuilder extends UtenteBuilder<ManutentoreBuilder> {

    private String tipoManutentore;

    /**
     * Imposta il tipo di manutentore.
     *
     * @param tipoManutentore Il tipo di manutentore.
     * @return L'istanza corrente del builder.
     */
    public ManutentoreBuilder tipoManutentore(String tipoManutentore) {
        this.tipoManutentore = tipoManutentore;
        return this;
    }

    /**
     * Metodo che restituisce l'istanza corrente del builder.
     * Questo metodo è necessario per permettere il concatenamento dei metodi del builder.
     *
     * @return L'istanza corrente del builder.
     */
    @Override
    protected ManutentoreBuilder self() {
        return this;
    }

    /**
     * Costruisce un oggetto Manutentore con i parametri impostati nel builder.
     *
     * @return Un nuovo oggetto Manutentore.
     */
    @Override
    public Manutentore build() {
        return new Manutentore(this);
    }
}