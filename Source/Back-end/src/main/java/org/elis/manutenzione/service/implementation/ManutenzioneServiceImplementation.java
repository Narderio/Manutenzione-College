package org.elis.manutenzione.service.implementation;

import org.elis.manutenzione.dto.response.GetManutenzioneDTO;
import org.elis.manutenzione.dto.response.GetManutenzioniDTO;
import org.elis.manutenzione.dto.response.ManutenzioneDTO;
import org.elis.manutenzione.handler.BadRequestException;
import org.elis.manutenzione.builderPattern.ManutenzioneBuilder;
import org.elis.manutenzione.dto.request.*;
import org.elis.manutenzione.handler.ForbiddenException;
import org.elis.manutenzione.handler.NoContentException;
import org.elis.manutenzione.mapper.ManutenzioneMapper;
import org.elis.manutenzione.model.entity.*;
import org.elis.manutenzione.repository.*;
import org.elis.manutenzione.service.definition.MailSenderService;
import org.elis.manutenzione.service.definition.ManutenzioneService;
import org.elis.manutenzione.statePattern.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementazione del servizio per la gestione delle manutenzioni.
 * Questa classe gestisce la logica di business per le operazioni relative alle manutenzioni,
 * come la richiesta, il filtraggio, l'accettazione, la riparazione, il rifiuto e il feedback.
 * Utilizza repository per l'accesso ai dati e un servizio per l'invio di email.
 */
@Service
public class ManutenzioneServiceImplementation implements ManutenzioneService {

    private final ManutenzioneRepository manutenzioneRepository;
    private final LuogoRepository luogoRepository;
    private final ManutentoreRepository manutentoreRepository;
    private final CriteriaManutenzioneRepository criteriaManutenzioneRepository;

    @Autowired
    private MailSenderService mailService;
    @Autowired
    private ManutenzioneMapper manutenzioneMapper;

