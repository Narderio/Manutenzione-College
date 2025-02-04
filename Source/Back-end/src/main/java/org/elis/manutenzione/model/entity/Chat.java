package org.elis.manutenzione.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

/**
 * Entità che rappresenta una chat tra due utenti.
 * Questa classe definisce la struttura di una chat all'interno del sistema,
 * includendo gli utenti partecipanti e la lista dei messaggi scambiati.
 */
@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id_utente_uno","id_utente_due"})})
public class Chat {

    /**
     * L'ID univoco della chat, generato automaticamente dal database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Il primo utente coinvolto nella chat.
     * Questo campo è mappato alla tabella `Utente` tramite una relazione Many-to-One.
     * La colonna che fa da foreign key si chiama `id_utente_uno` e non può essere null.
     * L'annotazione `@JsonIgnore` fa sì che questo campo non venga incluso nelle risposte JSON,
     * per evitare loop infiniti e problemi di serializzazione.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_utente_uno", nullable = false)
    private Utente utenteUno;

    /**
     * Il secondo utente coinvolto nella chat.
     * Questo campo è mappato alla tabella `Utente` tramite una relazione Many-to-One.
     * La colonna che fa da foreign key si chiama `id_utente_due` e può essere null.
     * L'annotazione `@JsonIgnore` fa sì che questo campo non venga incluso nelle risposte JSON,
     * per evitare loop infiniti e problemi di serializzazione.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_utente_due")
    private Utente utenteDue;

    /**
     * La lista dei messaggi scambiati nella chat.
     * Questo campo è mappato alla tabella `Messaggio` tramite una relazione One-to-Many.
     * La colonna `chat` nella tabella `Messaggio` è usata come chiave per la relazione.
     * L'annotazione `@JsonIgnore` fa sì che questo campo non venga incluso nelle risposte JSON,
     * per evitare loop infiniti e problemi di serializzazione.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "chat")
    private List<Messaggio> messaggi;

    /**
     * Costruttore vuoto per l'entità Chat. Necessario per JPA.
     */
    public Chat() {
    }

    /**
     * Costruttore per l'entità Chat che accetta i due utenti coinvolti.
     * @param utenteUno il primo utente della chat.
     * @param utenteDue il secondo utente della chat.
     */
    public Chat(Utente utenteUno, Utente utenteDue) {
        this.utenteUno = utenteUno;
        this.utenteDue = utenteDue;
    }
}