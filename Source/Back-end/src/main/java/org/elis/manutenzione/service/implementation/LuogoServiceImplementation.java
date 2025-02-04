package org.elis.manutenzione.service.implementation;

import org.elis.manutenzione.dto.request.ModificaStanzaRequest;
import org.elis.manutenzione.handler.BadRequestException;
import org.elis.manutenzione.builderPattern.LuogoBuilder;
import org.elis.manutenzione.dto.request.AggiungiLuogoRequest;
import org.elis.manutenzione.dto.request.EliminaLuogoRequest;
import org.elis.manutenzione.handler.NoContentException;
import org.elis.manutenzione.model._enum.Tipo;
import org.elis.manutenzione.model.entity.Luogo;
import org.elis.manutenzione.model.entity.Manutenzione;
import org.elis.manutenzione.model.entity.Residente;
import org.elis.manutenzione.repository.LuogoRepository;
import org.elis.manutenzione.repository.ManutenzioneRepository;
import org.elis.manutenzione.repository.ResidenteRepository;
import org.elis.manutenzione.service.definition.LuogoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementazione del servizio per la gestione dei luoghi.
 * Questa classe gestisce la logica di business per le operazioni relative ai luoghi,
 * come l'aggiunta, la modifica, l'eliminazione e il recupero dei luoghi.
 * Utilizza i repository per l'accesso ai dati.
 */
@Service
public class LuogoServiceImplementation implements LuogoService {

    private final LuogoRepository luogoRepository;

    private final ResidenteRepository residenteRepository;

    private final ManutenzioneRepository manutenzioneRepository;

    /**
     * Costruttore della classe.
     * Inizializza i repository necessari per la gestione dei luoghi.
     *
     * @param manutenzioneRepository Il repository per la gestione delle manutenzioni.
     * @param luogoRepository      Il repository per la gestione dei luoghi.
     * @param residenteRepository    Il repository per la gestione dei residenti.
     */
    public LuogoServiceImplementation(ManutenzioneRepository manutenzioneRepository, LuogoRepository luogoRepository, ResidenteRepository residenteRepository) {
        this.luogoRepository = luogoRepository;
        this.residenteRepository = residenteRepository;
        this.manutenzioneRepository = manutenzioneRepository;
    }

    /**
     * {@inheritDoc}
     * Aggiunge un nuovo luogo nel sistema.
     * Questo metodo gestisce la creazione di nuovi luoghi (comuni, stanze o bagni),
     * associa i residenti ai luoghi (se necessario), e salva le modifiche nel database.
     *
     * @param request DTO contenente i dati del luogo da aggiungere.
     * @return L'oggetto Luogo appena creato e salvato.
     * @throws BadRequestException Se il luogo esiste già, se i dati non sono validi (es. nucleo mancante per i luoghi non comuni),
     *                              o se il tipo non è valido.
     */
    @Override
    public Luogo aggiungiLuogo(AggiungiLuogoRequest request) {
        if (!request.getTipo().equals(Tipo.LuogoComune.toString())) {
            if (request.getNucleo() == null || request.getNucleo().isEmpty()) {
                throw new BadRequestException("Devi inserire il nucleo");
            }
        }

        Luogo prova = luogoRepository.findByNomeAndTipoAndNucleo(request.getNome(), Tipo.valueOf(request.getTipo()), request.getNucleo());
        if (prova != null) {
            throw new BadRequestException("Luogo già esistente");
        }

        Luogo luogo = new LuogoBuilder()
                .nome(request.getNome())
                .nucleo(request.getNucleo())
                .tipo(Tipo.valueOf(request.getTipo()))
                .piano(request.getPiano())
                .capienza(request.getCapienza())
                .build();

        luogoRepository.save(luogo);

        if (luogo.getTipo() == Tipo.LuogoComune) {
            luogo.setCapienza(0);
            luogo.setNucleo(null);
        } else if (luogo.getTipo() == Tipo.Bagno) {
            List<Residente> residenti = residenteRepository.findByLuoghiNucleoNome(luogo.getNucleo());
            luogo.setResidenti(residenti);
            for (Residente residente : residenti) {
                List<Luogo> luoghi = residente.getLuoghi();
                luoghi.add(luogo);
                residente.setLuoghi(luoghi);
                residenteRepository.save(residente);
            }
        }
        luogoRepository.save(luogo);
        return luogo;
    }


    /**
     * {@inheritDoc}
     * Recupera la lista di tutti i luoghi presenti nel sistema.
     *
     * @return La lista di tutti gli oggetti Luogo.
     */
    @Override
    public List<Luogo> getLuoghi() {
        List<Luogo> luoghi= luogoRepository.findAll();
        if (luoghi.isEmpty()) {
            throw new NoContentException("Nessun luogo presente");
        }
        return luoghi;
    }