    /**
     * Costruttore della classe.
     * Inizializza le dipendenze necessarie per la gestione delle manutenzioni.
     *
     * @param manutenzioneRepository         Il repository per la gestione delle manutenzioni.
     * @param luogoRepository                Il repository per la gestione dei luoghi.
     * @param criteriaManutenzioneRepository Il repository per le query di filtraggio manutenzioni.
     * @param manutentoreRepository          Il repository per la gestione dei manutentori.
     */
    public ManutenzioneServiceImplementation(ManutenzioneRepository manutenzioneRepository, LuogoRepository luogoRepository, CriteriaManutenzioneRepository criteriaManutenzioneRepository, ManutentoreRepository manutentoreRepository) {
        this.manutenzioneRepository = manutenzioneRepository;
        this.luogoRepository = luogoRepository;
        this.criteriaManutenzioneRepository = criteriaManutenzioneRepository;
        this.manutentoreRepository = manutentoreRepository;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per richiedere una nuova manutenzione.
     *
     * @param request DTO contenente i dati della manutenzione da richiedere.
     * @param r       Il residente che richiede la manutenzione.
     * @return L'oggetto Manutenzione appena creato e salvato.
     * @throws BadRequestException Se esiste già una manutenzione con la stessa descrizione e luogo.
     * @throws NoContentException   Se il luogo specificato non viene trovato.
     */
    @Override
    public Manutenzione richiediManutenzione(RichiediManutenzioneRequest request, Residente r) {
        Manutenzione prova = manutenzioneRepository.findByDescrizioneAndLuogoId(request.getDescrizione().toLowerCase(), request.getIdLuogo());
        if (prova != null) {
            throw new BadRequestException("Manutenzione già richiesta");
        }
        Luogo luogo = luogoRepository.findById(request.getIdLuogo());
        if (luogo == null) {
            throw new NoContentException("Luogo non trovato");
        }
        Manutenzione manutenzione = new ManutenzioneBuilder()
                .utenteRichiedente(r)
                .descrizione(request.getDescrizione().toLowerCase())
                .luogo(luogo)
                .stato(new Richiesto())
                .dataRichiesta(LocalDate.now())
                .nome(request.getNome())
                .immagine(request.getImmagine())
                .build();
        manutenzioneRepository.save(manutenzione);

        mailService.richiediManutenzione();

        return manutenzione;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per filtrare una manutenzione e assegnarla ad un manutentore.
     *
     * @param request DTO contenente i dati per il filtraggio della manutenzione.
     * @return L'oggetto Manutenzione dopo il filtraggio e l'assegnazione.
     * @throws NoContentException   Se la manutenzione o il manutentore non vengono trovati.
     * @throws BadRequestException Se la manutenzione non è nello stato corretto per essere filtrata.
     */
    @Override
    public Manutenzione filtraManutenzione(FiltraManutenzioneRequest request) {
        Manutenzione m = manutenzioneRepository.findById(request.getIdManutenzione());
        if (m == null) {
            throw new NoContentException("Manutenzione non trovata");
        }
        if (!(m.getStato() instanceof Richiesto) && !(m.getStato() instanceof Rifiutato)) {
            throw new BadRequestException("La manutenzione non è da filtrare");
        }
        Manutentore manutentore = manutentoreRepository.findByEmailAndBloccatoIsFalse(request.getEmailManutentore());
        if (manutentore == null) {
            throw new NoContentException("Manutentore non trovato");
        }

        mailService.filtraManutenzione(request.getEmailManutentore());

        m.setStato(new DaRiparare());
        m.setManutentore(manutentore);
        m.setPriorita(request.getPriorita());
        if (request.getNomeManutenzione() != null)
            m.setNome(request.getNomeManutenzione());
        manutenzioneRepository.save(m);
        return m;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per accettare una manutenzione.
     *
     * @param request DTO contenente i dati per l'accettazione della manutenzione.
     * @param u       Il manutentore che accetta la manutenzione.
     * @return L'oggetto Manutenzione dopo l'accettazione.
     * @throws NoContentException   Se la manutenzione non viene trovata.
     * @throws BadRequestException Se la manutenzione non è nello stato corretto per essere accettata.
     * @throws ForbiddenException  Se la manutenzione è assegnata a un altro manutentore.
     */
    @Override
    public Manutenzione accettaManutenzione(AccettaManutenzioneRequest request, Manutentore u) {
        Manutenzione m = manutenzioneRepository.findById(request.getIdManutenzione());
        if (m == null) {
            throw new NoContentException("Manutenzione non trovata");
        }
        if (!(m.getStato() instanceof DaRiparare)) {
            throw new BadRequestException("La manutenzione non è in uno stato accettabile");
        }
        if (!m.getManutentore().getEmail().equals(u.getEmail())) {
            throw new ForbiddenException("La manutenzione è stata assegnata a un altro manutentore");
        }
        Manutenzione m2 = manutenzioneRepository.findByLuogoAndDataPrevista(m.getLuogo(), request.getDataPrevista());
        if(m2!=null){
            throw new BadRequestException("In questa data è già prevista una manutenzione per questo luogo");
        }
        Manutenzione m3= manutenzioneRepository.findByManutentoreAndDataPrevista(u,request.getDataPrevista());
        if(m3!=null){
            throw new BadRequestException("Sei già impegnato in un'altra manutenzione in questa data!");
        }
        m.setStato(new Accettato());
        m.setDataPrevista(request.getDataPrevista());
        manutenzioneRepository.save(m);

        // Invia email ai residenti per notificare l'accettazione
        List<Residente> residenti = m.getLuogo().getResidenti();
        residenti.removeIf(Utente::getEmailBloccate);

        mailService.accettaManutenzione(residenti, request.getDataPrevista());

        return m;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per segnare una manutenzione come riparata.
     *
     * @param request     DTO contenente i dati per segnare la manutenzione come riparata.
     * @param manutentore Il manutentore che ha riparato la manutenzione.
     * @return L'oggetto Manutenzione dopo la riparazione.
     * @throws BadRequestException Se la manutenzione non viene trovata o non è nello stato corretto.
     * @throws ForbiddenException  Se la manutenzione è assegnata a un altro manutentore.
     */
    @Override
    public Manutenzione riparaManutenzione(RiparaManutenzioneRequest request, Manutentore manutentore) {
        // Verifica se la manutenzione esiste
        Manutenzione m = manutenzioneRepository.findById(request.getIdManutenzione());
        if (m == null) {
            throw new BadRequestException("manutenzione non trovata");
        }
        // Verifica se la manutenzione è in stato "Accettato"
        if (!(m.getStato() instanceof Accettato)) {
            throw new BadRequestException("La manutenzione non è da riparare");
        }
        // Verifica se la manutenzione è assegnata al manutentore corrente
        if (!m.getManutentore().getEmail().equals(manutentore.getEmail())) {
            throw new ForbiddenException("La manutenzione è assegnata a un altro manutentore");
        }
        // Aggiorna lo stato della manutenzione a "Riparato" e imposta la data di riparazione
        m.setStato(new Riparato());
        m.setDataRiparazione(LocalDate.now());
        manutenzioneRepository.save(m);

        mailService.riparaManutenzione(m.getUtenteRichiedente());

        return m;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per rifiutare una manutenzione.
     *
     * @param request DTO contenente i dati per il rifiuto della manutenzione.
     * @param u       L'utente (manutentore o supervisore) che rifiuta la manutenzione.
     * @return L'oggetto Manutenzione dopo il rifiuto.
     * @throws NoContentException   Se la manutenzione non viene trovata.
     * @throws BadRequestException Se la manutenzione non è nello stato corretto per essere rifiutata.
     * @throws ForbiddenException  Se la manutenzione è assegnata a un altro manutentore (nel caso di un manutentore che rifiuta).
     */
    @Override
    public Manutenzione rifiutaManutenzione(RifiutaManutenzioneRequest request, Utente u) {
        // Verifica se la manutenzione esiste
        Manutenzione m = manutenzioneRepository.findById(request.getIdManutenzione());
        if (m == null) {
            throw new NoContentException("manutenzione non trovata");
        }
        // Verifica se la manutenzione è in stato "DaRiparare" o "Accettato"
        if (!(m.getStato() instanceof DaRiparare) && !(m.getStato() instanceof Accettato) && !(m.getStato() instanceof Richiesto)) {
            throw new BadRequestException("La manutenzione non può essere rifiutata");
        }
        if (u instanceof Manutentore) {
            Manutentore manutentore = (Manutentore) u;
            // Verifica se la manutenzione è assegnata al manutentore corrente
            if (!m.getManutentore().getEmail().equals(manutentore.getEmail())) {
                throw new ForbiddenException("La manutenzione è assegnata a un altro manutentore");
            }
        }
        // Aggiorna lo stato della manutenzione a "Rifiutato"
        m.setStato(new Rifiutato());
        manutenzioneRepository.save(m);

        mailService.rifiutaManutenzione(u);

        return m;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per fornire un feedback su una manutenzione riparata.
     *
     * @param request DTO contenente i dati del feedback.
     * @param r       Il residente che fornisce il feedback.
     * @return true se il feedback è stato registrato con successo, false altrimenti.
     * @throws NoContentException   Se la manutenzione non viene trovata.
     * @throws BadRequestException Se la manutenzione non è nello stato corretto per ricevere feedback o se manca la descrizione del problema.
     * @throws ForbiddenException  Se la manutenzione è stata richiesta da un altro residente.
     */
    @Override
    public boolean feedbackManutenzione(FeedbackManutenzioneRequest request, Residente r) {
        // Verifica se la manutenzione esiste
        Manutenzione m = manutenzioneRepository.findById(request.getIdManutenzione());
        if (m == null) {
            throw new NoContentException("manutenzione non trovata");
        }
        // Verifica se la manutenzione è in stato "Riparato"
        if (!(m.getStato() instanceof Riparato)) {
            throw new BadRequestException("La manutenzione non è stata ancora riparata");
        }
        // Verifica se la manutenzione è stata richiesta dal residente corrente
        if (!(m.getUtenteRichiedente().getEmail().equals(r.getEmail()))) {
            throw new ForbiddenException("La manutenzione è stata richiesta da un altro residente");
        }
        // Se il feedback indica che il problema non è risolto, aggiorna la descrizione della manutenzione
        // e reimposta lo stato a "Richiesto"
        if (!request.isRisolto()) {
            if (request.getProblema() == null || request.getProblema().isEmpty()) {
                throw new BadRequestException("devi inserire la descrizione del problema");
            }
            String feedback = m.getDescrizione() + ". Feedback del richiedente dopo la riparazione:" + request.getProblema();
            m.setDescrizione(feedback);
            m.setStato(new Richiesto());
            m.setDataPrevista(null);
            m.setDataRiparazione(null);
            m.setPriorita(0);
        }
        manutenzioneRepository.save(m);
        return true;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per ottenere una lista di manutenzioni filtrate in base ai criteri specificati.
     *
     * @param request DTO contenente i criteri di filtraggio.
     * @param u       L'utente che sta richiedendo la lista di manutenzioni.
     * @return Una lista di oggetti Manutenzione filtrata.
     */
    @Override
    public List<ManutenzioneDTO> getManutenzioneFiltrata(FiltroManutenzione request, Utente u) {
        return criteriaManutenzioneRepository.getManutenzioneFiltrata(request, u);
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per modificare una manutenzione in stato Richiesto.
     *
     * @param request DTO contenente i dati della manutenzione da modificare.
     * @param r       Il residente che ha richiesto la manutenzione.
     * @return L'oggetto Manutenzione modificato.
     * @throws NoContentException   Se la manutenzione non viene trovata.
     * @throws ForbiddenException  Se la manutenzione è stata richiesta da un altro residente.
     * @throws BadRequestException Se la manutenzione non è nello stato corretto per essere modificata.
     */
    @Override
    public Manutenzione modificaManutenzione(ModificaManutenzioneRequest request, Residente r) {
        Manutenzione m = manutenzioneRepository.findById(request.getIdManutenzione());
        if (m == null)
            throw new NoContentException("Manutenzione non trovata");
        if (!m.getUtenteRichiedente().getEmail().equals(r.getEmail()))
            throw new ForbiddenException("Non puoi modificare una manutenzione richiesta da un altro residente");
        if (!(m.getStato() instanceof Richiesto))
            throw new BadRequestException("Non puoi modificare una manutenzione già filtrata o accettata");
        m.setDescrizione(request.getDescrizione());
        m.setNome(request.getNome());
        m.setImmagine(request.getImmagine());
        manutenzioneRepository.save(m);
        return m;
    }

    public List<GetManutenzioniDTO> getManutenzioni(Utente u) {
        List<Manutenzione> manutenzioni;
        if (u instanceof Residente)
            manutenzioni = manutenzioneRepository.findAllByUtenteRichiedente((Residente) u);
        else if (u instanceof Manutentore)
            manutenzioni = manutenzioneRepository.findAllByManutentore((Manutentore) u);
        else
            manutenzioni = manutenzioneRepository.findAll();
        List<GetManutenzioniDTO> manutenzioniDTO = manutenzioneMapper.toGetManutenzioniDTO(manutenzioni);
        return manutenzioniDTO;
    }

    public GetManutenzioneDTO getManutenzioneById(Long id, Utente u) {
        Manutenzione m = manutenzioneRepository.findById(id).orElse(null);
        if (m == null)
            throw new NoContentException("Manutenzione non trovata");
        if (u instanceof Residente && !m.getUtenteRichiedente().getEmail().equals(u.getEmail()))
            throw new ForbiddenException("Non puoi visualizzare una manutenzione richiesta da un altro residente");
        if(m.getManutentore()!=null)
            if (u instanceof Manutentore && !m.getManutentore().getEmail().equals(u.getEmail()))
                throw new ForbiddenException("Non puoi visualizzare una manutenzione assegnata a un altro manutentore");
        return manutenzioneMapper.toGetManutenzioneDTO(m);
    }
}