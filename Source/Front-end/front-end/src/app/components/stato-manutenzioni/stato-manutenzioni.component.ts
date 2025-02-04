import {Component, OnInit} from '@angular/core';
import {ManutenzioneService} from "../../services/manutenzione/manutenzione.service";
import {MatDialog} from "@angular/material/dialog";
import {ManutenzioneDTO} from "../../model/response/manutenzione-dto";
import {FiltroManutenzione} from "../../model/request/filtro-manutenzione";
import {FeedbackManutenzioneRequest} from "../../model/request/feedback-manutenzione-request";
import {ModificaManutenzioneRequest} from "../../model/request/modifica-manutenzione-request";
import {FormBuilder, FormGroup} from "@angular/forms";
import {FormDialogComponent} from "../../dialogs/form-dialog/form-dialog.component";
import {FormDialogDto} from "../../model/models/form-dialog-dto";
import {SnackbarService} from "../../services/snackbar/snackbar.service";
import {UtenteService} from "../../services/utente/utente.service";
import {ConfirmDialogComponent} from "../../dialogs/confirm-dialog/confirm-dialog.component";
import {ConfirmDialogDto} from "../../model/models/confirm-dialog-dto";
import {RiparaManutenzioneRequest} from "../../model/request/ripara-manutenzione-request";
import {RifiutaManutenzioneRequest} from "../../model/request/rifiuta-manutenzione-request";
import {AccettaManutenzioneRequest} from "../../model/request/accetta-manutenzione-request";
import {Ruolo} from "../../model/models/ruolo";
import {AuthService} from "../../services/auth/auth.service";
import {LoginResponse} from "../../model/response/login-response";
import {FormDialogInput} from "../../model/models/form-dialog-input";

@Component({
  selector: 'app-stato-manutenzioni',
  templateUrl: './stato-manutenzioni.component.html',
  styleUrls: ['./stato-manutenzioni.component.css']
})
export class StatoManutenzioniComponent implements OnInit {
  // per visualizzare le manutenzioni filtrate
  manutenzioni: ManutenzioneDTO[];

  // form per filtrare le manutenzioni
  filtroForm: FormGroup;

  // per filtrare le manutenzioni
  filtroManutenzione: FiltroManutenzione

  // per visualizzare le colonne in tabella
  colonneMostrate: string [] = ["priorita", "nome", "statoManutenzione", "dataRichiesta", "dataPrevista", "dataRiparazione", "manutentore", "actions"];

  // utente loggato
  utente: LoginResponse;

  constructor(private _manutenzioneService: ManutenzioneService, private dialog: MatDialog,
              private _snackbarService: SnackbarService, private _utenteService: UtenteService,
              private formBuilder: FormBuilder, private _authService: AuthService) {
    this.utente = this._authService.getUtente();
    // crea il form per filtrare le manutenzioni
    this.filtroForm = this.formBuilder.group(
      {
        nome: [""],
        statoManutenzione: [""],
        priorita: [""],
        dataPrevista: [""],
        dataRiparazione: [""],
        dataRichiesta: [""],
        manutentore: [""]
      }
    )
  }

  ngOnInit() {
    // richiede le manutenzioni filtrate
    this.filtroManutenzioni();
  }

  /**
   * Ritrovamento manutenzioni sulla base del filtroForm
   */
  filtroManutenzioni() {
    // crea un FiltroManutenzione sulla base del filtroForm e invia la richiesta al BE
    this.filtroManutenzione = new FiltroManutenzione("", this.filtroForm.value.dataRichiesta, this.filtroForm.value.dataRiparazione, this.filtroForm.value.dataPrevista, this.filtroForm.value.priorita, this.filtroForm.value.manutentore, this.filtroForm.value.statoManutenzione, "", this.filtroForm.value.nome)
    this._manutenzioneService.filtroManutenzioni(this.filtroManutenzione).subscribe({
        next: result => {
          // se ha successo salva il risultato in manutenzioni
          this.manutenzioni = result;
        },
        error: error => {
          // se non ha successo visualizza un messaggio di errore
          this._snackbarService.openError(error.error.message.errore);
        }
      }
    )
  }

