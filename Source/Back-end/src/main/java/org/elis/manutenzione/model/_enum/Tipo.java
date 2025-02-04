package org.elis.manutenzione.model._enum;

/**
 * Enumerazione che definisce i possibili tipi di luoghi in cui può essere effettuato un intervento di manutenzione.
 */
public enum Tipo {
    /**
     * Indica un intervento di manutenzione in un bagno.
     */
    Bagno,

    /**
     * Indica un intervento di manutenzione in una stanza.
     */
    Stanza,

    /**
     * Indica un intervento di manutenzione in un luogo comune, come ad esempio un corridoio, una sala riunioni o un giardino.
     */
    LuogoComune;
}