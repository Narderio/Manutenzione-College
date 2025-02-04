import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {RegistrazioneRequest} from "../../model/request/registrazione-request";
import {RegistrazioneResponse} from "../../model/response/registrazione-response";
import {EliminaUtenteRequest} from "../../model/request/elimina-utente-request";
import {AggiungiResidenteInStanzaRequest} from "../../model/request/aggiungi-residente-in-stanza-request";
import {AggiornaRuoloRequest} from "../../model/request/aggiorna-ruolo-request";
import {Utente} from "../../model/models/utente";
import {EliminaResidenteDaStanzaRequest} from "../../model/request/elimina-residente-da-stanza-request";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) {
  }

  /**
   * Invia la richiesta di registazione utente al BE
   */
  registrazione(registrazioneRequest: RegistrazioneRequest): Observable<RegistrazioneResponse> {
    return this.http.post<RegistrazioneResponse>(environment.baseUrl + environment.Admin.baseUrl + environment.Admin.registrazione, registrazioneRequest);
  }

  /**
   * Invia la richiesta di elimina utente al BE
   */
  eliminaUtente(deleteUtenteRequest: EliminaUtenteRequest) {
    return this.http.delete<void>(environment.baseUrl + environment.Admin.baseUrl + environment.Admin.eliminaUtente, {body: deleteUtenteRequest});
  }

  /**
   * Invia la richiesta di aggiorna ruolo al BE
   */
  aggiornaRuolo(aggiornaRuoloRequest: AggiornaRuoloRequest): Observable<Utente> {
    return this.http.patch<Utente>(environment.baseUrl + environment.Admin.baseUrl + environment.Admin.aggiornaRuolo, aggiornaRuoloRequest);
  }
  
  /**
   * Invia la richiesta di aggiungi residente in stanza al BE
   */
  aggiungiResidenteInStanza(aggiungiResidenteInStanzaRequest: AggiungiResidenteInStanzaRequest): Observable<void> {
    return this.http.post<void>(environment.baseUrl + environment.Admin.baseUrl + environment.Admin.aggiungiResidenteInStanza, aggiungiResidenteInStanzaRequest);
  }


  /**
   * Invia la richiesta di elimina residente da stanza al BE
   */
  eliminaResidenteDaStanza(eliminaResidenteDaStanzaRequest: EliminaResidenteDaStanzaRequest): Observable<void> {
    return this.http.patch<void>(environment.baseUrl + environment.Admin.baseUrl + environment.Admin.eliminaResidenteDaStanza, eliminaResidenteDaStanzaRequest);
  }
}
