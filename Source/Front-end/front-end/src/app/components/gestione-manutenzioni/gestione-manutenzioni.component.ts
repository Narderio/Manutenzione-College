import {Component, OnInit} from '@angular/core';
import {ManutenzioneService} from "../../services/manutenzione/manutenzione.service";
import {FiltroManutenzione} from "../../model/request/filtro-manutenzione";
import {ManutenzioneDTO} from "../../model/response/manutenzione-dto";
import {FiltraManutenzioneRequest} from "../../model/request/filtra-manutenzione-request";
import {RifiutaManutenzioneRequest} from "../../model/request/rifiuta-manutenzione-request";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {FormDialogDto} from "../../model/models/form-dialog-dto";
import {FormDialogComponent} from "../../dialogs/form-dialog/form-dialog.component";
import {ConfirmDialogDto} from "../../model/models/confirm-dialog-dto";
import {ConfirmDialogComponent} from "../../dialogs/confirm-dialog/confirm-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {LoginResponse} from "../../model/response/login-response";
import {UtenteService} from "../../services/utente/utente.service";
import {GetManutentoriResponse} from "../../model/response/get-manutentori-response";
import {Ruolo} from "../../model/models/ruolo";
import {AuthService} from "../../services/auth/auth.service";
import {SnackbarService} from "../../services/snackbar/snackbar.service";
import {FormDialogInput} from "../../model/models/form-dialog-input";

@Component({
  selector: 'app-gestione-manutenzioni',
  templateUrl: './gestione-manutenzioni.component.html',
  styleUrls: ['./gestione-manutenzioni.component.css']
})
export class GestioneManutenzioniComponent implements OnInit {

  manutenzioni: ManutenzioneDTO[]; // per salvare le manutenzioni filtrate
  manutentori: GetManutentoriResponse[]; // per filtrare la manutenzioni sulla base dei manutentori
  filtroForm: FormGroup; // form per filtrare le manutenzioni

  utente: LoginResponse; // per salvare l'utente loggato

  filtroManutenzione: FiltroManutenzione; // per filtrare le manutenzioni

  // colonne mostrate nella tabella
  colonneMostrate: string [] = ["priorita", "nome", "statoManutenzione", "dataRichiesta", "dataPrevista", "dataRiparazione", "manutentore", "actions"];


  constructor(private _manutenzioneService: ManutenzioneService, private dialog: MatDialog,
              private _utenteService: UtenteService, private _authService: AuthService,
              private _snackbarService: SnackbarService, private formBuilder: FormBuilder) {
    // ritrovamento dell'utente loggato
    this.utente = this._authService.getUtente();

    // creazione campi del form per filtrare le manutenzioni
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
    // richiesta manutenzioni con filtro vuoto e ritrovamento manutentori
    this.filtroManutenzioni();
    this.getManutentori();
  }

  /**
   * Ritrovamento manutenzioni sulla base del filtroForm
   */
  filtroManutenzioni() {
    // crea una variabie di tipo FiltroManutenzione
    this.filtroManutenzione = new FiltroManutenzione("", this.filtroForm.value.dataRichiesta, this.filtroForm.value.dataRiparazione, this.filtroForm.value.dataPrevista, this.filtroForm.value.priorita, this.filtroForm.value.manutentore, this.filtroForm.value.statoManutenzione, "", this.filtroForm.value.nome)
    this._manutenzioneService.filtroManutenzioni(this.filtroManutenzione).subscribe({
        next: result => {
          //se ha successo salva i dati in manutenzioni
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
   * Filtraggio delle manutenzioni da parte del supervisore
   */
  filtraManutenzione(manutenzione: ManutenzioneDTO) {
    const formDialogData: FormDialogDto = new FormDialogDto("Filtra la manutenzione: '" + manutenzione.nome + "'", "Filtra", [
      new FormDialogInput("Select", "Manutentore", "manutentore", [Validators.required], undefined, undefined, this.manutentori.map(manutentore => manutentore.email + "| " + manutentore.tipoManutentore),),
      new FormDialogInput("Number", "Priorita", "priorita", [Validators.required], 1, 5),
      new FormDialogInput("Text", "Nome", "nomeManutenzione", [Validators.required], undefined, undefined, undefined, manutenzione.nome),
    ])
    // apre una finestra di dialogo con form
    let dialogRef = this.dialog.open(FormDialogComponent, {
      width: "200",
      height: "500",
      data: formDialogData
    })

    dialogRef.afterClosed().subscribe(result => {
      if (!result) result;
      // se ha successo cerca il manutentore
      let manutentore = this.manutentori.find((manutentore) => manutentore.email == result.manutentore.split('|')[0]);
      if (manutentore) {
        // se il manutentore è presente crea un FiltraManutenzioneRequest e invia la richiesta Filtra al BE
        let filtraManutenzioneRequest = new FiltraManutenzioneRequest(manutenzione.id, manutentore.email, result.priorita, result.nomeManutenzione);
        this._manutenzioneService.filtraManutenzione(filtraManutenzioneRequest).subscribe({
          next: result => {
            // se ha successo richiede le manutenzioni
            this.filtroManutenzioni();
          },
          error: error => {
            // se non ha successo visualizza un messaggio di errore
            this._snackbarService.openError(error.error.message.errore);
          }
        })
      }

    })
  }

  /**
   * Ritrovamento dei manutentori
   */
  getManutentori() {
    this._utenteService.getManutentori().subscribe({
      next: result => {
        // se ha successo vengono salvati in manutentori
        this.manutentori = result;
      },
      error: (error) => {
        // se non ha successo visualizza un messaggio di errore
        this._snackbarService.openError(error.error.message.errore)
      }

    })
  }

  /**
   * Apre una finestra di dialogo e invia il rifiuta manutenzione
   */
  rifiutaManutenzione(manutenzione: ManutenzioneDTO) {
    const confirmDialogData: ConfirmDialogDto = new ConfirmDialogDto("Rifiuta Manutenzione", "Vuoi davvero rifiutare la manutenzione'" + manutenzione.nome + "'?","Rifiuta")
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

  protected readonly Ruolo = Ruolo;
}
