package org.elis.manutenzione.handler;

/**
 * Eccezione personalizzata per errori di tipo Bad Request (400).
 * Questa eccezione viene lanciata quando la richiesta del client non è valida.
 */
public class BadRequestException extends RuntimeException {
    /**
     * Costruttore della classe.
     *
     * @param message Il messaggio che descrive l'errore.
     */
    public BadRequestException(String message) {
        super(message);
    }
}