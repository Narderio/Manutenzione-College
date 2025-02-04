package org.elis.manutenzione.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.elis.manutenzione.model._enum.TipoMessaggioInviato;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

/**
 * Entità che rappresenta un messaggio all'interno di una chat.
 */
@Data
@Entity
public class Messaggio {

    /**
     * L'ID del messaggio.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Il testo del messaggio.
     */
    @Column(columnDefinition = "LONGTEXT")
    private String testo;

    /**
     * La data e ora di invio del messaggio.
     */
    @CreationTimestamp
    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    private LocalDateTime dataOra;

    /**
     * Indica se il messaggio è stato inviato dal primo utente della chat.
     */
    private boolean primoUtente;

    /**
     * La chat a cui appartiene il messaggio.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_chat")
    private Chat chat;

    /**
     * L'ID del messaggio a cui risponde.
     */
    private long idRiferimentoMessaggio;

    /**
     * Il tipo del messaggio. Immagine, testo o link per la manutenzione
     */
    private TipoMessaggioInviato tipoMessaggio;

    /**
     * Costruttore di default.
     */
    public Messaggio() {
    }

    /**
     * Costruttore per la creazione di un oggetto Messaggio.
     * @param testo Il testo del messaggio.
     * @param primoUtente Indica se il messaggio è del primo utente.
     * @param chat La chat a cui appartiene il messaggio.
     * @param idRiferimentoMessaggio L'ID del messaggio a cui risponde.
     */
    public Messaggio(String testo, boolean primoUtente, Chat chat, long idRiferimentoMessaggio, TipoMessaggioInviato tipoMessaggio) {
        this.testo = testo;
        this.primoUtente = primoUtente;
        this.chat = chat;
        this.idRiferimentoMessaggio = idRiferimentoMessaggio;
        this.tipoMessaggio = tipoMessaggio;
    }
}