package org.elis.manutenzione.handler;

/**
 * Eccezione personalizzata per errori di tipo Forbidden (403).
 * Questa eccezione viene lanciata quando il client non ha l'autorizzazione per accedere alla risorsa richiesta.
 */
public class NoContentException extends RuntimeException {
    /**
     * Costruttore della classe.
     *
     * @param message Il messaggio che descrive l'errore.
     */
    public NoContentException(String message) {
        super(message);
    }
}