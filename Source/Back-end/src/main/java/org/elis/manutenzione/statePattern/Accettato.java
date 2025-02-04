package org.elis.manutenzione.statePattern;

/**
 * Questa classe rappresenta lo stato "Accettato" di una manutenzione,
 * implementando l'interfaccia {@link StatoManutenzione}.
 */
public class Accettato implements StatoManutenzione {

    /**
     * Restituisce una stringa che rappresenta lo stato della manutenzione.
     *
     * @return La stringa "Accettato".
     */
    @Override
    public String getStato() {
        return "Accettato";
    }
}