    /**
     * {@inheritDoc}
     * Recupera la lista di tutti i luoghi comuni.
     *
     * @return La lista di tutti gli oggetti Luogo di tipo LuogoComune.
     */
    @Override
    public List<Luogo> getLuoghiComuni() {
        List<Luogo> luoghi = luogoRepository.findAllByTipo(Tipo.LuogoComune);
        if (luoghi.isEmpty()) {
            throw new NoContentException("Nessun luogo comune presente");
        }
        return luoghi;
    }

    /**
     * {@inheritDoc}
     * Recupera la lista di tutte le stanze.
     *
     * @return La lista di tutti gli oggetti Luogo di tipo Stanza.
     */
    @Override
    public List<Luogo> getStanze() {
        List<Luogo> luoghi = luogoRepository.findAllByTipo(Tipo.Stanza);
        if (luoghi.isEmpty()) {
            throw new NoContentException("Nessuna stanza presente");
        }
        return luoghi;
    }

    /**
     * {@inheritDoc}
     * Recupera la lista di tutti i bagni.
     *
     * @return La lista di tutti gli oggetti Luogo di tipo Bagno.
     */
    @Override
    public List<Luogo> getBagni() {
        List<Luogo> luoghi = luogoRepository.findAllByTipo(Tipo.Bagno);
        if (luoghi.isEmpty()) {
            throw new NoContentException("Nessun bagno presente");
        }
        return luoghi;
    }

    /**
     * {@inheritDoc}
     * Modifica la capienza di una stanza specifica.
     *
     * @param request DTO contenente l'id della stanza e la nuova capienza.
     * @return L'oggetto Luogo (stanza) modificato.
     * @throws NoContentException  Se la stanza non viene trovata.
     * @throws BadRequestException Se il luogo non è una stanza o se la capienza è inferiore al numero di residenti.
     */
    @Override
    public Luogo modificaStanza(ModificaStanzaRequest request) {
        Luogo luogo = luogoRepository.findById(request.getIdStanza());
        if (luogo == null) {
            throw new NoContentException("Stanza non esistente");
        }
        if (luogo.getTipo() != Tipo.Stanza) {
            throw new BadRequestException("Il luogo non è una stanza");
        }
        if (luogo.getResidenti().size() > request.getCapienza()) {
            throw new BadRequestException("La capienza non può essere inferiore al numero di residenti");
        }
        luogo.setCapienza(request.getCapienza());
        luogoRepository.save(luogo);
        return luogo;
    }

    /**
     * {@inheritDoc}
     * Recupera la lista dei luoghi associati ad un residente specifico.
     *
     * @param r Il residente di cui si vogliono recuperare i luoghi.
     * @return La lista di tutti gli oggetti Luogo associati al residente.
     */
    @Override
    public Luogo getStanzaByResidente(Residente r) {
        return r.getLuoghi().stream()
                .filter(luogo -> luogo.getTipo() == Tipo.Stanza)
                .findFirst()
                .orElseThrow(() -> new NoContentException("Stanza non trovata per il residente"));
    }

    /**
     * {@inheritDoc}
     * Elimina un luogo specifico dal sistema.
     * Questo metodo gestisce anche la rimozione dell'associazione tra il luogo e i residenti.
     * Inoltre, verifica se ci sono manutenzioni in corso nel luogo.
     *
     * @param request DTO contenente l'id del luogo da eliminare.
     * @return true se l'operazione di eliminazione è completata con successo, false altrimenti.
     * @throws NoContentException  Se il luogo non viene trovato.
     * @throws BadRequestException Se il luogo è un bagno, o se ci sono manutenzioni in corso per quel luogo.
     */
    @Override
    public boolean eliminaLuogo(EliminaLuogoRequest request) {
        Luogo luogo = luogoRepository.findById(request.getLuogoId());
        if (luogo == null) {
            throw new NoContentException("Luogo non esistente");
        }
        if (luogo.getTipo() == Tipo.Bagno) {
            throw new BadRequestException("Impossibile eliminare un bagno");
        } else {
            //rimuove l'associazione tra residente e luogo
            List<Residente> residenti = luogo.getResidenti();
            for (Residente residente : residenti) {
                List<Luogo> luoghiResidente = residente.getLuoghi();
                luoghiResidente.remove(luogo);
                residente.setLuoghi(luoghiResidente);
                residenteRepository.save(residente);
            }
            luogo.setResidenti(null);
        }
        //trovo le manutenzioni che riguardano il luogo e non terminate
        List<Manutenzione> manutenzioni = manutenzioneRepository.findByLuogoIdAndStatoManutenzione(luogo.getId());
        if (!manutenzioni.isEmpty()) {
            throw new BadRequestException("Impossibile eliminare un luogo con manutenzioni in corso");
        }
        //elimina il luogo
        luogoRepository.delete(luogo);
        return true;
    }
}