package org.elis.manutenzione.service.definition;

import org.elis.manutenzione.dto.request.*;
import org.elis.manutenzione.model.entity.*;

import java.util.List;

/**
 * Interfaccia del servizio per la gestione degli utenti.
 * Questa interfaccia definisce i metodi per le operazioni relative agli utenti,
 * come la registrazione, l'eliminazione, l'assegnazione e il recupero password.
 */
public interface UtenteService {


    /**
     * Registra un nuovo utente nel sistema.
     *
     * @param registrazioneRequest DTO contenente i dati dell'utente da registrare.
     * @return L'oggetto Utente appena creato e salvato.
     */
    public Utente registrazione(RegistrazioneRequest registrazioneRequest);


    /**
     * Recupera tutti gli utenti non bloccati.
     *
     * @return Una lista di tutti gli utenti non bloccati.
     */
    public List<Utente> getUtenti(Utente u);


    /**
     * Recupera tutti i residenti non bloccati.
     *
     * @return Una lista di tutti i residenti non bloccati.
     */
    public List<Residente> getResidenti();


    /**
     * Elimina un utente tramite email (impostando il campo bloccato a true).
     *
     * @param request DTO contenente l'email dell'utente da eliminare.
     * @return true se l'operazione ha avuto successo, false altrimenti.
     */
    public boolean deleteUtente(EliminaUtenteRequest request);


    /**
     * Aggiunge un residente in una stanza specifica.
     *
     * @param request DTO contenente l'email del residente e il nome della stanza.
     * @return true se l'operazione ha avuto successo, false altrimenti.
     */
    public boolean aggiungiResidenteInStanza(AggiungiResidenteInStanzaRequest request);


    /**
     * Recupera tutti i manutentori non bloccati.
     *
     * @return Una lista di tutti i manutentori non bloccati.
     */
    public List<Manutentore> getManutentori();


    /**
     * Aggiorna il ruolo di un utente.
     *
     * @param aggiornaRuoloRequest DTO contenente l'email dell'utente e il nuovo ruolo.
     * @return L'oggetto Utente con il nuovo ruolo.
     */
    public Utente aggiornaRuolo(AggiornaRuoloRequest aggiornaRuoloRequest);


    /**
     * Recupera un utente tramite email e verifica se non è bloccato.
     *
     * @param email L'email dell'utente da recuperare.
     * @return L'oggetto Utente se trovato, null altrimenti.
     */
    public Utente getUtenteByEmailAndBloccatoIsFalse(String email);


    /**
     * Elimina un residente da una stanza.
     *
     * @param request DTO contenente l'email del residente da eliminare.
     * @return true se l'operazione ha avuto successo, false altrimenti.
     */
    public boolean eliminaResidenteDaStanza(EliminaResidenteDaStanzaRequest request);


    /**
     * Richiede una nuova password dimenticata.
     *
     * @param request DTO contenente l'email dell'utente che ha dimenticato la password.
     * @return true se l'operazione ha avuto successo, false altrimenti.
     */
    public boolean passwordDimenticata(String email);


    /**
     * Recupera tutti i supervisori non bloccati.
     *
     * @return Una lista di tutti i supervisori non bloccati.
     */
    public List<Supervisore> getSupervisori();

    /**
     * Recupera tutti gli amministratori non bloccati.
     *
     * @return Una lista di tutti gli amministratori non bloccati.
     */
    public List<Admin> getAdmin();

    /**
     * Disiscrive un residente dalle email di notifica.
     * @param r Il residente da disiscrivere
     * @return true se l'operazione ha avuto successo, false altrimenti.
     */
    public boolean disiscriviEmail(Residente r);

    /**
     * Iscrive un residente alle email di notifica.
     * @param r Il residente da iscrivere
     * @return true se l'operazione ha avuto successo, false altrimenti.
     */
    public boolean iscriviEmail(Residente r);
}