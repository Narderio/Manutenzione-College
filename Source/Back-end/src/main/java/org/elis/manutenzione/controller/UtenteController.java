package org.elis.manutenzione.controller;

import jakarta.validation.Valid;
import org.elis.manutenzione.dto.request.*;
import org.elis.manutenzione.dto.response.*;
import org.elis.manutenzione.mapper.UtenteMapper;
import org.elis.manutenzione.model.entity.*;
import org.elis.manutenzione.security.TokenUtil;
import org.elis.manutenzione.service.definition.UtenteService;
import org.elis.manutenzione.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller REST per la gestione degli utenti.
 * Questo controller espone endpoint per la visualizzazione, il login,
 * il cambio password e il recupero password degli utenti.
 * Utilizza il servizio {@link UtenteService} per la logica di business.
 */
@RestController
public class UtenteController {

    private final UtenteService utenteService;
    private final TokenUtil tokenUtil;
    private final UtenteMapper utenteMapper;
    @Autowired
    private AuthServiceImplementation authService;

    /**
     * Costruttore della classe.
     * Inizializza le dipendenze necessarie per la gestione degli utenti.
     *
     * @param utenteService Il servizio per la gestione degli utenti.
     * @param tokenUtil    Il servizio per la generazione e validazione dei token JWT.
     * @param utenteMapper   Il mapper per la conversione tra entità Utente e DTO.
     */
    public UtenteController(UtenteService utenteService, TokenUtil tokenUtil, UtenteMapper utenteMapper) {
        this.utenteService = utenteService;
        this.tokenUtil = tokenUtil;
        this.utenteMapper = utenteMapper;
    }

    /**
     * Endpoint per la visualizzazione di tutti gli utenti.
     *
     * @return ResponseEntity contenente la lista degli utenti in formato DTO o status 404 NOT_FOUND se non ci sono utenti.
     */
    @GetMapping("/all/getUtenti")
    public ResponseEntity<List<GetUtentiResponse>> getUtenti(UsernamePasswordAuthenticationToken upat) {
        Utente ut = (Utente) upat.getPrincipal();
        List<Utente> utenti = utenteService.getUtenti(ut);
        // Converte la lista di entità Utente in una lista di DTO GetUtentiResponse.
        List<GetUtentiResponse> utentiMappati = new ArrayList<>();
        for (Utente u : utenti) {
            utentiMappati.add(utenteMapper.toUtente(u));
        }
        return ResponseEntity.ok(utentiMappati);
    }

    /**
     * Endpoint per la visualizzazione di tutti i residenti.
     *
     * @return ResponseEntity contenente la lista dei residenti in formato DTO o status 404 NOT_FOUND se non ci sono residenti.
     */
    @GetMapping("/getResidenti")
    public ResponseEntity<List<GetResidentiResponse>> getResidenti() {
        List<Residente> residenti = utenteService.getResidenti();

        // Converte la lista di entità Residente in una lista di DTO GetResidentiResponse.
        List<GetResidentiResponse> residentiMappati = new ArrayList<>();
        for (Residente r : residenti) {
            residentiMappati.add(utenteMapper.toResidente(r));
        }
        return ResponseEntity.ok(residentiMappati);
    }

    /**
     * Endpoint per la visualizzazione di tutti i manutentori.
     *
     * @return ResponseEntity contenente la lista dei manutentori in formato DTO o status 404 NOT_FOUND se non ci sono manutentori.
     */
    @GetMapping("/getManutentori")
    public ResponseEntity<List<GetManutentoriResponse>> getManutentori() {
        List<Manutentore> manutentori = utenteService.getManutentori();
        List<GetManutentoriResponse> manutentoriMappati = new ArrayList<>();
        for (Manutentore m : manutentori) {
            manutentoriMappati.add(utenteMapper.toManutentore(m));
        }
        return ResponseEntity.ok(manutentoriMappati);
    }

