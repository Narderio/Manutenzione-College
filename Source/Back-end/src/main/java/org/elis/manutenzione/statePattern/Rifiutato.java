package org.elis.manutenzione.statePattern;

/**
 * Questa classe rappresenta lo stato "Rifiutato" di una manutenzione,
 * implementando l'interfaccia {@link StatoManutenzione}.
 */
public class Rifiutato implements StatoManutenzione {

    /**
     * Restituisce una stringa che rappresenta lo stato della manutenzione.
     *
     * @return La stringa "Rifiutato".
     */
    @Override
    public String getStato() {
        return "Rifiutato";
    }
}