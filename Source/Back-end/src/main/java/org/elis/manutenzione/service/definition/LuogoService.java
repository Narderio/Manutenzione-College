package org.elis.manutenzione.service.definition;

import org.elis.manutenzione.dto.request.AggiungiLuogoRequest;
import org.elis.manutenzione.dto.request.EliminaLuogoRequest;
import org.elis.manutenzione.dto.request.ModificaStanzaRequest;
import org.elis.manutenzione.model.entity.Luogo;
import org.elis.manutenzione.model.entity.Residente;

import java.util.List;

/**
 * Interfaccia del servizio per la gestione dei luoghi.
 * Questa interfaccia definisce i metodi per le operazioni relative ai luoghi,
 * come l'aggiunta, la modifica, l'eliminazione e il recupero dei luoghi.
 */
public interface LuogoService {

    /**
     * Aggiunge un nuovo luogo nel sistema.
     *
     * @param request DTO contenente i dati del luogo da aggiungere.
     * @return L'oggetto Luogo appena creato e salvato.
     */
    public Luogo aggiungiLuogo(AggiungiLuogoRequest request);



    /**
     * Recupera la lista di tutti i luoghi presenti nel sistema.
     *
     * @return La lista di tutti gli oggetti Luogo.
     */
    public List<Luogo> getLuoghi();


    /**
     * Elimina un luogo specifico dal sistema.
     *
     * @param request DTO contenente l'id del luogo da eliminare.
     * @return true se l'operazione di eliminazione è completata con successo, false altrimenti.
     */
    public boolean eliminaLuogo(EliminaLuogoRequest request);


    /**
     * Recupera la lista di tutti i luoghi comuni.
     *
     * @return La lista di tutti gli oggetti Luogo di tipo LuogoComune.
     */
    public List<Luogo> getLuoghiComuni();


    /**
     * Recupera la lista di tutte le stanze.
     *
     * @return La lista di tutti gli oggetti Luogo di tipo Stanza.
     */
    public List<Luogo> getStanze();


    /**
     * Recupera la lista di tutti i bagni.
     *
     * @return La lista di tutti gli oggetti Luogo di tipo Bagno.
     */
    public List<Luogo> getBagni();


    /**
     * Modifica la capienza di una stanza specifica.
     *
     * @param request DTO contenente l'id della stanza e la nuova capienza.
     * @return L'oggetto Luogo (stanza) modificato.
     */
    public Luogo modificaStanza(ModificaStanzaRequest request);


    /**
     * Recupera la lista dei luoghi associati ad un residente specifico.
     *
     * @param r Il residente di cui si vogliono recuperare i luoghi.
     * @return La lista di tutti gli oggetti Luogo associati al residente.
     */
    public Luogo getStanzaByResidente(Residente r);
}