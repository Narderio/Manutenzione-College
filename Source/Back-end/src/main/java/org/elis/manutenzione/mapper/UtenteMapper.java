package org.elis.manutenzione.mapper;

import org.elis.manutenzione.dto.response.*;
import org.elis.manutenzione.model._enum.Ruolo;
import org.elis.manutenzione.model._enum.Tipo;
import org.elis.manutenzione.model.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Classe mapper per la conversione di oggetti {@link Utente} in DTO.
 */
@Component
public class UtenteMapper {

    /**
     * Converte un oggetto {@link Utente} in un DTO {@link LoginResponse}.
     *
     * @param u L'oggetto Utente da convertire.
     * @return Il DTO {@link LoginResponse}.
     */
    public LoginResponse toLoginResponse(Utente u, String token){
        LoginResponse l= new LoginResponse();
        l.setEmail(u.getEmail());
        l.setNome(u.getNome());
        l.setCognome(u.getCognome());
        l.setEmailBloccate(u.getEmailBloccate());
        l.setLuoghi(null);
        l.setToken(token);
        if (u instanceof Manutentore)
            l.setRuolo(Ruolo.Manutentore);
        else if (u instanceof Supervisore)
            l.setRuolo(Ruolo.Supervisore);
        else if (u instanceof Admin)
            l.setRuolo(Ruolo.Admin);
        else if (u instanceof Residente) {
            l.setRuolo(Ruolo.Residente);
            List<String> luoghi = ((Residente) u).getLuoghi().stream()
                    .map(Luogo::getNome)
                    .toList();
            l.setLuoghi(luoghi);
        }
        l.setDataDiNascita(u.getDataDiNascita());
        return l;
    }

    /**
     * Converte un oggetto {@link Utente} in un DTO {@link RegistrazioneResponse}.
     *
     * @param u L'oggetto Utente da convertire.
     * @return Il DTO {@link RegistrazioneResponse}.
     */
    public RegistrazioneResponse toRegistrazioneResponse(Utente u){
        RegistrazioneResponse r= new RegistrazioneResponse();
        r.setEmail(u.getEmail());
        r.setNome(u.getNome());
        r.setCognome(u.getCognome());
        r.setDataDiNascita(u.getDataDiNascita());
        return r;
    }

    /**
     * Converte un'entità {@link Residente} in un DTO {@link GetResidentiResponse}.
     *
     * @param r l'entità Residente da convertire
     * @return il DTO GetResidentiResponse corrispondente
     */
    public GetResidentiResponse toResidente(Residente r) {
        GetResidentiResponse response = new GetResidentiResponse();
        response.setEmail(r.getEmail());
        response.setNome(r.getNome());
        response.setCognome(r.getCognome());
        response.setDataDiNascita(r.getDataDiNascita());

        // Ottiene la stanza del residente filtrando la lista dei luoghi.
        Luogo stanza = r.getLuoghi().stream()
                .filter(l -> l.getTipo().equals(Tipo.Stanza))
                .findFirst()
                .orElse(null);

        // Imposta il nome della stanza nella risposta se presente, altrimenti imposta null.
        if (stanza != null) {
            response.setStanza(stanza.getNome());
        } else {
            response.setStanza(null);
        }

        return response;
    }

    /**
     * Converte un'entità {@link Utente} in un DTO {@link GetUtentiResponse}.
     *
     * @param u L'entità {@link Utente} da convertire.
     * @return Il DTO {@link GetUtentiResponse} risultante.
     */
    public GetUtentiResponse toUtente(Utente u) {
        GetUtentiResponse response = new GetUtentiResponse();
        response.setEmail(u.getEmail());
        response.setNome(u.getNome());
        response.setCognome(u.getCognome());
        response.setDataDiNascita(u.getDataDiNascita());
        if (u instanceof Residente)
            response.setRuolo(Ruolo.Residente);
        else if (u instanceof Manutentore)
            response.setRuolo(Ruolo.Manutentore);
        else if (u instanceof Supervisore)
            response.setRuolo(Ruolo.Supervisore);
        else if (u instanceof Admin)
            response.setRuolo(Ruolo.Admin);
        return response;
    }

    /**
     * Converte un'entità {@link Manutentore} in un DTO {@link GetManutentoriResponse}.
     *
     * @param m L'entità {@link Manutentore} da convertire.
     * @return Il DTO {@link GetManutentoriResponse} risultante.
     */
    public GetManutentoriResponse toManutentore(Manutentore m) {
        GetManutentoriResponse response = new GetManutentoriResponse();
        response.setEmail(m.getEmail());
        response.setNome(m.getNome());
        response.setCognome(m.getCognome());
        response.setDataDiNascita(m.getDataDiNascita());
        response.setTipoManutentore(m.getTipoManutentore());
        response.setManutenzioniEffettuate(m.getManutenzioniEffettuate().size());
        return response;
    }

    /**
     * Converte un'entità {@link Supervisore} in un DTO {@link GetSupervisoriResponse}.
     *
     * @param s L'entità {@link Supervisore} da convertire.
     * @return Il DTO {@link GetSupervisoriResponse} risultante.
     */
    public GetSupervisoriResponse toSupervisore(Supervisore s) {
        GetSupervisoriResponse response = new GetSupervisoriResponse();
        response.setEmail(s.getEmail());
        response.setNome(s.getNome());
        response.setCognome(s.getCognome());
        response.setDataDiNascita(s.getDataDiNascita());
        return response;
    }

    /**
     * Converte un'entità {@link Admin} in un DTO {@link GetAdminResponse}.
     *
     * @param a L'entità {@link Admin} da convertire.
     * @return Il DTO {@link GetAdminResponse} risultante.
     */
    public GetAdminResponse toAdmin(Admin a) {
        GetAdminResponse response = new GetAdminResponse();
        response.setEmail(a.getEmail());
        response.setNome(a.getNome());
        response.setCognome(a.getCognome());
        response.setDataDiNascita(a.getDataDiNascita());
        return response;
    }
}