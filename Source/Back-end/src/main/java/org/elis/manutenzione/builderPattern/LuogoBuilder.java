package org.elis.manutenzione.builderPattern;

import org.elis.manutenzione.model.entity.Luogo;
import org.elis.manutenzione.model._enum.Tipo;

/**
 * Classe builder per la creazione di oggetti Luogo.
 */
public class LuogoBuilder {
    private String nome;
    private String nucleo;
    private Tipo tipo;
    private int piano;
    private int capienza;


    /**
     * Imposta il nome del luogo.
     *
     * @param nome Il nome del luogo.
     * @return L'istanza corrente del builder.
     */
    public LuogoBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    /**
     * Imposta il nucleo del luogo.
     *
     * @param nucleo Il nucleo del luogo.
     * @return L'istanza corrente del builder.
     */
    public LuogoBuilder nucleo(String nucleo) {
        this.nucleo = nucleo;
        return this;
    }

    /**
     * Imposta il tipo del luogo.
     *
     * @param tipo Il tipo del luogo.
     * @return L'istanza corrente del builder.
     */
    public LuogoBuilder tipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    /**
     * Imposta il piano del luogo.
     *
     * @param piano Il piano del luogo.
     * @return L'istanza corrente del builder.
     */
    public LuogoBuilder piano(int piano) {
        this.piano = piano;
        return this;
    }

    /**
     * Imposta la capienza del luogo.
     *
     * @param capienza La capienza del luogo.
     * @return L'istanza corrente del builder.
     */
    public LuogoBuilder capienza(int capienza) {
        this.capienza = capienza;
        return this;
    }


    /**
     * Costruisce un oggetto Luogo con i parametri impostati nel builder.
     *
     * @return Un nuovo oggetto Luogo.
     */
    public Luogo build() {
        return new Luogo(nome, nucleo, tipo, piano, capienza);
    }
}