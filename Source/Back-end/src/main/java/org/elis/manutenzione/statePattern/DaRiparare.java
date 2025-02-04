package org.elis.manutenzione.statePattern;

/**
 * Questa classe rappresenta lo stato "Da riparare" di una manutenzione,
 * implementando l'interfaccia {@link StatoManutenzione}.
 */
public class DaRiparare implements StatoManutenzione {

    /**
     * Restituisce una stringa che rappresenta lo stato della manutenzione.
     *
     * @return La stringa "Da riparare".
     */
    @Override
    public String getStato() {
        return "Da riparare";
    }
}