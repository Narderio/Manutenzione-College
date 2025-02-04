package org.elis.manutenzione.builderPattern;

import lombok.Data;
import org.elis.manutenzione.model.entity.Utente;

import java.time.LocalDate;

/**
 * Classe astratta builder per la creazione di oggetti {@link Utente}.
 * Questa classe fornisce un'interfaccia fluente per la costruzione di oggetti Utente,
 * consentendo di impostare i vari attributi in modo incrementale.
 *
 * @param <T> Il tipo concreto del builder, che estende {@link UtenteBuilder}.
 */
@Data
public abstract class UtenteBuilder<T extends UtenteBuilder<T>> {

    protected String email;
    protected String nome;
    protected String cognome;
    protected String password;
    protected LocalDate dataDiNascita;

    /**
     * Imposta l'email dell'utente.
     *
     * @param email L'email dell'utente.
     * @return Il builder stesso, per consentire il concatenamento dei metodi.
     */
    public T email(String email) {
        this.email = email;
        return self();
    }

    /**
     * Imposta il nome dell'utente.
     *
     * @param nome Il nome dell'utente.
     * @return Il builder stesso, per consentire il concatenamento dei metodi.
     */
    public T nome(String nome) {
        this.nome = nome;
        return self();
    }

    /**
     * Imposta il cognome dell'utente.
     *
     * @param cognome Il cognome dell'utente.
     * @return Il builder stesso, per consentire il concatenamento dei metodi.
     */
    public T cognome(String cognome) {
        this.cognome = cognome;
        return self();
    }

    /**
     * Imposta la password dell'utente.
     *
     * @param password La password dell'utente.
     * @return Il builder stesso, per consentire il concatenamento dei metodi.
     */
    public T password(String password) {
        this.password = password;
        return self();
    }

    /**
     * Imposta la data di nascita dell'utente.
     *
     * @param dataDiNascita La data di nascita dell'utente.
     * @return Il builder stesso, per consentire il concatenamento dei metodi.
     */
    public T dataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
        return self();
    }

    /**
     * Metodo astratto che ritorna il builder stesso.
     * Questo metodo è necessario per mantenere il tipo corretto del builder durante il concatenamento dei metodi.
     *
     * @return Il builder stesso.
     */
    protected abstract T self();

    /**
     * Metodo astratto che costruisce l'oggetto {@link Utente}.
     * Questo metodo deve essere implementato dalle sottoclassi concrete del builder.
     *
     * @return L'oggetto Utente costruito.
     */
    public abstract Utente build();
}