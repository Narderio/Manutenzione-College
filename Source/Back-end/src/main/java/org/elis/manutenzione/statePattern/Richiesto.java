package org.elis.manutenzione.statePattern;

/**
 * Questa classe rappresenta lo stato "Richiesto" di una manutenzione,
 * implementando l'interfaccia {@link StatoManutenzione}.
 */
public class Richiesto implements StatoManutenzione {

    /**
     * Restituisce una stringa che rappresenta lo stato della manutenzione.
     *
     * @return La stringa "Richiesto".
     */
    @Override
    public String getStato() {
        return "Richiesto";
    }
}