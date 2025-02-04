package org.elis.manutenzione.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.elis.manutenzione.builderPattern.ManutenzioneBuilder;
import org.elis.manutenzione.statePattern.*;
import java.time.LocalDate;

/**
 * Entità che rappresenta una richiesta di manutenzione.
 */
@Data
@Entity
public class Manutenzione {

    /**
     * L'ID della manutenzione.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Il residente che ha richiesto la manutenzione.
     */
    @ManyToOne
    @JoinColumn(name = "utente_richiedente_email", nullable = true)
    private Residente utenteRichiedente;

    /**
     * Il manutentore assegnato alla manutenzione.
     */
    @ManyToOne
    @JoinColumn(name = "manutentore_email", nullable = true)
    private Manutentore manutentore;

    /**
     * La descrizione della manutenzione.
     */
    private String descrizione;

    /**
     * Il nome della manutenzione (come fosse un'alias).
     */
    private String nome;

    /**
     * La data in cui è stata richiesta la manutenzione.
     */
    @Column(name = "dataRichiesta")
    private LocalDate dataRichiesta;

    /**
     * La data in cui la manutenzione è stata riparata.
     */
    private LocalDate dataRiparazione;

    /**
     * La data prevista per la riparazione della manutenzione.
     */
    private LocalDate dataPrevista;

    /**
     * La priorità della manutenzione.
     */
    @Column(nullable = true)
    private int priorita;

    /**
     * Lo stato della manutenzione (es. Richiesto, Accettato, Riparato, Rifiutato).
     */
    @Column
    private String statoManutenzione;

    /**
     * L'oggetto che rappresenta lo stato corrente della manutenzione (utilizzato per il pattern State).
     */
    @Transient
    private StatoManutenzione stato;

    /**
     * Il luogo in cui è richiesta la manutenzione.
     */
    @ManyToOne
    @JoinColumn(name = "luogo_id", nullable = false)
    private Luogo luogo;

    /**
     * L'immagine del guasto.
     */
    @Column(columnDefinition = "LONGTEXT")
    private String immagine;

    /**
     * Costruttore di default per JPA.
     */
    public Manutenzione() {}

    /**
     * Costruttore che utilizza un {@link ManutenzioneBuilder} per creare un'istanza di Manutenzione.
     *
     * @param builder Il builder utilizzato per la costruzione dell'oggetto.
     */
    public Manutenzione(ManutenzioneBuilder builder) {
        this.utenteRichiedente = builder.getUtenteRichiedente();
        this.descrizione = builder.getDescrizione();
        this.statoManutenzione = builder.getStatoManutenzione();
        this.luogo = builder.getLuogo();
        this.stato = builder.getStato();
        this.nome = builder.getNome();
        this.dataRichiesta = builder.getDataRichiesta();
        this.immagine = builder.getImmagine();
    }

    /**
     * Imposta lo stato della manutenzione.
     *
     * @param stato Il nuovo stato della manutenzione.
     */
    public void setStato(StatoManutenzione stato) {
        if(stato!=null) {
            this.stato = stato; // Assegna lo stato all'attributo `stato`
            statoManutenzione = stato.getStato();
        }
    }

    /**
     * Restituisce lo stato corrente della manutenzione.
     * Se lo stato non è ancora stato istanziato, viene creato in base al valore di `statoManutenzione`.
     *
     * @return Lo stato corrente della manutenzione.
     */
    public StatoManutenzione getStato(){
        if(stato == null && statoManutenzione != null){
            switch (statoManutenzione) {
                case "Accettato" -> stato = new Accettato();
                case "Rifiutato" -> stato = new Rifiutato();
                case "Riparato" -> stato = new Riparato();
                case "Da riparare" -> stato = new DaRiparare();
                default -> stato = new Richiesto();
            };
        }
        return stato;
    }

}