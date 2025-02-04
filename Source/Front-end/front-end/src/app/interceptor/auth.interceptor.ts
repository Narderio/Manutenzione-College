import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginResponse} from "../model/response/login-response";
import {AuthService} from "../services/auth/auth.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private _authService: AuthService) {
  }

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    // recupera il token dall'authService
    const token: string = this._authService.getToken();

    // controlla se è presente
    if (token) {
      // in caso positivo clona la richiesta ed aggiunge l'authorization nell'header
      const clonedRequest = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        }
      });
      // invia la richiesta
      return next.handle(clonedRequest);
    }

    // in caso negativo invia la richiesta senza token
    return next.handle(req);
  }
}
