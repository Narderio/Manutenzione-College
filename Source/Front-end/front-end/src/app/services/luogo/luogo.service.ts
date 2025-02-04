import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";
import {AggiungiLuogoRequest} from "../../model/request/aggiungi-luogo-request";
import {AggiungiLuogoResponse} from "../../model/response/aggiungi-luogo-response";
import {GetLuoghiResponse} from "../../model/response/get-luoghi-response";
import {GetStanzeResponse} from "../../model/response/get-stanze-response";
import {GetLuoghiComuniResponse} from "../../model/response/get-luoghi-comuni-response";
import {GetBagniResponse} from "../../model/response/get-bagni-response";
import {EliminaLuogoRequest} from "../../model/request/elimina-luogo-request";
import {ModificaStanzaRequest} from "../../model/request/modifica-stanza-request";
import {Luogo} from "../../model/models/luogo";
import {GetStanzaDto} from "../../model/models/get-stanza-dto";

@Injectable({
  providedIn: 'root'
})
export class LuogoService {

  constructor(private http: HttpClient) {
  }

  /**
   * Invia la richiesta di aggiungi luogo al BE
   */
  aggiungiLuogo(aggiungiLuogoRequest: AggiungiLuogoRequest): Observable<AggiungiLuogoResponse> {
    return this.http.post<AggiungiLuogoResponse>(environment.baseUrl + environment.Admin.baseUrl + environment.Luogo.aggiungiLuogo, aggiungiLuogoRequest);
  }

  /**
   * Invia la richiesta di ritrovamento luoghi al BE
   */
  getLuoghi(): Observable<GetLuoghiResponse[]> {
    return this.http.get<GetLuoghiResponse[]>(environment.baseUrl + environment.All.baseUrl + environment.Luogo.getLuoghi);
  }

  /**
   * Invia la richiesta di ritrovamento stanze al BE
   */
  getStanze(): Observable<GetStanzeResponse[]> {
    return this.http.get<GetStanzeResponse[]>(environment.baseUrl + environment.Admin.baseUrl + environment.Luogo.getStanze);
  }

  /**
   * Invia la richiesta di ritrovamento luoghi comuni al BE
   */
  getLuoghiComuni(): Observable<GetLuoghiComuniResponse[]> {
    return this.http.get<GetLuoghiComuniResponse[]>(environment.baseUrl + environment.Admin.baseUrl + environment.Luogo.getLuoghiComuni);
  }

  /**
   * Invia la richiesta di ritrovamento bagni al BE
   */
  getBagni(): Observable<GetBagniResponse[]> {
    return this.http.get<GetBagniResponse[]>(environment.baseUrl + environment.Admin.baseUrl + environment.Luogo.getBagni);
  }

  /**
   * Invia la richiesta di elimina luogo al BE
   */
  eliminaLuogo(eliminaLuogoRequest: EliminaLuogoRequest): Observable<boolean> {
    return this.http.delete<boolean>(environment.baseUrl + environment.Admin.baseUrl + environment.Luogo.eliminaLuogo, {
      body: eliminaLuogoRequest
    });
  }

  /**
   * Invia la richiesta di modifica stanza al BE
   */
  modificaStanza(modificStanzaRequest: ModificaStanzaRequest): Observable<Luogo[]> {
    return this.http.patch<Luogo[]>(environment.baseUrl + environment.Admin.baseUrl + environment.Luogo.modificaStanza, modificStanzaRequest);
  }

  /**
   * Invia la richiesta di ritrovamento stanza del residente al BE
   */
  getStanza(){
    return this.http.get<GetStanzaDto>(environment.baseUrl + environment.Residente.baseUrl + environment.Luogo.getStanza);
  }
}
