package org.elis.manutenzione.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.elis.manutenzione.builderPattern.UtenteBuilder;
import org.elis.manutenzione.model._enum.Ruolo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Questa classe astratta rappresenta un Utente generico nel sistema di manutenzione.
 * Implementa l'interfaccia {@link UserDetails} di Spring Security per la gestione dell'autenticazione.
 * Le classi concrete che estendono questa classe (es. {@link Residente}, {@link Manutentore})
 * rappresentano i diversi ruoli che un utente può avere nel sistema.
 */
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Ruolo")
@Entity
public abstract class Utente implements UserDetails {

    /**
     * L'email dell'utente, che funge anche da identificativo univoco (ID).
     */
    @Id
    private String email;

    /**
     * Il nome dell'utente.
     */
    private String nome;

    /**
     * Il cognome dell'utente.
     */
    private String cognome;

    /**
     * La password dell'utente (memorizzata in forma crittografata).
     */
    private String password;

    /**
     * La data di nascita dell'utente.
     */
    private LocalDate dataDiNascita;

    /**
     * Indica se l'account dell'utente è bloccato.
     */
    @Column(nullable = false)
    private boolean bloccato;

    /**
     * Indica se l'utente vuole ricevere le email facoltative
     */
    @Column(nullable = false, name = "email_bloccate")
    private boolean emailBloccate = false;

    /**
     * Costruttore protetto senza argomenti richiesto da JPA.
     */
    protected Utente() {}

    /**
     * Costruttore protetto che utilizza il pattern Builder per creare un'istanza di Utente.
     * Questo costruttore è utilizzato dalle sottoclassi per la creazione di istanze specifiche.
     *
     * @param builder Il builder contenente i dati per la creazione dell'Utente.
     * @see UtenteBuilder
     */
    protected Utente(UtenteBuilder<?> builder) {
        this.email = builder.getEmail();
        this.nome = builder.getNome();
        this.cognome = builder.getCognome();
        this.password = builder.getPassword();
        this.dataDiNascita = builder.getDataDiNascita();
    }

    /**
     * Restituisce le autorizzazioni assegnate all'utente.
     * In questo caso, l'autorità è basata sul ruolo dell'utente (es. "ROLE_Residente").
     *
     * @return Una collezione di {@link GrantedAuthority} che rappresentano le autorizzazioni dell'utente.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority sga= new SimpleGrantedAuthority("ROLE_" + this.getClass().getSimpleName());
        return List.of(sga);
    }

    /**
     * Restituisce l'email dell'utente, che funge da username per l'autenticazione.
     *
     * @return L'email dell'utente.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indica se l'account dell'utente è scaduto.
     * In questo caso, gli account non scadono mai, quindi restituisce sempre {@code true}.
     *
     * @return {@code true} se l'account non è scaduto, {@code false} altrimenti.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica se l'account dell'utente è bloccato.
     *
     * @return {@code true} se l'account è bloccato, {@code false} altrimenti.
     */
    @Override
    public boolean isAccountNonLocked() {
        return !bloccato;
    }

    /**
     * Indica se la password dell'utente è scaduta.
     * In questo caso, le password non scadono mai, quindi restituisce sempre {@code true}.
     *
     * @return {@code true} se la password non è scaduta, {@code false} altrimenti.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica se l'utente è abilitato.
     * In questo caso, gli utenti sono sempre abilitati, quindi restituisce sempre {@code true}.
     *
     * @return {@code true} se l'utente è abilitato, {@code false} altrimenti.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }



    public boolean getEmailBloccate() {
        return emailBloccate;
    }
}