import {Component, OnInit} from '@angular/core';
import {LuogoService} from "../../services/luogo/luogo.service";
import {GetStanzeResponse} from "../../model/response/get-stanze-response";
import {GetBagniResponse} from "../../model/response/get-bagni-response";
import {GetLuoghiComuniResponse} from "../../model/response/get-luoghi-comuni-response";
import {AggiungiLuogoRequest} from "../../model/request/aggiungi-luogo-request";
import {EliminaLuogoRequest} from "../../model/request/elimina-luogo-request";
import {ModificaStanzaRequest} from "../../model/request/modifica-stanza-request";
import {SnackbarService} from "../../services/snackbar/snackbar.service";

@Component({
  selector: 'app-gestione-luoghi',
  templateUrl: './gestione-luoghi.component.html',
  styleUrls: ['./gestione-luoghi.component.css']
})
export class GestioneLuoghiComponent implements OnInit {

  stanze: GetStanzeResponse[]; // per visualizzare le stanze
  bagni: GetBagniResponse[]; // per visualizzare i bagni
  luoghiComuni: GetLuoghiComuniResponse[]; // per visualizzare i luoghiComuni

  constructor(private _luogoService: LuogoService, private _snackbarService: SnackbarService) {
  }

  ngOnInit(): void {
    // ritrovamento di tutti i tipi di luogo
    this.getAll()
  }

  /**
   * Invia le richieste al BE per salvare tutti i tipi di luogo
   */
  getAll() {
    this.getStanze()
    this.getBagni()
    this.getLuoghiComuni()
  }

  /**
   * Ritrovamento delle stanze
   */
  getStanze() {
    this._luogoService.getStanze().subscribe({
        next: data => {
          // se ha successo salva i dati in stanze
          this.stanze = data;
        },
        error: error => {
          // se non ha successo svuota stanze e visualizza un messaggio di errore
          this.stanze = [];
          this._snackbarService.openError(error.error.message.errore);
        }
      }
    )
  }

  /**
   * Ritrovamento dei bagni
   */
  getBagni() {
    this._luogoService.getBagni().subscribe({
      next: value => {
        // se ha successo salva i dati in bagni
        this.bagni = value;
      },
      error: error => {
        // se non ha successo svuota i bagni e visualizza un messaggio di errore
        this.bagni = [];
        this._snackbarService.openError(error.error.message.errore);

      }
    })
  }

  /**
   * Ritrovamento dei luoghi comuni
   */
  getLuoghiComuni() {
    this._luogoService.getLuoghiComuni().subscribe({
      next: value => {
        // se ha successo salva i dati in luoghiComuni
        this.luoghiComuni = value;
      },
      error: error => {
        // se non ha successo svuota luoghiComuni e visualizza un messaggio di errore
        this.luoghiComuni = [];
        this._snackbarService.openError(error.error.message.errore);

      }
    })
  }

  /**
   * Invia la richiesta di aggiunta luogo al BE
   */
  aggiungiLuogo(aggiungiLuogoRequest: AggiungiLuogoRequest): void {

    this._luogoService.aggiungiLuogo(aggiungiLuogoRequest).subscribe({
      next: (value) => {
        // se ha successo richiede tutti i dati sui luoghi al BE
        this.getAll();
      },
      error: (error) => {
        // se non ha successo emette un messaggio di errore
        this._snackbarService.openError(error.error.message.errore);
      }
    })

  }

  /**
   * Invia la richiesta di eliminazione luogo al BE
   */
  eliminaLuogo(eliminaLuogoRequest: EliminaLuogoRequest) {

    this._luogoService.eliminaLuogo(eliminaLuogoRequest).subscribe({
        next: (value) => {
          // se ha successo richiede tutti i dati sui luoghi al BE
          this.getAll();
        },
        error: (error) => {
          // se non ha successo visualizza un messaggio di errore
          this._snackbarService.openError(error.error.message.errore);
        }
      }
    )

  }

  /**
   * Invia la richiesta di modifica luogo al BE
   */
  modificaStanza(modificaStanzaRequest: ModificaStanzaRequest) {
    this._luogoService.modificaStanza(modificaStanzaRequest).subscribe({
      next: value => {
        // se ha successo richiede tutti i dati sui luoghi al BE
        this.getAll();
      },
      error: error => {
        // se non ha successo visualizza un messaggio di errore
        this._snackbarService.openError(error.error.message.errore);
      }
    })
  }
}
