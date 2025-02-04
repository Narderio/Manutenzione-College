package org.elis.manutenzione.service.implementation;

import org.elis.manutenzione.handler.BadRequestException;
import org.elis.manutenzione.builderPattern.AdminBuilder;
import org.elis.manutenzione.builderPattern.ManutentoreBuilder;
import org.elis.manutenzione.builderPattern.ResidenteBuilder;
import org.elis.manutenzione.builderPattern.SupervisoreBuilder;
import org.elis.manutenzione.dto.request.*;
import org.elis.manutenzione.handler.NoContentException;
import org.elis.manutenzione.model._enum.Ruolo;
import org.elis.manutenzione.model._enum.Tipo;
import org.elis.manutenzione.model.entity.*;
import org.elis.manutenzione.repository.*;
import org.elis.manutenzione.service.definition.LuogoService;
import org.elis.manutenzione.service.definition.MailSenderService;
import org.elis.manutenzione.service.definition.UtenteService;
import org.elis.manutenzione.statePattern.Richiesto;
import org.elis.manutenzione.statePattern.Riparato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementazione del servizio per la gestione degli utenti.
 * Questa classe gestisce la logica di business per le operazioni relative agli utenti,
 * come la registrazione, il login, l'eliminazione, l'assegnazione e il recupero password.
 * Utilizza i repository per l'accesso ai dati e i servizi per la gestione dei luoghi e l'invio di email.
 */
@Service
public class UtenteServiceImplementation implements UtenteService {

    @Autowired
    LuogoService luogoService;

    @Autowired
    private MailSenderService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UtenteRepository utenteRepo;
    private final ManutenzioneRepository manutenzioneRepo;
    private final ManutentoreRepository manutentoreRepo;
    private final AdminRepository adminRepo;
    private final SupervisoreRepository supervisoreRepo;
    private final ResidenteRepository residenteRepo;
    @Autowired
    private LuogoRepository luogoRepository;

