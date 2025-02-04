package org.elis.manutenzione.statePattern;

/**
 * Interfaccia che definisce il comportamento degli stati di una manutenzione.
 * <p>
 * Questa interfaccia fa parte dell'implementazione del pattern State per la gestione dello stato di una manutenzione.
 * Ogni classe concreta che implementa questa interfaccia rappresenta uno specifico stato della manutenzione (ad esempio, "Richiesto", "Accettato", "Riparato", ecc.).
 */
public interface StatoManutenzione {

    /**
     * Restituisce una stringa che rappresenta lo stato corrente della manutenzione.
     *
     * @return La stringa che rappresenta lo stato della manutenzione.
     */
    String getStato();
}