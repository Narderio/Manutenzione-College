package org.elis.manutenzione.builderPattern;

import lombok.Data;
import org.elis.manutenzione.model.entity.Luogo;
import org.elis.manutenzione.model.entity.Residente;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe builder per la creazione di oggetti Residente.
 * Estende la classe astratta {@link UtenteBuilder}.
 */
@Data
public class ResidenteBuilder extends UtenteBuilder<ResidenteBuilder> {

    /**
     * Metodo che restituisce l'istanza corrente del builder.
     * Questo metodo è necessario per permettere il concatenamento dei metodi del builder.
     *
     * @return L'istanza corrente del builder.
     */
    @Override
    protected ResidenteBuilder self() {
        return this;
    }

    /**
     * Costruisce un oggetto Residente con i parametri impostati nel builder.
     *
     * @return Un nuovo oggetto Residente.
     */
    @Override
    public Residente build() {
        return new Residente(this);
    }
}