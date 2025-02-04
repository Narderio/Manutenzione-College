package org.elis.manutenzione.controller;

import jakarta.validation.Valid;
import org.elis.manutenzione.dto.request.*;
import org.elis.manutenzione.dto.response.*;
import org.elis.manutenzione.mapper.ManutenzioneMapper;
import org.elis.manutenzione.model.entity.Manutentore;
import org.elis.manutenzione.model.entity.Manutenzione;
import org.elis.manutenzione.model.entity.Residente;
import org.elis.manutenzione.model.entity.Utente;
import org.elis.manutenzione.service.definition.ManutenzioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST per la gestione delle manutenzioni.
 * Questo controller espone endpoint per la richiesta, la modifica, il filtraggio, l'accettazione,
 * la riparazione, il rifiuto e il feedback delle manutenzioni.
 * Utilizza il servizio {@link ManutenzioneService} per la logica di business.
 */
@RestController
public class ManutenzioneController {

    @Autowired
    ManutenzioneService manutenzioneService;

    private final ManutenzioneMapper manutenzioneMapper;

    /**
     * Costruttore della classe.
     * Inizializza le dipendenze necessarie per la gestione delle manutenzioni.
     *
     * @param manutenzioneMapper Il mapper per la conversione tra entità Manutenzione e DTO.
     */
    public ManutenzioneController(ManutenzioneMapper manutenzioneMapper) {
        this.manutenzioneMapper = manutenzioneMapper;
    }

