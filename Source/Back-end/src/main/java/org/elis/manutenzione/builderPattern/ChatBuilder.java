package org.elis.manutenzione.builderPattern;

import org.elis.manutenzione.model.entity.Chat;
import org.elis.manutenzione.model.entity.Utente;

/**
 * Builder per la creazione di oggetti {@link Chat}.
 * Fornisce un'interfaccia fluida per la costruzione di oggetti Chat.
 */
public class ChatBuilder {
    /**
     * Il primo utente della chat.
     */
    private Utente utenteUno;
    /**
     * Il secondo utente della chat.
     */
    private Utente utenteDue;

    /**
     * Imposta il primo utente della chat.
     * @param utenteUno Il primo utente.
     * @return Il builder stesso per concatenare le chiamate.
     */
    public ChatBuilder utenteUno(Utente utenteUno) {
        this.utenteUno = utenteUno;
        return this;
    }

    /**
     * Imposta il secondo utente della chat.
     * @param utenteDue Il secondo utente.
     * @return Il builder stesso per concatenare le chiamate.
     */
    public ChatBuilder utenteDue(Utente utenteDue) {
        this.utenteDue = utenteDue;
        return this;
    }

    /**
     * Costruisce un oggetto Chat con i parametri impostati.
     * @return Un'istanza di Chat.
     */
    public Chat build() {
        return new Chat(utenteUno, utenteDue);
    }
}