import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {FormDialogComponent} from "../../dialogs/form-dialog/form-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {UtenteService} from "../../services/utente/utente.service";
import {FormDialogDto} from "../../model/models/form-dialog-dto";
import {CambioPasswordRequest} from "../../model/request/cambio-password-request";
import {LoginResponse} from "../../model/response/login-response";
import {SnackbarService} from "../../services/snackbar/snackbar.service";
import {Ruolo} from "../../model/models/ruolo";
import {LuogoService} from "../../services/luogo/luogo.service";
import {AuthService} from "../../services/auth/auth.service";
import {GetStanzaDto} from "../../model/models/get-stanza-dto";
import {FormDialogInput} from "../../model/models/form-dialog-input";

@Component({
  selector: 'app-profilo',
  templateUrl: './profilo.component.html',
  styleUrls: ['./profilo.component.css']
})
export class ProfiloComponent implements OnInit {

  checkboxForm: FormGroup; // per iscriversi/disicriversi dalle email
  utente: LoginResponse; // per visualizzare i dati dell'utente loggato
  stanza: GetStanzaDto; // per visualizzare la stanza dell'utente loggato

  constructor(private dialog: MatDialog, private _utenteService: UtenteService,
              private _snackbarService: SnackbarService, private _luogoService: LuogoService,
              private _authService: AuthService, private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    // ritrovamento dell'utente loggato
    this.utente = this._authService.getUtente();

    // se l'utente è residente richiede il getStanza
    if (this.utente.ruolo == Ruolo.Residente)
      this.getStanza();

    // crea il form per l'iscrizione/disiscrizione dalla mail
    this.checkboxForm = this.formBuilder.group({
      iscrizioneEmail: [!this.utente.emailBloccate]
    });

    // sottoscrizione al cambiamento della checkbox
    this.checkboxForm.get('iscrizioneEmail')?.valueChanges.subscribe(value => {
      // richiede l'iscrizione all'email
      this._utenteService.iscrizioneEmail(value).subscribe({
        next: value => {
          // se ha successo cambia il valore dentro utente
          this.utente.emailBloccate = !this.utente.emailBloccate;
          // aggiorna l'utente all'interno del localStorage
          localStorage.setItem('utente', JSON.stringify(this.utente));
        },
        error: err => {
          // se non ha successo visualizza un messaggio di errore
          this._snackbarService.openError(err.error.message.errore)
        }
      })
    });
  }

  /**
   * Ritrovamento della stanza dell'utente
   */
  getStanza() {
    this._luogoService.getStanza().subscribe({
      next: result => {
        // se ha successo la salva in stanza
        this.stanza = result;
      },
      error: err => {
        // se non ha successo svuota la stanza e visualizza un messaggio di errore
        this.stanza = {} as GetStanzaDto;
        this._snackbarService.openError(err.error.message.errore);
      }
    })
  }

  /**
   * Apre una finestra di dialogo con form e richiede il cambio password
   */
  apriCambioPassword() {
    const formDialogData: FormDialogDto = new FormDialogDto("Cambio Password", "Salva", [
        new FormDialogInput('Password', 'Ultima Password', "ultimaPassword", [Validators.required]),
      new FormDialogInput('Password', 'Nuova Password', "nuovaPassword", [Validators.required]),
      new FormDialogInput('Password', 'Conferma Password', "confermaPassword", [Validators.required]),
      ]);
    // apre una finestra di dialogo
    let dialogRef = this.dialog.open(FormDialogComponent, {
      height: '320px',
      width: '500px',
      data: formDialogData,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea un cambioPasswordRequest e richiede il cambioPassword
      let cambioPasswordRequest: CambioPasswordRequest = new CambioPasswordRequest(result.ultimaPassword, result.nuovaPassword, result.confermaPassword);
      this._utenteService.cambioPassword(cambioPasswordRequest).subscribe({
          next: value => {
            // se ha successo visualizza un messaggio di successo
            this._snackbarService.openSuccess("Password modificata con successo");
          },
          error: err => {
            // se non ha successo visualizza un messaggio di errore
            this._snackbarService.openError(err.error.message.errore);

          }
        }
      )
    });
  }

  protected readonly Ruolo = Ruolo;
}