    /**
     * Endpoint per la richiesta di una nuova manutenzione da parte di un residente.
     *
     * @param request DTO contenente i dati della manutenzione da richiedere.
     * @param upat    Token di autenticazione dell'utente che sta effettuando la richiesta.
     * @return ResponseEntity contenente i dati della manutenzione appena richiesta in formato DTO.
     */
    @PostMapping("residente/richiediManutenzione")
    public ResponseEntity<RichiediManutenzioneResponse> richiediManutenzione(@Valid @RequestBody RichiediManutenzioneRequest request, UsernamePasswordAuthenticationToken upat) {
        Residente r = (Residente) upat.getPrincipal();
        Manutenzione m = manutenzioneService.richiediManutenzione(request, r);
        RichiediManutenzioneResponse response = manutenzioneMapper.toRichiediManutenzioneResponse(m);
        //if (response == null)
        //    throw new ServerErrorException("Errore nella richiesta della manutenzione");
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint per la modifica di una manutenzione da parte di un residente (solo se in stato "Richiesto").
     *
     * @param request DTO contenente i dati della manutenzione da modificare.
     * @param upat    Token di autenticazione dell'utente che sta effettuando la modifica.
     * @return ResponseEntity contenente i dati della manutenzione modificata in formato DTO.
     */
    @PatchMapping("residente/modificaManutenzione")
    public ResponseEntity<RichiediManutenzioneResponse> modificaManutenzione(@Valid @RequestBody ModificaManutenzioneRequest request, UsernamePasswordAuthenticationToken upat) {
        Residente r = (Residente) upat.getPrincipal();
        Manutenzione m = manutenzioneService.modificaManutenzione(request, r);
        RichiediManutenzioneResponse response = manutenzioneMapper.toRichiediManutenzioneResponse(m);
        //if (response == null)
        //    throw new ServerErrorException("Errore nella modifica della manutenzione");
        return ResponseEntity.ok(response);
    }


    /**
     * Endpoint per il filtraggio di una manutenzione da parte di un supervisore.
     *
     * @param request DTO contenente i dati per il filtraggio della manutenzione.
     * @return ResponseEntity contenente i dati della manutenzione filtrata in formato DTO.
     */
    @PostMapping("supervisore/filtraManutenzione")
    public ResponseEntity<FiltraManutenzioneResponse> filtraManutenzione(@Valid @RequestBody FiltraManutenzioneRequest request) {
        Manutenzione m = manutenzioneService.filtraManutenzione(request);
        FiltraManutenzioneResponse response = manutenzioneMapper.toFiltraManutenzioneResponse(m);
        //if (response == null)
        //    throw new ServerErrorException("Errore nel filtraggio della manutenzione");
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint per l'accettazione di una manutenzione da parte di un manutentore.
     *
     * @param request DTO contenente i dati per l'accettazione della manutenzione.
     * @param upat    Token di autenticazione dell'utente che sta effettuando l'accettazione.
     * @return ResponseEntity contenente i dati della manutenzione accettata in formato DTO.
     */
    @PostMapping("manutentore/accettaManutenzione")
    public ResponseEntity<AccettaManutenzioneResponse> accettaManutenzione(@Valid @RequestBody AccettaManutenzioneRequest request, UsernamePasswordAuthenticationToken upat) {
        Manutentore u = (Manutentore) upat.getPrincipal();
        Manutenzione m = manutenzioneService.accettaManutenzione(request, u);
        AccettaManutenzioneResponse response = manutenzioneMapper.toAccettaManutenzioneResponse(m);
        //if (response == null)
        //    throw new ServerErrorException("Errore nell'accettazione della manutenzione");
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint per la riparazione di una manutenzione da parte di un manutentore.
     *
     * @param request DTO contenente i dati per segnare la manutenzione come riparata.
     * @param upat    Token di autenticazione dell'utente che sta effettuando la riparazione.
     * @return ResponseEntity contenente i dati della manutenzione riparata in formato DTO.
     */
    @PostMapping("manutentore/riparaManutenzione")
    public ResponseEntity<RiparaManutenzioneResponse> riparaManutenzione(@Valid @RequestBody RiparaManutenzioneRequest request, UsernamePasswordAuthenticationToken upat) {
        Manutentore m = (Manutentore) upat.getPrincipal();
        Manutenzione manutenzione = manutenzioneService.riparaManutenzione(request, m);
        RiparaManutenzioneResponse response = manutenzioneMapper.toRiparaManutenzioneResponse(manutenzione);
        //if (response == null)
        //    throw new ServerErrorException("Errore nella riparazione della manutenzione");
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint per il rifiuto di una manutenzione da parte di un manutentore o supervisore.
     *
     * @param request DTO contenente i dati per il rifiuto della manutenzione.
     * @param upat    Token di autenticazione dell'utente che sta effettuando il rifiuto.
     * @return ResponseEntity contenente i dati della manutenzione rifiutata in formato DTO.
     */
    @PostMapping("/rifiutaManutenzione")
    public ResponseEntity<RifiutaManutenzioneResponse> rifiutaManutenzione(@Valid @RequestBody RifiutaManutenzioneRequest request, UsernamePasswordAuthenticationToken upat) {
        Utente u = (Utente) upat.getPrincipal();
        Manutenzione manutenzione = manutenzioneService.rifiutaManutenzione(request, u);
        RifiutaManutenzioneResponse response = manutenzioneMapper.toRifiutaManutenzioneResponse(manutenzione);
        //if (response == null)
        //    throw new ServerErrorException("Errore nel rifiuto della manutenzione");
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint per la visualizzazione di una lista di manutenzioni filtrate in base a determinati criteri.
     *
     * @param request DTO contenente i criteri di filtraggio.
     * @param upat    Token di autenticazione dell'utente che sta richiedendo la lista di manutenzioni.
     * @return ResponseEntity contenente la lista delle manutenzioni in formato DTO.
     */
    @PostMapping("all/filtroManutenzione")
    public ResponseEntity<List<ManutenzioneDTO>> filtroManutenzioni(@RequestBody FiltroManutenzione request, UsernamePasswordAuthenticationToken upat) {
        Utente u = (Utente) upat.getPrincipal();
        List<ManutenzioneDTO> manutenzioni = manutenzioneService.getManutenzioneFiltrata(request, u);
        if (manutenzioni.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.OK).body(manutenzioni);
    }

    /**
     * Endpoint per fornire un feedback su una manutenzione riparata da parte di un residente.
     *
     * @param request DTO contenente i dati del feedback.
     * @param upat    Token di autenticazione dell'utente che sta fornendo il feedback.
     * @return ResponseEntity con status 200 OK in caso di successo.
     */
    @PostMapping("residente/feedbackManutenzione")
    public ResponseEntity<Boolean> feedbackManutenzione(@Valid @RequestBody FeedbackManutenzioneRequest request, UsernamePasswordAuthenticationToken upat) {
        Residente r = (Residente) upat.getPrincipal();
        manutenzioneService.feedbackManutenzione(request, r);
        return ResponseEntity.ok().build();
    }

    @GetMapping("all/getManutenzioni")
    public ResponseEntity<List<GetManutenzioniDTO>> getManutenzioni(UsernamePasswordAuthenticationToken upat) {
        Utente u = (Utente) upat.getPrincipal();
        List<GetManutenzioniDTO> manutenzioni = manutenzioneService.getManutenzioni(u);
        if (manutenzioni.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.OK).body(manutenzioni);
    }

    @GetMapping("all/getManutenzione/{id}")
    public ResponseEntity<GetManutenzioneDTO> getManutenzioneById(@PathVariable Long id, UsernamePasswordAuthenticationToken upat) {
        Utente u = (Utente) upat.getPrincipal();
        GetManutenzioneDTO manutenzione = manutenzioneService.getManutenzioneById(id, u);
        return ResponseEntity.status(HttpStatus.OK).body(manutenzione);
    }
}