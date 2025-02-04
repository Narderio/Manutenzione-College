package org.elis.manutenzione.handler;

/**
 * Eccezione personalizzata per errori di tipo Bad Gateway (502).
 * Questa eccezione viene lanciata quando si verifica un errore di comunicazione con un altro server o servizio.
 */
public class BadGatewayException extends RuntimeException {
    /**
     * Costruttore della classe.
     *
     * @param message Il messaggio che descrive l'errore.
     */
    public BadGatewayException(String message) {
        super(message);
    }
}