    /**
     * Endpoint per la visualizzazione di tutti i supervisori.
     *
     * @return ResponseEntity contenente la lista dei supervisori in formato DTO o status 404 NOT_FOUND se non ci sono supervisori.
     */
    @GetMapping("/admin/getSupervisori")
    public ResponseEntity<List<GetSupervisoriResponse>> getSupervisori() {
        List<Supervisore> supervisori = utenteService.getSupervisori();
        List<GetSupervisoriResponse> supervisoriMappati = new ArrayList<>();
        for (Supervisore s : supervisori) {
            supervisoriMappati.add(utenteMapper.toSupervisore(s));
        }
        return ResponseEntity.ok(supervisoriMappati);
    }

    /**
     * Endpoint per la visualizzazione di tutti gli amministratori.
     *
     * @return ResponseEntity contenente la lista degli amministratori in formato DTO o status 404 NOT_FOUND se non ci sono amministratori.
     */
    @GetMapping("/admin/getAdmin")
    public ResponseEntity<List<GetAdminResponse>> getAdmin() {
        List<Admin> admin = utenteService.getAdmin();
        List<GetAdminResponse> adminMappati = new ArrayList<>();
        for (Admin a : admin) {
            adminMappati.add(utenteMapper.toAdmin(a));
        }
        return ResponseEntity.ok(adminMappati);
    }

    /**
     * Endpoint per il login di un utente.
     *
     * @param request DTO contenente le credenziali dell'utente.
     * @return ResponseEntity contenente i dati dell'utente in formato DTO e il token JWT nell'header "Authorization".
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Utente utente = authService.login(request);
        String token = tokenUtil.generaToken(utente);
        LoginResponse response = utenteMapper.toLoginResponse(utente, token);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint per il cambio password di un utente.
     *
     * @param request DTO contenente le password vecchia, nuova e di conferma.
     * @param upat    Token di autenticazione dell'utente che sta effettuando il cambio password.
     * @return ResponseEntity con status 200 OK in caso di successo, status 400 BAD_REQUEST altrimenti.
     */
    @PatchMapping("/cambioPassword")
    public ResponseEntity<Void> cambioPassword(@Valid @RequestBody CambioPasswordRequest request, UsernamePasswordAuthenticationToken upat) {
        Utente u = (Utente) upat.getPrincipal();
        authService.cambioPassword(request, u);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint per la richiesta di una nuova password dimenticata.
     *
     * @param request DTO contenente l'email dell'utente che ha dimenticato la password.
     * @return ResponseEntity con status 200 OK in caso di successo, status 400 BAD_REQUEST altrimenti.
     */
    @PostMapping("passwordDimenticata")
    public ResponseEntity<Void> passwordDimenticata(@Valid @RequestBody PasswordDimenticataRequest request) {
        utenteService.passwordDimenticata(request.getEmail());
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint per la disiscrizione di un residente dalle email di notifica.
     *
     * @param upat Token di autenticazione dell'utente che sta effettuando la disiscrizione.
     * @return ResponseEntity con status 200 OK in caso di successo, status 400 BAD_REQUEST altrimenti.
     */
    @PatchMapping("residente/disinscriviEmail")
    public ResponseEntity<Void> disiscriviEmail(UsernamePasswordAuthenticationToken upat) {
        Residente r = (Residente) upat.getPrincipal();
        utenteService.disiscriviEmail(r);
        return ResponseEntity.ok().build();

    }

    /**
     * Endpoint per l'iscrizione di un residente alle email di notifica.
     *
     * @param upat Token di autenticazione dell'utente che sta effettuando l'iscrizione.
     * @return ResponseEntity con status 200 OK in caso di successo, status 400 BAD_REQUEST altrimenti.
     */
    @PatchMapping("residente/iscriviEmail")
    public ResponseEntity<Void> iscriviEmail(UsernamePasswordAuthenticationToken upat) {
        Residente r = (Residente) upat.getPrincipal();
        utenteService.iscriviEmail(r);
        return ResponseEntity.ok().build();
    }
}