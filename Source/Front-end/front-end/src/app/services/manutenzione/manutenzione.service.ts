import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {RichiediManutenzioneRequest} from "../../model/request/richiedi-manutenzione-request";
import {RichiediManutenzioneResponse} from "../../model/response/richiedi-manutenzione-response";
import {ModificaManutenzioneRequest} from "../../model/request/modifica-manutenzione-request";
import {FiltraManutenzioneRequest} from "../../model/request/filtra-manutenzione-request";
import {FiltraManutenzioneResponse} from "../../model/response/filtra-manutenzione-response";
import {AccettaManutenzioneRequest} from "../../model/request/accetta-manutenzione-request";
import {AccettaManutenzioneResponse} from "../../model/response/accetta-manutenzione-response";
import {RiparaManutenzioneRequest} from "../../model/request/ripara-manutenzione-request";
import {RiparaManutenzioneResponse} from "../../model/response/ripara-manutenzione-response";
import {RifiutaManutenzioneRequest} from "../../model/request/rifiuta-manutenzione-request";
import {RifiutaManutenzioneResponse} from "../../model/response/rifiuta-manutenzione-response";
import {FiltroManutenzione} from "../../model/request/filtro-manutenzione";
import {ManutenzioneDTO} from "../../model/response/manutenzione-dto";
import {FeedbackManutenzioneRequest} from "../../model/request/feedback-manutenzione-request";
import {GetManutenzioniDto} from "../../model/response/get-manutenzioni-dto";
import {GetManutenzioneDTO} from "../../model/response/get-manutenzione-dto";

@Injectable({
  providedIn: 'root'
})
export class ManutenzioneService {

  constructor(private http: HttpClient) {
  }

  /**
   * Invia la richiesta di richiedi manutenzione al BE
   */
  richiediManutenzione(richiediManutenzioneRequest: RichiediManutenzioneRequest): Observable<RichiediManutenzioneResponse> {
    return this.http.post<RichiediManutenzioneResponse>(environment.baseUrl + environment.Residente.baseUrl + environment.Manutenzione.richiediManutenzione, richiediManutenzioneRequest);
  }

  /**
   * Invia la richiesta di modifica manutenzione al BE
   */
  modificaManutenzione(modificaManutenzioneRequest: ModificaManutenzioneRequest): Observable<RichiediManutenzioneResponse> {
    return this.http.patch<RichiediManutenzioneResponse>(environment.baseUrl + environment.Residente.baseUrl + environment.Manutenzione.modificaManutenzione, modificaManutenzioneRequest);
  }

  /**
   * Invia la richiesta di filtra manutenzione da parte del supervisore al BE
   */
  filtraManutenzione(filtraManutenzioneRequest: FiltraManutenzioneRequest): Observable<FiltraManutenzioneResponse> {
    return this.http.post<FiltraManutenzioneResponse>(environment.baseUrl + environment.Supervisore.baseUrl + environment.Manutenzione.filtraManutenzione, filtraManutenzioneRequest);
  }

  /**
   * Invia la richiesta di accetta manutenzione al BE
   */
  accettaManutenzione(accettaManutenzioneRequest: AccettaManutenzioneRequest): Observable<AccettaManutenzioneResponse> {
    return this.http.post<AccettaManutenzioneResponse>(environment.baseUrl + environment.Manutenzione.baseUrl + environment.Manutenzione.accettaManutenzione, accettaManutenzioneRequest);
  }

  /**
   * Invia la richiesta di ripara manutenzione al BE
   */
  riparaManutenzione(riparaManutenzioneRequest: RiparaManutenzioneRequest): Observable<RiparaManutenzioneResponse> {
    return this.http.post<RiparaManutenzioneResponse>(environment.baseUrl + environment.Manutenzione.baseUrl + environment.Manutenzione.riparaManutenzione, riparaManutenzioneRequest);
  }

  /**
   * Invia la richiesta di rifiuta manutenzione al BE
   */
  rifiutaManutenzione(rifiutaManutenzioneRequest: RifiutaManutenzioneRequest): Observable<RifiutaManutenzioneResponse> {
    return this.http.post<RifiutaManutenzioneResponse>(environment.baseUrl + environment.Manutenzione.rifiutaManutenzione, rifiutaManutenzioneRequest);
  }

  /**
   * Invia la richiesta di filtro manutenzione al BE
   */
  filtroManutenzioni(filtroManutenzione: FiltroManutenzione): Observable<ManutenzioneDTO[]> {
    return this.http.post<ManutenzioneDTO[]>(environment.baseUrl + environment.All.baseUrl + environment.Manutenzione.filtroManutenzione, filtroManutenzione);
  }

  /**
   * Invia la richiesta di feedback manutenzione al BE
   */
  feedbackManutenzioni(feedbackManutenzioneRequest: FeedbackManutenzioneRequest): Observable<void> {
    return this.http.post<void>(environment.baseUrl + environment.Residente.baseUrl + environment.Manutenzione.feedbackManutenzioni, feedbackManutenzioneRequest);
  }

  /**
   * Invia la richiesta di ritrovamento manutenzioni al BE
   */
  getManutenzioni() {
    return this.http.get<GetManutenzioniDto[]>(environment.baseUrl + environment.All.baseUrl + environment.Manutenzione.getManutenzioni);
  }

  /**
   * Invia la richiesta di ritrovamento manutenzione sulla base dell'id al BE
   */
  getManutenzioneById(id: string) {
    return this.http.get<GetManutenzioneDTO>(environment.baseUrl + environment.All.baseUrl + environment.Manutenzione.getManutenzione + "/" + id);
  }
}
