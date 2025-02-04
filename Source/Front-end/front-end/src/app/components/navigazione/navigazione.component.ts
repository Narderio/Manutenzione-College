import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Sections} from "../../model/left-toolbar";
import {Router, Routes} from "@angular/router";
import {AuthService} from "../../services/auth/auth.service";

@Component({
  selector: 'app-navigazione',
  templateUrl: './navigazione.component.html',
  styleUrls: ['./navigazione.component.css']
})
export class NavigazioneComponent {
  title = "Manutenzione College"; // titolo visualizzato nella left-bar
  menuOpen: boolean = false;
  pathSelezionato: string;

  @Output()
  pathEmitter: EventEmitter<string> = new EventEmitter<string>();

  constructor(private router: Router, private _authService: AuthService) {
  }

  /**
   * get 'menuRoutes' che filtra le rotte in base al ruolo
   */
  get menuRoutes() {
    const userRole = this._authService.getRuolo();

    // router.config contiene tutte le rotte definite nell'AppRoutingModule
    return this.router.config
      .filter(route => {
        // prende i ruoli dalla rotta
        const roles = route.data?.['ruoli'];

        // se la rotta ha i seguenti path non viene presa
        if (route.path == "**" || route.path == "dettaglio-manutenzione/:id" || route.path == "profilo")
          return false;

        // se non vi sono ruoli significa che la rotta è libera e tutti possono accedere
        if (!roles || roles.length === 0) {
          return true;
        }

        // Altrimenti, deve comprendere il ruolo dell'utente loggato
        return roles.includes(userRole);
      })
      .map(route => ({
        // Estraggo ciò che mi serve per la navbar ossia label e path
        label: route.data?.['label'] || route.path,
        path: route.path || ""
      }));
  }


  toggleDropdown() {
    this.menuOpen = !this.menuOpen; // Cambia stato menu
  }

  /**
   * Emette il path al componente padre (app.component.ts)
   */
  navigate(path: string) {
    this.menuOpen = false; // Chiude il menu quando si naviga
    this.pathSelezionato = path;
    this.pathEmitter.emit(path);
  }
}
