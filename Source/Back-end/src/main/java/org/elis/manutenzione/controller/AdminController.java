package org.elis.manutenzione.controller;

import jakarta.validation.Valid;
import org.elis.manutenzione.dto.request.*;
import org.elis.manutenzione.dto.response.RegistrazioneResponse;
import org.elis.manutenzione.mapper.UtenteMapper;
import org.elis.manutenzione.model.entity.Utente;
import org.elis.manutenzione.service.definition.UtenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UtenteMapper utenteMapper;

    private final UtenteService utenteService;


    public AdminController(UtenteService utenteService, UtenteMapper utenteMapper) {
        this.utenteService = utenteService;
        this.utenteMapper = utenteMapper;
    }


    @PostMapping("/registrazione")
    public ResponseEntity<RegistrazioneResponse> registrazione(@Valid @RequestBody RegistrazioneRequest request) {
        Utente u = utenteService.registrazione(request);
        RegistrazioneResponse r = utenteMapper.toRegistrazioneResponse(u);
        return ResponseEntity.ok(r);
    }


    @DeleteMapping("/eliminaUtente")
    public ResponseEntity<Void> deleteUtente(@Valid @RequestBody EliminaUtenteRequest request){
        utenteService.deleteUtente(request);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/aggiungiResidenteInStanza")
    public ResponseEntity<Boolean> aggiungiResidenteInStanza(@Valid @RequestBody AggiungiResidenteInStanzaRequest request){
        utenteService.aggiungiResidenteInStanza(request);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/aggiornaRuolo")
    public ResponseEntity<RegistrazioneResponse> aggiornaRuolo(@Valid @RequestBody AggiornaRuoloRequest request){
        Utente u = utenteService.aggiornaRuolo(request);
        RegistrazioneResponse r = utenteMapper.toRegistrazioneResponse(u);
        return ResponseEntity.ok(r);
    }

    @PatchMapping("/eliminaResidenteDaStanza")
    public ResponseEntity<Boolean> eliminaResidenteDaStanza(@Valid @RequestBody EliminaResidenteDaStanzaRequest request){
        utenteService.eliminaResidenteDaStanza(request);
        return ResponseEntity.ok().build();
    }
}