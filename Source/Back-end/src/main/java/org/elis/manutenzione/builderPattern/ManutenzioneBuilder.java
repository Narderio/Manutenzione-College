package org.elis.manutenzione.builderPattern;

import lombok.Data;
import org.elis.manutenzione.model.entity.Luogo;
import org.elis.manutenzione.model.entity.Manutenzione;
import org.elis.manutenzione.model.entity.Residente;
import org.elis.manutenzione.statePattern.StatoManutenzione;

import java.time.LocalDate;

/**
 * Classe builder per la creazione di oggetti Manutenzione.
 */
@Data
public class ManutenzioneBuilder {
    private Residente utenteRichiedente;
    private String descrizione;
    private String statoManutenzione;
    private StatoManutenzione stato;
    private Luogo luogo;
    private String nome;
    private LocalDate dataRichiesta;
    private String immagine;

    /**
     * Imposta l'utente richiedente la manutenzione.
     *
     * @param utenteRichiedente L'utente richiedente la manutenzione.
     * @return L'istanza corrente del builder.
     */
    public ManutenzioneBuilder utenteRichiedente(Residente utenteRichiedente) {
        this.utenteRichiedente = utenteRichiedente;
        return this;
    }

    /**
     * Imposta la data di richiesta della manutenzione.
     *
     * @param dataRichiesta La data di richiesta della manutenzione.
     * @return L'istanza corrente del builder.
     */
    public ManutenzioneBuilder dataRichiesta(LocalDate dataRichiesta) {
        this.dataRichiesta = dataRichiesta;
        return this;
    }

    /**
     * Imposta la descrizione della manutenzione.
     *
     * @param descrizione La descrizione della manutenzione.
     * @return L'istanza corrente del builder.
     */
    public ManutenzioneBuilder descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    /**
     * Imposta l'immagine del guasto.
     *
     * @param immagine La descrizione della manutenzione.
     * @return L'istanza corrente del builder.
     */
    public ManutenzioneBuilder immagine(String immagine) {
        this.immagine = immagine;
        return this;
    }


    /**
     * Imposta il nome della manutenzione.
     *
     * @param nome Il nome della manutenzione.
     * @return L'istanza corrente del builder.
     */
    public ManutenzioneBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    /**
     * Imposta lo stato della manutenzione.
     *
     * @param stato Lo stato della manutenzione.
     * @return L'istanza corrente del builder.
     */
    public ManutenzioneBuilder stato(StatoManutenzione stato) {
        this.stato = stato;

        if (stato != null) {
            this.statoManutenzione = stato.getStato();
        }

        return this;
    }

    /**
     * Imposta il luogo in cui è richiesta la manutenzione.
     *
     * @param luogo Il luogo in cui è richiesta la manutenzione.
     * @return L'istanza corrente del builder.
     */
    public ManutenzioneBuilder luogo(Luogo luogo) {
        this.luogo = luogo;
        return this;
    }

    /**
     * Costruisce un oggetto Manutenzione con i parametri impostati nel builder.
     *
     * @return Un nuovo oggetto Manutenzione.
     */
    public Manutenzione build() {
        return new Manutenzione(this);
    }
}