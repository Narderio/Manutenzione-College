package org.elis.manutenzione.controller;

import jakarta.validation.Valid;
import org.elis.manutenzione.dto.request.AggiungiLuogoRequest;
import org.elis.manutenzione.dto.request.EliminaLuogoRequest;
import org.elis.manutenzione.dto.request.ModificaStanzaRequest;
import org.elis.manutenzione.dto.response.*;
import org.elis.manutenzione.mapper.LuogoMapper;
import org.elis.manutenzione.model.entity.Luogo;
import org.elis.manutenzione.model.entity.Residente;
import org.elis.manutenzione.service.definition.LuogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST per la gestione dei luoghi.
 * Questo controller espone endpoint per l'aggiunta, la visualizzazione,
 * l'eliminazione e la modifica dei luoghi (comuni, stanze, bagni).
 * Utilizza il servizio {@link LuogoService} per la logica di business.
 */
@RestController
public class LuogoController {

    @Autowired
    LuogoService luogoService;

    private final LuogoMapper luogoMapper;

    /**
     * Costruttore della classe.
     * Inizializza le dipendenze necessarie per la gestione dei luoghi.
     *
     * @param luogoMapper Il mapper per la conversione tra entità Luogo e DTO.
     */
    public LuogoController(LuogoMapper luogoMapper) {
        this.luogoMapper = luogoMapper;
    }

    /**
     * Endpoint per l'aggiunta di un nuovo luogo.
     *
     * @param request DTO contenente i dati del luogo da aggiungere.
     * @return ResponseEntity contenente i dati del luogo appena aggiunto in formato DTO.
     * @throws org.apache.coyote.BadRequestException Se si verifica un errore nella richiesta.
     */
    @PostMapping("/admin/aggiungiLuogo")
    public ResponseEntity<AggiungiLuogoResponse> aggiungiLuogo(@Valid @RequestBody AggiungiLuogoRequest request) throws org.apache.coyote.BadRequestException {
        Luogo l = luogoService.aggiungiLuogo(request);
        AggiungiLuogoResponse response = luogoMapper.toAggiungiLuogoResponse(l);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint per la visualizzazione dei nuclei.
     *
     * @return ResponseEntity contenente la lista dei nuclei in formato DTO o status 404 NOT_FOUND se non ci sono luoghi.
     */
    @GetMapping("/admin/getNuclei")
    public ResponseEntity<List<GetNucleiResponse>> getNuclei() {
        List<Luogo> luoghi = luogoService.getLuoghi();
        List<GetNucleiResponse> response = luogoMapper.toGetNucleiMapper(luoghi);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint per la visualizzazione di tutti i luoghi.
     *
     * @return ResponseEntity contenente la lista dei luoghi in formato DTO o status 404 NOT_FOUND se non ci sono luoghi.
     */
    @GetMapping("all/getLuoghi")
    public ResponseEntity<List<GetLuoghiResponse>> getLuoghi() {
        List<Luogo> luoghi = luogoService.getLuoghi();
        List<GetLuoghiResponse> response = luogoMapper.toGetLuoghiMapper(luoghi);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint per la visualizzazione della stanza associata a un residente.
     *
     * @param upat Token di autenticazione dell'utente che sta richiedendo la stanza.
     * @return ResponseEntity contenente il nome della stanza in formato DTO
     */
    @GetMapping("/residente/getStanza")
    public ResponseEntity<GetStanzaDTO> getLuoghiResidente(UsernamePasswordAuthenticationToken upat) {
        Residente r = (Residente) upat.getPrincipal();
        Luogo stanza = luogoService.getStanzaByResidente(r);
        GetStanzaDTO response = luogoMapper.toGetStanzaMapper(stanza);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint per la visualizzazione dei luoghi comuni.
     *
     * @return ResponseEntity contenente la lista dei luoghi comuni in formato DTO o status 404 NOT_FOUND se non ci sono luoghi comuni.
     */
    @GetMapping("/admin/getLuoghiComuni")
    public ResponseEntity<List<GetLuoghiComuniResponse>> getLuoghiComuni() {
        List<Luogo> luoghi = luogoService.getLuoghiComuni();
        List<GetLuoghiComuniResponse> response = luogoMapper.toGetLuoghiComuniMapper(luoghi);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint per la visualizzazione delle stanze.
     *
     * @return ResponseEntity contenente la lista delle stanze in formato DTO o status 404 NOT_FOUND se non ci sono stanze.
     */
    @GetMapping("/admin/getStanze")
    public ResponseEntity<List<GetStanzeResponse>> getStanze() {
        List<Luogo> luoghi = luogoService.getStanze();
        List<GetStanzeResponse> response = luogoMapper.toGetStanzeMapper(luoghi);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint per la visualizzazione dei bagni.
     *
     * @return ResponseEntity contenente la lista dei bagni in formato DTO o status 404 NOT_FOUND se non ci sono bagni.
     */
    @GetMapping("/admin/getBagni")
    public ResponseEntity<List<GetBagniResponse>> getBagni() {
        List<Luogo> luoghi = luogoService.getBagni();
        List<GetBagniResponse> response = luogoMapper.toGetBagniMapper(luoghi);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint per l'eliminazione di un luogo.
     *
     * @param request DTO contenente l'id del luogo da eliminare.
     * @return ResponseEntity con status 200 OK in caso di successo, status 404 NOT_FOUND se il luogo non esiste.
     */
    @DeleteMapping("/admin/eliminaLuogo")
    public ResponseEntity<Boolean> eliminaLuogo(@Valid @RequestBody EliminaLuogoRequest request) {
        luogoService.eliminaLuogo(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint per la modifica della capienza di una stanza.
     *
     * @param request DTO contenente l'id della stanza e la nuova capienza.
     * @return ResponseEntity contenente i dati del luogo (stanza) modificato.
     */
    @PatchMapping("/admin/modificaStanza")
    public ResponseEntity<Luogo> modificaStanza(@Valid @RequestBody ModificaStanzaRequest request) {
        Luogo l = luogoService.modificaStanza(request);
        return ResponseEntity.ok(l);
    }
}