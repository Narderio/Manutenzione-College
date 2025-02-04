package org.elis.manutenzione.repository;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.elis.manutenzione.model.entity.Luogo;
import org.elis.manutenzione.model.entity.Manutentore;
import org.elis.manutenzione.model.entity.Manutenzione;
import org.elis.manutenzione.model.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfaccia repository per la gestione delle manutenzioni nel database.
 * Estende {@link JpaRepository} per fornire metodi di accesso ai dati per l'entità {@link Manutenzione}.
 */
public interface ManutenzioneRepository extends JpaRepository<Manutenzione, Long> {

    /**
     * Trova una manutenzione in base al suo ID.
     *
     * @param id L'ID della manutenzione.
     * @return La {@link Manutenzione} corrispondente all'ID specificato.
     */
    public Manutenzione findById(long id);

    /**
     * Trova tutte le manutenzioni assegnate a un determinato manutentore.
     *
     * @param u Il {@link Utente} che rappresenta il manutentore.
     * @return Una lista di {@link Manutenzione} assegnate al manutentore.
     */
    public List<Manutenzione> findAllByManutentore(Utente u);

    /**
     * Trova una manutenzione in base alla sua descrizione e all'ID del luogo in cui è stata effettuata.
     *
     * @param lowerCase La descrizione della manutenzione (in minuscolo).
     * @param idLuogo   L'ID del luogo.
     * @return La {@link Manutenzione} corrispondente alla descrizione e all'ID del luogo specificati.
     */
    public Manutenzione findByDescrizioneAndLuogoId(String lowerCase, long idLuogo);

    /**
     * Trova una manutenzione in base all'ID del luogo in cui è stata effettuata.
     *
     * @param idLuogo L'ID del luogo.
     *
     * @return La {@link Manutenzione} corrispondente all'ID del luogo specificato.
     */
    @Query("SELECT m FROM Manutenzione m WHERE m.luogo.id = ?1 AND (m.statoManutenzione != 'Riparato' or m.statoManutenzione != 'Rifiutato')")
    public List<Manutenzione> findByLuogoIdAndStatoManutenzione(long idLuogo);

    /**
     * Trova tutte le manutenzioni richieste da un determinato utente.
     *
     * @param u L'utente che ha richiesto le manutenzioni.
     * @return Una lista di {@link Manutenzione} richieste dall'utente.
     */
    public List<Manutenzione> findAllByUtenteRichiedente(Utente u);

/**
     * Trova una manutenzione in base al luogo e alla data prevista.
     *
     * @param luogo        Il luogo della manutenzione.
     * @param dataPrevista La data prevista per la manutenzione.
     * @return La {@link Manutenzione} corrispondente al luogo e alla data prevista specificati.
     */
    Manutenzione findByLuogoAndDataPrevista(Luogo luogo, LocalDate dataPrevista);

    /**
     * Trova una manutenzione in base al manutentore e alla data prevista.
     *
     * @param u            Il manutentore assegnato alla manutenzione.
     * @param dataPrevista La data prevista per la manutenzione.
     * @return La {@link Manutenzione} corrispondente al manutentore e alla data prevista specificati.
     */
    Manutenzione findByManutentoreAndDataPrevista(Manutentore u, LocalDate dataPrevista);
}