import {Component, OnInit} from '@angular/core';

import {UtenteService} from "../../services/utente/utente.service";
import {AdminService} from "../../services/admin/admin.service";
import {GetResidentiResponse} from "../../model/response/get-residenti-response";
import {GetManutentoriResponse} from "../../model/response/get-manutentori-response";
import {GetAdminResponse} from "../../model/response/get-admin-response";
import {GetSupervisoriResponse} from "../../model/response/get-supervisori-response";
import {RegistrazioneRequest} from "../../model/request/registrazione-request";
import {EliminaUtenteRequest} from "../../model/request/elimina-utente-request";
import {AggiornaRuoloRequest} from "../../model/request/aggiorna-ruolo-request";
import {AggiungiResidenteInStanzaRequest} from "../../model/request/aggiungi-residente-in-stanza-request";
import {EliminaResidenteDaStanzaRequest} from "../../model/request/elimina-residente-da-stanza-request";
import {SnackbarService} from "../../services/snackbar/snackbar.service";

@Component({
  selector: 'app-gestione-utenti',
  templateUrl: './gestione-utenti.component.html',
  styleUrls: ['./gestione-utenti.component.css']
})
export class GestioneUtentiComponent implements OnInit {

  // per visualizzare le informazioni degli utenti
  residenti: GetResidentiResponse[];
  manutentori: GetManutentoriResponse[];
  admin: GetAdminResponse[];
  supervisori: GetSupervisoriResponse[];

  constructor(private _utenteService: UtenteService, private _adminService: AdminService,
              private _snackbarService: SnackbarService) {
  }

  ngOnInit(): void {
    // richiede al BE tutti i tipi di utenti
    this.getAll()
  }

  /**
   * Richiede tutti i tipi di utente
   */
  getAll() {
    this.getResidenti();
    this.getManutentori();
    this.getAdmin();
    this.getSupervisori();
  }

  /**
   * Ritrovamento dei Residenti
   */
  getResidenti() {
    this._utenteService.getResidenti().subscribe({
      next: value => {
        // se ha successo li salva in residenti
        this.residenti = value;
      },
      error: error => {
        // se non ha successo vuota residenti e visualizza un messaggio di errore
        this.residenti = [];
        this._snackbarService.openError(error.error.message.errore);
      }
    })
  }

  /**
   * Ritrovamento dei manutentori
   */
  getManutentori() {
    this._utenteService.getManutentori().subscribe({
      next: value => {
        // se ha successo li salva in manutentori
        this.manutentori = value;
      },
      error: error => {
        // se non ha successo svuota manutentori e visualizza un messaggio di errore
        this.manutentori = [];
        this._snackbarService.openError(error.error.message.errore);

      }
    })
  }

  /**
   * Ritrovamento degli admin
   */
  getAdmin() {
    this._utenteService.getAdmin().subscribe({
      next: value => {
        // se ha successo li salva in admin
        this.admin = value;
      },
      error: error => {
        // se non ha successo svuota admin e visualizza un messaggio di errore
        this.admin = [];
        this._snackbarService.openError(error.error.message.errore);

      }
    })
  }

  /**
   * Ritrovamento dei supervisori
   */
  getSupervisori() {
    this._utenteService.getSupervisori().subscribe({
      next: value => {
        // se ha successo li salva in supervisori
        this.supervisori = value;
      },
      error: error => {
        // se non ha successo svuota supervisori e visualizza un messaggio di errore
        this.supervisori = [];
        this._snackbarService.openError(error.error.message.errore);
      }
    })
  }

  /**
   * invia la richiesta di aggiunta Utente
   */
  aggiungiUtente(registrazioneRequest: RegistrazioneRequest) {
    this._adminService.registrazione(registrazioneRequest).subscribe({
      next: value => {
        // se ha successo richiede tutti i tipi di utente
        this.getAll();
      },
      error: error => {
        // se non ha successo visualizza un messaggio di errore
        this._snackbarService.openError(error.error.message.errore);
      }
    })
  }

  /**
   * invia la richiesta di eliminazione utente
   */
  eliminaUtente(deleteUtenteRequest: EliminaUtenteRequest) {
    this._adminService.eliminaUtente(deleteUtenteRequest).subscribe({
        next: value => {
          // se ha successo richiede tutti i tipi di utente
          this.getAll()
          this._snackbarService.openSuccess("Utente eliminato con successo");
        },
        error: error => {
          // se non ha successo visualizza un messaggio di errore
          this._snackbarService.openError(error.error.message.errore);
        }
      }
    )
  }

  /**
   * invia la richiesta di aggiorna ruolo
   */
  aggiornaRuolo(aggiornaRuoloRequest: AggiornaRuoloRequest) {
    this._adminService.aggiornaRuolo(aggiornaRuoloRequest).subscribe({
      next: value => {
        // se ha successo richiede tutti i tipi di utente
        this.getAll();
        this._snackbarService.openSuccess("Ruolo aggiornato con successo");
      },
      error: error => {
        // se non ha successo visualizza un messaggio di errore
        this._snackbarService.openError(error.error.message.errore);
      }
    })
  }

  /**
   * A seconda del tipo di dato che arriva richiede un aggiungiResidenteInStazna
   * oppure un eliminaResidenteDaStanza
   */
  aggiornaStanzaResidente(residenteInStanza: AggiungiResidenteInStanzaRequest | EliminaResidenteDaStanzaRequest) {
    if (residenteInStanza instanceof AggiungiResidenteInStanzaRequest) {
      // se il dato è di tipo aggiungiResidenteInStanzaRequest richiede un aggiungiResidenteInStanza
      this._adminService.aggiungiResidenteInStanza(residenteInStanza).subscribe({
        next: value => {
          // se ha successo richiede i residenti
          this.getResidenti();
          this._snackbarService.openSuccess("Residente aggiunto con successo alla stanza");
        },
        error: error => {
          // se non ha successo visualizza un messaggio di errore
          this._snackbarService.openError(error.error.message.errore);
        }
      })
    } else {
      // se il dato è di tipo eliminaResidenteDaStanzaRequest richiede un eliminaResidenteDaStanza
      this._adminService.eliminaResidenteDaStanza(residenteInStanza).subscribe({
        next: value => {
          // se ha successo richiede i residenti
          this.getResidenti();
          this._snackbarService.openSuccess("Residente eliminato con successo dalla stanza");
        },
        error: error => {
          // se non ha successo visualizza un messaggio di errore
          this._snackbarService.openError(error.error.message.errore);
        }
      })
    }
  }
}
