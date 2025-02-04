package org.elis.manutenzione.model._enum;

/**
 * Enumerazione che definisce i possibili ruoli degli utenti nel sistema di manutenzione.
 */
public enum Ruolo {
    /**
     * Ruolo di un residente, che può segnalare guasti
     */
    Residente,

    /**
     * Ruolo di un supervisore, che può gestire le segnalazioni dei residenti e assegnare i lavori ai manutentori.
     */
    Supervisore,

    /**
     * Ruolo di un manutentore, che può visualizzare i lavori assegnati e aggiornarne lo stato.
     */
    Manutentore,

    /**
     * Ruolo di un amministratore, che può gestire gli utenti e i luoghi del sistema.
     */
    Admin;
}