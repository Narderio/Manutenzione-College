package org.elis.manutenzione.repository;

import org.elis.manutenzione.model.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia repository per la gestione degli utenti nel database.
 * Estende {@link JpaRepository} per fornire metodi di accesso ai dati per l'entità {@link Utente}.
 */
public interface UtenteRepository extends JpaRepository<Utente, String> {

    /**
     * Trova un utente in base alla sua email.
     *
     * @param email L'email dell'utente.
     * @return L'{@link Utente} corrispondente all'email specificata.
     */
    public Utente findByEmail(String email);

    /**
     * Restituisce tutti gli utenti non bloccati.
     *
     * @return Una lista di {@link Utente} non bloccati.
     */
    public List<Utente> findAllByBloccatoIsFalse();

    /**
     * Trova un utente in base alla sua email.
     * Utilizza una query nativa SQL.
     *
     * @param email L'email dell'utente.
     * @return Un {@link Optional} contenente l'{@link Utente} corrispondente all'email specificata, se presente.
     */
    public Optional<Utente> findUtenteByEmail(String email);

    /**
     * Trova un utente in base alla sua email e password, assicurandosi che non sia bloccato.
     *
     * @param email    L'email dell'utente.
     * @param password La password dell'utente.
     * @return L'{@link Utente} corrispondente all'email e password specificate, se non bloccato.
     */
    public Utente findByEmailAndPasswordAndBloccatoIsFalse(String email, String password);

    /**
     * Aggiunge un residente a una stanza, aggiornando il suo nucleo abitativo e il numero di stanza nel database.
     * Utilizza una query nativa SQL.
     *
     * @param email      L'email del residente.
     * @param nomeNucleo Il nome del nucleo abitativo.
     * @param numeroStanza Il numero di stanza.
     */
//    @Modifying
//    @Transactional
//    @Query(nativeQuery = true, value = "UPDATE utente SET stanza_nucleo_nome = :nomeNucleo, stanza_numero = :numeroStanza WHERE email = :email")
//    public void aggiungiResidenteInStanza(String email, String nomeNucleo, int numeroStanza);

    /**
     * Trova un utente in base alla sua email, assicurandosi che non sia bloccato.
     *
     * @param email L'email dell'utente.
     * @return Un {@link Optional} contenente l'{@link Utente} corrispondente all'email specificata, se non bloccato.
     */
    public Optional<Utente> findByEmailAndBloccatoIsFalse(String email);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE utente SET ruolo = :ruolo WHERE email = :email")
    public void updateRuoloUtente(String ruolo, String email);
}