    /**
     * Costruttore della classe.
     * Inizializza le dipendenze necessarie per la gestione degli utenti.
     *
     * @param utenteRepo        Il repository per la gestione degli utenti generici.
     * @param manutenzioneRepo   Il repository per la gestione delle manutenzioni.
     * @param manutentoreRepo     Il repository per la gestione dei manutentori.
     * @param adminRepo        Il repository per la gestione degli amministratori.
     * @param supervisoreRepo   Il repository per la gestione dei supervisori.
     * @param residenteRepo      Il repository per la gestione dei residenti.
     */
    public UtenteServiceImplementation(UtenteRepository utenteRepo, ManutenzioneRepository manutenzioneRepo, ManutentoreRepository manutentoreRepo, AdminRepository adminRepo, SupervisoreRepository supervisoreRepo, ResidenteRepository residenteRepo) {
        this.utenteRepo = utenteRepo;
        this.manutenzioneRepo = manutenzioneRepo;
        this.manutentoreRepo = manutentoreRepo;
        this.adminRepo = adminRepo;
        this.supervisoreRepo = supervisoreRepo;
        this.residenteRepo = residenteRepo;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per registrare un nuovo utente nel sistema.
     *
     * @param registrazioneRequest DTO contenente i dati dell'utente da registrare.
     * @return L'oggetto Utente appena creato e salvato.
     * @throws BadRequestException Se l'utente esiste già, è bloccato o se il ruolo non è valido.
     *                             o se il tipo di manutentore non viene specificato
     */
    @Override
    public Utente registrazione(RegistrazioneRequest registrazioneRequest) {
        Utente prova = utenteRepo.findByEmail(registrazioneRequest.getEmail());
        if (prova != null)
            if (prova.isBloccato())
                throw new BadRequestException("utente bloccato");
            else
                throw new BadRequestException("utente già registrato");
        if (registrazioneRequest.getRuolo() == Ruolo.Manutentore && (registrazioneRequest.getTipoManutentore() == null || registrazioneRequest.getTipoManutentore().isEmpty()))
            throw new BadRequestException("Inserire il tipo di manutentore");

        String uuid = UUID.randomUUID().toString();
        String passwordGenerata = uuid.replace("-", "").substring(0, 12);
        String passwordGenerataHashata = passwordEncoder.encode(passwordGenerata);

        Utente u;
        switch (registrazioneRequest.getRuolo()) {
            case Admin:
                u = new AdminBuilder().nome(registrazioneRequest.getNome()).cognome(registrazioneRequest.getCognome()).email(registrazioneRequest.getEmail()).password(passwordGenerataHashata).dataDiNascita(registrazioneRequest.getDataDiNascita()).build();
                break;
            case Manutentore:
                u = new ManutentoreBuilder().nome(registrazioneRequest.getNome()).cognome(registrazioneRequest.getCognome()).email(registrazioneRequest.getEmail()).password(passwordGenerataHashata).dataDiNascita(registrazioneRequest.getDataDiNascita()).tipoManutentore(registrazioneRequest.getTipoManutentore()).build();
                break;
            case Supervisore:
                u = new SupervisoreBuilder().nome(registrazioneRequest.getNome()).cognome(registrazioneRequest.getCognome()).email(registrazioneRequest.getEmail()).password(passwordGenerataHashata).dataDiNascita(registrazioneRequest.getDataDiNascita()).build();
                break;
            case Residente:
                u = new ResidenteBuilder().nome(registrazioneRequest.getNome()).cognome(registrazioneRequest.getCognome()).email(registrazioneRequest.getEmail()).password(passwordGenerataHashata).dataDiNascita(registrazioneRequest.getDataDiNascita()).build();
                break;
            default:
                throw new BadRequestException("Ruolo non valido");
        }

        mailService.registrazione(u, passwordGenerata);

        // Salva l'utente nel repository corretto
        if (u instanceof Admin) {
            adminRepo.save((Admin) u);
        } else if (u instanceof Manutentore) {
            manutentoreRepo.save((Manutentore) u);
        } else if (u instanceof Supervisore) {
            supervisoreRepo.save((Supervisore) u);
        } else {
            Residente residente = (Residente) u;
            residenteRepo.save(residente);

        }
        return u;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per recuperare tutti gli utenti non bloccati.
     *
     * @return Una lista di tutti gli utenti non bloccati.
     */
    @Override
    public List<Utente> getUtenti(Utente u) {
        List<Utente> utenti=utenteRepo.findAllByBloccatoIsFalse();
        utenti.removeIf(utente -> utente.getEmail().equals(u.getEmail()));
        if(u instanceof Residente) {
            utenti.removeIf(utente -> utente instanceof Admin);
            utenti.removeIf(utente -> utente instanceof Manutentore);
        }
        else if(u instanceof Manutentore){
            utenti.removeIf(utente -> utente instanceof Admin);
            utenti.removeIf(utente -> utente instanceof Residente);
        }
        else if(u instanceof Supervisore){
            utenti.removeIf(utente -> utente instanceof Admin);
        }
        if(utenti.isEmpty())
            throw new NoContentException("Nessun utente presente");
        return utenti;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per recuperare tutti i supervisori non bloccati.
     *
     * @return Una lista di tutti i supervisori non bloccati.
     */
    @Override
    public List<Supervisore> getSupervisori() {
        List<Supervisore> s= supervisoreRepo.findAllByBloccatoIsFalse();
        if(s.isEmpty())
            throw new NoContentException("Nessun supervisore presente");
        return s;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per recuperare tutti i residenti non bloccati.
     *
     * @return Una lista di tutti i residenti non bloccati.
     */
    @Override
    public List<Residente> getResidenti() {
        List<Residente> r= residenteRepo.findAllByBloccatoIsFalse();
        if(r.isEmpty())
            throw new NoContentException("Nessun residente presente");
        return r;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per eliminare un utente tramite email (impostando il campo bloccato a true).
     *
     * @param request DTO contenente l'email dell'utente da eliminare.
     * @return true se l'operazione ha avuto successo, false altrimenti.
     * @throws NoContentException Se l'utente non viene trovato.
     * @throws BadRequestException Se si tenta di eliminare l'ultimo admin o se l'utente da eliminare è un residente
     *                               con manutenzioni in corso.
     */
    @Override
    public boolean deleteUtente(EliminaUtenteRequest request) {
        Utente u = utenteRepo.findByEmailAndBloccatoIsFalse(request.getEmail()).orElse(null);
        if (u == null)
            throw new NoContentException("utente non trovato");
        if (u instanceof Admin && adminRepo.findAllByBloccatoIsFalse().size() <= 1) {
            throw new BadRequestException("non puoi eliminare l'ultimo admin");
        }
        if (u instanceof Manutentore) {
            List<Manutenzione> manutenzioniAssegnate = manutenzioneRepo.findAllByManutentore(u);
            for (Manutenzione manutenzione : manutenzioniAssegnate) {
                manutenzione.setManutentore(null);
                manutenzione.setStato(new Richiesto());
                manutenzione.setPriorita(0);
                manutenzione.setDataPrevista(null);
                manutenzioneRepo.save(manutenzione);
            }
        }

        if(u instanceof Residente){
            Residente r = (Residente) u;
            List<Luogo> luoghi = r.getLuoghi();
            for(Luogo l: luoghi){
                l.getResidenti().remove(r);
                luogoRepository.save(l);
            }
            r.getLuoghi().removeAll(luoghi);
            residenteRepo.save(r);
        }

        u.setBloccato(true);
        utenteRepo.save(u);
        return true;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per effettuare il login di un utente.
     *
     * @param request DTO contenente le credenziali dell'utente.
     * @return L'oggetto Utente se le credenziali sono corrette, altrimenti lancia eccezione.
     * @throws BadRequestException Se le credenziali sono errate.

    @Override
    public Utente login(LoginRequest request) {
        Utente u = utenteRepo.findByEmailAndPasswordAndBloccatoIsFalse(request.getEmail(), request.getPassword());
        if (u == null)
            throw new BadRequestException("credenziali errate");
        return u;
    }
    */

    /**
     * {@inheritDoc}
     * Implementazione del metodo per aggiungere un residente in una stanza specifica.
     *
     * @param request DTO contenente l'email del residente e il nome della stanza.
     * @return true se l'operazione ha avuto successo, false altrimenti.
     * @throws NoContentException Se l'utente o la stanza non vengono trovati.
     * @throws BadRequestException Se l'utente non è un residente, se la stanza è piena o se il residente risiede già in una stanza.
     */
    @Override
    public boolean aggiungiResidenteInStanza(AggiungiResidenteInStanzaRequest request) {
        Utente u = utenteRepo.findByEmailAndBloccatoIsFalse(request.getEmail()).orElse(null);
        if (u == null)
            throw new NoContentException("utente non trovato");
        if (!(u instanceof Residente))
            throw new BadRequestException("l'utente non può risiedere in una stanza");
        Luogo s = luogoRepository.findByNomeAndTipo(request.getStanza(), Tipo.Stanza);
        if (s == null)
            throw new NoContentException("stanza non trovata");
        if (s.getCapienza() == s.getResidenti().size())
            throw new BadRequestException("stanza piena");
        Residente r = (Residente) u;
        List<Luogo> luoghi = r.getLuoghi();
        if (luoghi.stream().anyMatch(l -> l.getTipo() == Tipo.Stanza))
            throw new BadRequestException("l'utente risiede già in una stanza");
        String nucleo = s.getNucleo();
        Luogo bagno = luogoRepository.findByNomeAndTipoAndNucleo("Bagno", Tipo.Bagno, nucleo);
        luoghi.add(s);
        if(bagno!=null){
            luoghi.add(bagno);
            bagno.getResidenti().add(r);
            luogoRepository.save(bagno);
        }
        r.setLuoghi(luoghi);
        residenteRepo.save(r);
        return true;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per recuperare tutti i manutentori non bloccati.
     *
     * @return Una lista di tutti i manutentori non bloccati.
     */
    @Override
    public List<Manutentore> getManutentori() {
        List<Manutentore> manutentori= manutentoreRepo.findAllByBloccatoIsFalse();
        if(manutentori.isEmpty())
            throw new NoContentException("Nessun manutentore presente");
        return manutentori;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per recuperare tutti gli amministratori non bloccati.
     *
     * @return Una lista di tutti gli amministratori non bloccati.
     */
    @Override
    public List<Admin> getAdmin() {
        List<Admin>admins= adminRepo.findAllByBloccatoIsFalse();
        if(admins.isEmpty())
            throw new NoContentException("Nessun admin presente");
        return admins;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per aggiornare il ruolo di un utente.
     *
     * @param request DTO contenente l'email dell'utente e il nuovo ruolo.
     * @return L'oggetto Utente con il nuovo ruolo.
     * @throws NoContentException Se l'utente non viene trovato.
     * @throws BadRequestException Se si tenta di cambiare il ruolo dell'ultimo admin o se il ruolo non è valido.
     */
    @Override
    public Utente aggiornaRuolo(AggiornaRuoloRequest request) {
        Utente utente = utenteRepo.findByEmailAndBloccatoIsFalse(request.getEmail()).orElse(null);
        if(utente == null)
            throw new NoContentException("utente non trovato");
        if (utente instanceof Residente && request.getRuoloNuovo() == Ruolo.Residente)
            throw new BadRequestException("L'utente ha già quel ruolo");
        else if (utente instanceof Manutentore && request.getRuoloNuovo() == Ruolo.Manutentore)
            throw new BadRequestException("L'utente ha già quel ruolo");
        else if (utente instanceof Admin && request.getRuoloNuovo() == Ruolo.Admin)
            throw new BadRequestException("L'utente ha già quel ruolo");
        else if (utente instanceof Supervisore && request.getRuoloNuovo() == Ruolo.Supervisore)
            throw new BadRequestException("L'utente ha già quel ruolo");
        // Verifica che non sia l'ultimo admin
        List<Admin> utentiAdmin = adminRepo.findAllByBloccatoIsFalse();
        if (utentiAdmin.size() <= 1 && utentiAdmin.contains(utente) && request.getRuoloNuovo() != Ruolo.Admin)
            throw new BadRequestException("non puoi cambiare il ruolo dell'ultimo admin");

        if (utente instanceof Manutentore) {
                List<Manutenzione> manutenzioni = manutenzioneRepo.findAllByManutentore((Manutentore) utente);
                for (Manutenzione m : manutenzioni) {
                    if (m.getStato() instanceof Riparato)
                        m.setManutentore(null);
                    else {
                        m.setManutentore(null);
                        m.setPriorita(0);
                        m.setStato(new Richiesto());
                        m.setDataPrevista(null);
                        m.setDataRiparazione(null);
                        manutenzioneRepo.save(m);
                    }
                }
        }
        utenteRepo.updateRuoloUtente(request.getRuoloNuovo().toString(), request.getEmail());
        utenteRepo.save(utente);

        return utente;
    }



    /**
     * {@inheritDoc}
     * Implementazione del metodo per recuperare un utente tramite email e verifica se non è bloccato.
     *
     * @param email L'email dell'utente da recuperare.
     * @return L'oggetto Utente se trovato, null altrimenti.
     */
    @Override
    public Utente getUtenteByEmailAndBloccatoIsFalse(String email) {
        return utenteRepo.findByEmailAndBloccatoIsFalse(email).orElse(null);
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per eliminare un residente da una stanza.
     *
     * @param request DTO contenente l'email del residente da eliminare.
     * @return true se l'operazione ha avuto successo, false altrimenti.
     * @throws NoContentException Se l'utente o la stanza non vengono trovati.
     */
    @Override
    public boolean eliminaResidenteDaStanza(EliminaResidenteDaStanzaRequest request) {
        Residente r = residenteRepo.findByEmailAndBloccatoIsFalse(request.getEmail());
        if (r == null)
            throw new NoContentException("utente non trovato");
        Luogo s = r.getLuoghi().stream().filter(l -> l.getTipo().equals(Tipo.Stanza)).findFirst().orElse(null);
        if (s == null)
            throw new NoContentException("L'utente non è in questa stanza");
        List<Luogo> luoghi = r.getLuoghi();
        s.getResidenti().remove(r);
        luogoRepository.save(s);
        Luogo bagno = luogoRepository.findByNomeAndTipoAndNucleo("Bagno", Tipo.Bagno, s.getNucleo());
        if(bagno!=null){
            bagno.getResidenti().remove(r);
            luogoRepository.save(bagno);
            luoghi.remove(bagno);
        }
        luoghi.remove(s);
        r.setLuoghi(luoghi);
        residenteRepo.save(r);
        return true;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per richiedere una nuova password dimenticata.
     *
     * @param email dell'utente che ha dimenticato la password.
     * @return true se l'operazione ha avuto successo, false altrimenti.
     * @throws BadRequestException Se l'utente non viene trovato.
     */
    @Override
    public boolean passwordDimenticata(String email) {
        Utente u = utenteRepo.findByEmailAndBloccatoIsFalse(email).orElse(null);
        if (u == null)
            throw new BadRequestException("utente non trovato");

        // Genera una nuova password casuale
        String uuid = UUID.randomUUID().toString();
        String passwordGenerata = uuid.replace("-", "").substring(0, 12);
        String passwordGenerataHashata = passwordEncoder.encode(passwordGenerata);

        mailService.passwordDimenticata(email, passwordGenerata);

        // Aggiorna la password dell'utente e salva le modifiche
        u.setPassword(passwordGenerataHashata);
        utenteRepo.save(u);
        return true;
    }


    /**
     * {@inheritDoc}
     * Implementazione del metodo per disiscrivere un residente dalle email di notifica.
     * @param r Il residente da disiscrivere
     * @return true se l'operazione ha avuto successo, false altrimenti.
     */
    @Override
    public boolean disiscriviEmail(Residente r){
        r.setEmailBloccate(true);
        residenteRepo.save(r);
        return true;
    }


    /**
     * {@inheritDoc}
     * Implementazione del metodo per iscrivere un residente alle email di notifica.
     * @param r Il residente da iscrivere
     * @return true se l'operazione ha avuto successo, false altrimenti.
     */
    @Override
    public boolean iscriviEmail(Residente r){
        r.setEmailBloccate(false);
        residenteRepo.save(r);
        return true;
    }
}