import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from "../services/auth/auth.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private _authService: AuthService, private router: Router) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    // Recupera i ruoli ammessi dalla rotta (se presenti)
    const ruoliPermessi = route.data['ruoli'] as Array<string> || [];

    // Se la rotta non specifica ruoli, passa senza restrizioni
    if (ruoliPermessi.length === 0) {
      return true;
    }

    // Verifica se il ruolo dell’utente è tra quelli ammessi
    const ruoloUtente = this._authService.getRuolo();
    if (ruoloUtente && ruoliPermessi.includes(ruoloUtente)) {
      return true;
    }

    // Se l'utente è loggato ma il ruolo non è valido lo reindirizza
    this.router.navigate(['/profilo']);
    return false;
  }

}
