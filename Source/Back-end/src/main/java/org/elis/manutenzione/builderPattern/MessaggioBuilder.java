package org.elis.manutenzione.builderPattern;

import org.elis.manutenzione.model.entity.Chat;
import org.elis.manutenzione.model.entity.Messaggio;
import org.elis.manutenzione.model._enum.TipoMessaggioInviato;

/**
 * Builder per la creazione di oggetti {@link Messaggio}.
 * Fornisce un'interfaccia fluida per la costruzione di oggetti Messaggio.
 */
public class MessaggioBuilder {
    /**
     * Il testo del messaggio.
     */
    private String testo;
    /**
     * Indica se il messaggio è stato inviato dal primo utente della chat.
     */
    private boolean primoUtente;
    /**
     * La chat a cui appartiene il messaggio.
     */
    private Chat chat;
    /**
     * L'ID del messaggio a cui risponde.
     */
    private long idRiferimentoMessaggio;


    /**
     * Il tipo del messaggio. Immagine o testo
     */
    private TipoMessaggioInviato tipoMessaggio;

    /**
     * Imposta il testo del messaggio.
     * @param testo Il testo del messaggio.
     * @return Il builder stesso per concatenare le chiamate.
     */
    public MessaggioBuilder testo(String testo) {
        this.testo = testo;
        return this;
    }

    /**
     * Imposta se il messaggio è stato inviato dal primo utente.
     * @param primoUtente Indica se il messaggio è del primo utente.
     * @return Il builder stesso per concatenare le chiamate.
     */
    public MessaggioBuilder primoUtente(boolean primoUtente) {
        this.primoUtente = primoUtente;
        return this;
    }

    public MessaggioBuilder tipoMessaggio(TipoMessaggioInviato tipoMessaggio) {
        this.tipoMessaggio = tipoMessaggio;
        return this;
    }

    /**
     * Imposta la chat a cui appartiene il messaggio.
     * @param chat La chat a cui appartiene il messaggio.
     * @return Il builder stesso per concatenare le chiamate.
     */
    public MessaggioBuilder chat(Chat chat) {
        this.chat = chat;
        return this;
    }

    /**
     * Imposta l'ID del messaggio di riferimento.
     * @param idRiferimentoMessaggio L'ID del messaggio di riferimento.
     * @return Il builder stesso per concatenare le chiamate.
     */
    public MessaggioBuilder idRiferimentoMessaggio(long idRiferimentoMessaggio) {
        this.idRiferimentoMessaggio = idRiferimentoMessaggio;
        return this;
    }

    /**
     * Costruisce un oggetto Messaggio con i parametri impostati.
     * @return Un'istanza di Messaggio.
     */
    public Messaggio build() {
        return new Messaggio(testo, primoUtente, chat, idRiferimentoMessaggio, tipoMessaggio);
    }
}