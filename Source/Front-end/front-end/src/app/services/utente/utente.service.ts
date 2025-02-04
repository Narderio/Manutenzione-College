import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {map, Observable} from "rxjs";
import {GetUtentiResponse} from "../../model/response/get-utenti-response";
import {GetResidentiResponse} from "../../model/response/get-residenti-response";
import {GetManutentoriResponse} from "../../model/response/get-manutentori-response";
import {GetAdminResponse} from "../../model/response/get-admin-response";
import {GetSupervisoriResponse} from "../../model/response/get-supervisori-response";
import {LoginRequest} from "../../model/request/login-request";
import {LoginResponse} from "../../model/response/login-response";
import {CambioPasswordRequest} from "../../model/request/cambio-password-request";
import {PasswordDimenticataRequest} from "../../model/request/password-dimenticata-request";

@Injectable({
  providedIn: 'root'
})
export class UtenteService {

  constructor(private http: HttpClient) {
  }

  /**
   * Invia la richiesta di ritrovamento utenti al BE
   */
  getUtenti(): Observable<GetUtentiResponse[]> {
    return this.http.get<GetUtentiResponse[]>(environment.baseUrl + environment.All.baseUrl + environment.Utente.getUtenti);
  }

  /**
   * Invia la richiesta di ritrovamento residenti al BE
   */
  getResidenti(): Observable<GetResidentiResponse[]> {
    return this.http.get<GetResidentiResponse[]>(environment.baseUrl + environment.Utente.getResidenti);
  }
  /**
   * Invia la richiesta di ritrovamento manutentori al BE
   */
  getManutentori(): Observable<GetManutentoriResponse[]> {
    return this.http.get<GetManutentoriResponse[]>(environment.baseUrl + environment.Utente.getManutentori);
  }

  /**
   * Invia la richiesta di ritrovamento admin al BE
   */
  getAdmin(): Observable<GetAdminResponse[]> {
    return this.http.get<GetAdminResponse[]>(environment.baseUrl + environment.Admin.baseUrl + environment.Utente.getAdmin);
  }

  /**
   * Invia la richiesta di ritrovamento supervisori al BE
   */
  getSupervisori(): Observable<GetSupervisoriResponse[]> {
    return this.http.get<GetSupervisoriResponse[]>(environment.baseUrl + environment.Admin.baseUrl + environment.Utente.getSupervisori);
  }

  /**
   * Invia la richiesta di password dimenticata al BE
   */
  passwordDimenticata(passwordDimenticataRequest: PasswordDimenticataRequest): Observable<void> {
    return this.http.post<void>(environment.baseUrl  + environment.Utente.passwordDimenticata, passwordDimenticataRequest)
  }

  /**
   * Invia la richiesta di cambio password al BE
   */
  cambioPassword(cambioPasswordRequest: CambioPasswordRequest) {
    return this.http.patch<void>(environment.baseUrl + environment.Utente.cambioPassword, cambioPasswordRequest);
  }

  /**
   * Invia la richiesta di iscrizione/disiscrizione dalle email al BE
   */
  iscrizioneEmail(value: boolean) {
    if (value) {
      return this.http.patch<void>(environment.baseUrl + environment.Residente.baseUrl + environment.Utente.iscriviEmail, {});
    } else {
      return this.http.patch<void>(environment.baseUrl + environment.Residente.baseUrl + environment.Utente.disinscriviEmail, {});
    }
  }
}
