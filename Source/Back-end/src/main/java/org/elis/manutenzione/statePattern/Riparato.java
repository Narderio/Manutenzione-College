package org.elis.manutenzione.statePattern;

/**
 * Questa classe rappresenta lo stato "Riparato" di una manutenzione,
 * implementando l'interfaccia {@link StatoManutenzione}.
 */
public class Riparato implements StatoManutenzione {

    /**
     * Restituisce una stringa che rappresenta lo stato della manutenzione.
     *
     * @return La stringa "Riparato".
     */
    @Override
    public String getStato() {
        return "Riparato";
    }
}