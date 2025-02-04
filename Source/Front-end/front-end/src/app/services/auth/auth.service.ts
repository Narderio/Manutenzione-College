import {Injectable} from '@angular/core';
import {LoginResponse} from "../../model/response/login-response";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {LoginRequest} from "../../model/request/login-request";
import {SnackbarService} from "../snackbar/snackbar.service";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private _snackbarService: SnackbarService, private router: Router) {
  }

  /**
   * Invia la richiesta di login al BE e salva le informazioni nel localstorage
   */
  login(loginRequest: LoginRequest): boolean {
    this.http.post<LoginResponse>(environment.baseUrl + environment.Utente.login, loginRequest).subscribe({
      next: result => {
        // Se ha successo salviamo tutte le informazioni dell'utente
        localStorage.setItem('utente', JSON.stringify(result))
        // Salviamo un token fasullo
        localStorage.setItem('token', result.token);
        // Salviamo il ruolo
        localStorage.setItem('ruolo', result.ruolo);
        this.router.navigate(['/profilo']);
        return true;
      },
      error: err => {
        // se non ha successo visualizza un messaggio di errore
        this._snackbarService.openError(err.error.message.errore)
      }
    })
    return false;
  }

  /**
   * Ritorna true se l'utente è loggato
   */
  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  /**
   *  Logout e rimozione di tutti i dati dal localstorage
   */
  logout(): void {
    localStorage.removeItem('utente')
    localStorage.removeItem('token');
    localStorage.removeItem('ruolo');
  }

  /**
   * Recupera l'utente
   */
  getUtente(): LoginResponse {
    return JSON.parse(<string>localStorage.getItem('utente'));
  }

  /**
   * Recupera il ruolo dell'utente (es: 'Admin', 'User', ecc.)
   */
  getRuolo(): string {
    return localStorage.getItem('ruolo') || "";
  }

  /**
   * Recupera il token dell'utente
   */
  getToken(): string {
    return localStorage.getItem('token') || "";
  }
}
