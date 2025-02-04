import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {FormDialogComponent} from "../../../dialogs/form-dialog/form-dialog.component";
import {Validators} from "@angular/forms";
import {ConfirmDialogComponent} from "../../../dialogs/confirm-dialog/confirm-dialog.component";
import {GetSupervisoriResponse} from "../../../model/response/get-supervisori-response";
import {RegistrazioneRequest} from "../../../model/request/registrazione-request";
import {EliminaUtenteRequest} from "../../../model/request/elimina-utente-request";
import {AggiornaRuoloRequest} from "../../../model/request/aggiorna-ruolo-request";
import {FormDialogDto} from "../../../model/models/form-dialog-dto";
import {Ruolo} from "../../../model/models/ruolo";
import {ConfirmDialogDto} from "../../../model/models/confirm-dialog-dto";
import {FormDialogInput} from "../../../model/models/form-dialog-input";

@Component({
  selector: 'app-supervisori',
  templateUrl: './supervisori.component.html',
  styleUrls: ['./supervisori.component.css']
})
export class SupervisoriComponent {

  @Input()
  supervisori: GetSupervisoriResponse[]; // per visualizzare i supervisori

  // per emettere al componente padre le informazioni di aggiungi supervisore
  @Output()
  aggiungiEmitter: EventEmitter<RegistrazioneRequest> = new EventEmitter();

  // per emettere al componente padre le informazioni di elimina supervisore
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
   * Apre una finestra di dialogo con form ed emette le informazioni al componente padre
   */
  aggiungiUtente() {
    const formDialogData: FormDialogDto = new FormDialogDto("Inserimento Utente", "Salva", [
      new FormDialogInput("Text", "Nome", "nome", [Validators.required]),
      new FormDialogInput("Text", "Cognome", "cognome", [Validators.required]),
      new FormDialogInput("Text", "Email", "email", [Validators.required]),
      new FormDialogInput("Date", "Data di Nascita", "dataDiNascita", [Validators.required])
    ])
    // apre una finestra di dialogo
    let dialogRef = this.dialog.open(FormDialogComponent, {
      height: '400px',
      width: '500px',
      data: formDialogData
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea un registraRequest e lo emette al componente padre
      let registrazioneRequest = new RegistrazioneRequest(result.nome, result.cognome, result.email, result.dataDiNascita, Ruolo.Supervisore, false, "");
      this.aggiungiEmitter.emit(registrazioneRequest);

    });
  }

  /**
   * Apre una finestra di dialogo con form ed emette le informazioni al componente padre
   */
  aggiornaRuolo(element: GetSupervisoriResponse) {
    const formDialogData: FormDialogDto = new FormDialogDto("Modifica Ruoli a '" + element.nome + " " + element.cognome + "'", "Modifica", [
      new FormDialogInput("Select", "Ruolo", "ruolo", [Validators.required], undefined, undefined, Object.keys(Ruolo), Ruolo.Supervisore),
    ])
    // apre una finestra di dialogo
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
  eliminaUtente(element: GetSupervisoriResponse) {
    const confirmDialogData: ConfirmDialogDto = new ConfirmDialogDto("Elimina Utente","Vuoi davvero eliminare l'utente '" + element.nome + " " + element.cognome + "'?", "Elimina")
    // apre una finestra di dialogo
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      height: '200px',
      width: '400px',
      data: confirmDialogData
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea un eliminaUtenteRequest e lo emette al padre
      let eliminaUtenteRequest = new EliminaUtenteRequest(element.email);
      this.eliminaEmitter.emit(eliminaUtenteRequest);

    });
  }

}
