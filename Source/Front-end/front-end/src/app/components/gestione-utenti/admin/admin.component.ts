import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {FormDialogComponent} from "../../../dialogs/form-dialog/form-dialog.component";
import {Validators} from "@angular/forms";
import {ConfirmDialogComponent} from "../../../dialogs/confirm-dialog/confirm-dialog.component";
import {GetAdminResponse} from "../../../model/response/get-admin-response";
import {RegistrazioneRequest} from "../../../model/request/registrazione-request";
import {EliminaUtenteRequest} from "../../../model/request/elimina-utente-request";
import {AggiornaRuoloRequest} from "../../../model/request/aggiorna-ruolo-request";
import {FormDialogDto} from "../../../model/models/form-dialog-dto";
import {Ruolo} from "../../../model/models/ruolo";
import {ConfirmDialogDto} from "../../../model/models/confirm-dialog-dto";
import {FormDialogInput} from "../../../model/models/form-dialog-input";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {

  @Input()
  admin: GetAdminResponse[]; // per visualizzare gli admin

  // per emettere al componente padre le informazioni di aggiungi admin
  @Output()
  aggiungiEmitter: EventEmitter<RegistrazioneRequest> = new EventEmitter();

  // per emettere al componente padre le informazioni di elimina admin
  @Output()
  eliminaEmitter: EventEmitter<EliminaUtenteRequest> = new EventEmitter();

  // per emettere al componente padre le informazioni di aggiorna ruolo
  @Output()
  aggiornaRuoloEmitter: EventEmitter<AggiornaRuoloRequest> = new EventEmitter();

  // per visualizzare le colonne in tabella
  colonneMostrate: string[] = ["email", "nome", "cognome", "dataDiNascita", "actions"];

  constructor(private dialog: MatDialog) {
  }

  /**
   * Apre una finestra di dialogo con form ed emette al componente padre le informazioni
   */
  aggiungiUtente() {
    const formDialogData: FormDialogDto = new FormDialogDto("Inserimento Utente", "Salva", [
      new FormDialogInput('Text', "Nome", "nome", [Validators.required]),
      new FormDialogInput('Text', "Cognome", "cognome", [Validators.required]),
      new FormDialogInput('Text', "Email", "email", [Validators.required]),
      new FormDialogInput('Date', "Data di nascita", "dataDiNascita", [Validators.required]),
    ])
    // apre la finestra di dialogo
    let dialogRef = this.dialog.open(FormDialogComponent, {
      height: '400px',
      width: '500px',
      data: formDialogData
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea un registraRequest e lo emette al componente padre
      let registrazioneRequest = new RegistrazioneRequest(result.nome, result.cognome, result.email, result.dataDiNascita, Ruolo.Admin, false, "");
      this.aggiungiEmitter.emit(registrazioneRequest);

    });
  }

  /**
   * Apre una finestra di dialogo con form ed emette al component padre le informazioni
   */
  aggiornaRuolo(element: GetAdminResponse) {
    const formDialogData: FormDialogDto = new FormDialogDto("Modifica Ruoli a '" + element.nome + " " + element.cognome + "'", "Modifica", [
      new FormDialogInput('Select', "Ruolo", "ruolo", [Validators.required], undefined, undefined, Object.keys(Ruolo), Ruolo.Admin),
    ])
    // apre la finestra di dialogo
    let dialogRef = this.dialog.open(FormDialogComponent, {
      height: '230px',
      width: '500px',
      data: formDialogData
    })

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea un aggiornaRuoloRequest e lo emette al componente padre
      let aggiornaRuoloRequest = new AggiornaRuoloRequest(element.email, result.ruolo);
      this.aggiornaRuoloEmitter.emit(aggiornaRuoloRequest);
    })
  }

  /**
   * Apre una finestra di dialogo ed emette le informazioni al componente padre
   */
  eliminaUtente(element: GetAdminResponse) {
    const confirmDialogData: ConfirmDialogDto = new ConfirmDialogDto("Elimina utente", "Vuoi davvero eliminare l'utente '" + element.nome + " " + element.cognome + "'?", "Elimina")
    // apre la finestra di dialogo
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      height: '200px',
      width: '400px',
      data: confirmDialogData
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea un eliminaUtenteRequest e lo emette al componente padre
      let eliminaUtenteRequest = new EliminaUtenteRequest(element.email);
      this.eliminaEmitter.emit(eliminaUtenteRequest);

    });
  }
}