  /**
   * Apre una finestra di dialogo con form e invia il feedback al backend
   */
  feedbackManutenzione(manutenzione: ManutenzioneDTO) {
    const formDialogData: FormDialogDto = new FormDialogDto("Lacia un feedback per la manutenzione: '" + manutenzione.nome + "'",
      "Invia", [
        new FormDialogInput("Checkbox", "Risolto?", "risolto", []),
        new FormDialogInput("Text", "Feedback", "problema", [])
      ])
    // apre una finestra di dialogo
    let dialogRef = this.dialog.open(FormDialogComponent, {
      width: "200",
      height: "500",
      data: formDialogData
    })

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea un feedbackManutenzioneRequest e lo invia al BE
      let feedbackManutenzioneRequest = new FeedbackManutenzioneRequest(manutenzione.id, result.risolto, result.problema)
      this._manutenzioneService.feedbackManutenzioni(feedbackManutenzioneRequest).subscribe({
        next: result => {
          // se ha successo richiede il filtroManutenzione
          this.filtroManutenzioni();
        },
        error: error => {
          // se non ha successo visualizza un messaggio di errore
          this._snackbarService.openError(error.error.message.errore);
        }
      })

    })
  }

  /**
   * Apre una finestra di dialogo con form e invia la richiesta al BE
   */
  modificaManutenzione(manutenzione: ManutenzioneDTO) {
    const formDialogData: FormDialogDto = new FormDialogDto("Modifica per la manutenzione: '" + manutenzione.nome + "'", "Modifica", [
      new FormDialogInput("Text", "Nome", "nome", [], undefined, undefined, undefined, manutenzione.nome),
      new FormDialogInput("Text", "Descrizione", "descrizione", [], undefined, undefined, undefined, manutenzione.descrizione),
    ])
    // apre una finestra di dialogo
    let dialogRef = this.dialog.open(FormDialogComponent, {
      width: "200",
      height: "500",
      data: formDialogData
    })

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea un modificaManutenzioneRequest e lo invia al BE
      let modificaManutenzioneRequest = new ModificaManutenzioneRequest(manutenzione.id, result.descrizione, result.nome)
      this._manutenzioneService.modificaManutenzione(modificaManutenzioneRequest).subscribe({
        next: result => {
          // se ha successo richiede le manutenzioni filtrate
          this.filtroManutenzioni();
        },
        error: error => {
          // se non ha successo visualizza un messaggio di errore
          this._snackbarService.openError(error.error.message.errore);
        }
      })

    })
  }

  /**
   * Apre una finestra di dialogo e invia la richiesta di ripara manutenzione
   */
  riparaManutenzione(manutenzione: ManutenzioneDTO) {
    const confirmDialogData: ConfirmDialogDto = new ConfirmDialogDto("Ripara Manutenzione", "Vuoi davvero confermare l'avvenuta riparazione della manutenzione'" + manutenzione.nome + "'?", "Ripara")
    // apre la finestra di dialogo
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: "200",
      height: "500",
      data: confirmDialogData
    })

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea un RiparaManutenzioneRequest e lo invia al BE
      let riparaManutenzioneRequest = new RiparaManutenzioneRequest(manutenzione.id);
      this._manutenzioneService.riparaManutenzione(riparaManutenzioneRequest).subscribe({
        next: value => {
          // se ha successo richiede le manutenzioni
          this.filtroManutenzioni();
        },
        error: err => {
          // se non ha successo visualizza un messaggio di errore
          this._snackbarService.openError(err.error.message.errore);
        }
      })

    })
  }

  /**
   * Apre una finestra di dialogo e invia il rifiuta manutenzione
   */
  rifiutaManutenzione(manutenzione: ManutenzioneDTO) {
    const confirmDialogData: ConfirmDialogDto = new ConfirmDialogDto("Rifiuta Manutenzione", "Vuoi davvero rifiutare la manutenzione'" + manutenzione.nome + "'?", "Rifiuta");
    // apre la finestra di dialogo
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: "200",
      height: "500",
      data: confirmDialogData
    })

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea un RifiutaManutenzioneRequest e invia il rifiutaManutenzion
      let rifiutamanutenzioneRequest = new RifiutaManutenzioneRequest(manutenzione.id);
      this._manutenzioneService.rifiutaManutenzione(rifiutamanutenzioneRequest).subscribe({
        next: value => {
          // se ha successo richiede le manutenzioni
          this.filtroManutenzioni();
        },
        error: err => {
          // se non ha successo visualizza un messaggio di errore
          this._snackbarService.openError(err.error.message.errore)
        }
      })

    })
  }

  /**
   * Apre una finestra di dialogo con form e invia l'accetta manutenzione
   */
  accettaManutenzione(manutenzione: ManutenzioneDTO) {
    const formDialogDto: FormDialogDto = new FormDialogDto("Accetta la manutenzione: '" + manutenzione.nome + "'", "Accetta", [
      new FormDialogInput("Date", "Data Prevista", "dataPrevista", [])
    ])
    let dialogRef = this.dialog.open(FormDialogComponent, {
      width: "200",
      height: "500",
      data: formDialogDto
    })

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea un accettaManutenzioneRequest e lo invia al BE
      let accettaManutenzioneRequest = new AccettaManutenzioneRequest(manutenzione.id, result.dataPrevista);
      this._manutenzioneService.accettaManutenzione(accettaManutenzioneRequest).subscribe({
        next: value => {
          // se ha successo richiede le manutenzioni
          this.filtroManutenzioni();
        },
        error: err => {
          // se non ha successo visualizza un messaggio di errore
          this._snackbarService.openError(err.error.message.errore);
        }
      })

    })
  }

  protected readonly Ruolo = Ruolo;
}
