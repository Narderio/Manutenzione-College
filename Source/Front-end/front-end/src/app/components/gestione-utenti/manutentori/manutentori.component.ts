import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {FormDialogComponent} from "../../../dialogs/form-dialog/form-dialog.component";
import {Validators} from "@angular/forms";
import {ConfirmDialogComponent} from "../../../dialogs/confirm-dialog/confirm-dialog.component";
import {GetManutentoriResponse} from "../../../model/response/get-manutentori-response";
import {RegistrazioneRequest} from "../../../model/request/registrazione-request";
import {AggiornaRuoloRequest} from "../../../model/request/aggiorna-ruolo-request";
import {EliminaUtenteRequest} from "../../../model/request/elimina-utente-request";
import {FormDialogDto} from "../../../model/models/form-dialog-dto";
import {Ruolo} from "../../../model/models/ruolo";
import {ConfirmDialogDto} from "../../../model/models/confirm-dialog-dto";
import {FormDialogInput} from "../../../model/models/form-dialog-input";

@Component({
  selector: 'app-manutentori',
  templateUrl: './manutentori.component.html',
  styleUrls: ['./manutentori.component.css']
})
export class ManutentoriComponent {

  @Input()
  manutentori: GetManutentoriResponse[]; // per visualizzare i manutentori

  // per visualizzare le colonne in tabella
  colonneMostrate: string[] = ["email", "nome", "cognome", "dataDiNascita", "manutenzioniEffettuate", "tipoManutentore", "actions"];

  // per emettere al componente padre le informazioni di aggiungi manutentore
  @Output()
  aggiungiEmitter: EventEmitter<RegistrazioneRequest> = new EventEmitter();

  // per emettere al componente padre le informazioni di aggiorna  ruolo
  @Output()
  aggiornaRuoloEmitter: EventEmitter<AggiornaRuoloRequest> = new EventEmitter();

  // per emettere al componente padre le informazioni di elimina manutentore
  @Output()
  eliminaEmitter: EventEmitter<EliminaUtenteRequest> = new EventEmitter();

  constructor(private dialog: MatDialog) {
  }

  /**
   * Apre una finestra di dialogo con form ed emette le informazioni al componente padre
   */
  aggiungiManutentore() {
    const formDialogDto: FormDialogDto = new FormDialogDto("Inserimento Utente","Salva", [
      new FormDialogInput("Text",'Nome', "nome", [Validators.required]),
      new FormDialogInput("Text",'Cognome', "cognome", [Validators.required]),
      new FormDialogInput("Text",'Email', "email", [Validators.required]),
      new FormDialogInput("Date",'Data di nascita', "dataDiNascita", [Validators.required]),
      new FormDialogInput("Text",'Tipo di manutentore', "tipoDiManutentore", [Validators.required]),
    ])
    // apre la finestra di dialogo
    let dialogRef = this.dialog.open(FormDialogComponent, {
      height: '300px',
      width: '500px',
      data: formDialogDto
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea un registrazioneRequest e lo emette al componente padre
      let registrazioneRequest = new RegistrazioneRequest(result.nome, result.cognome, result.email, result.dataDiNascita, Ruolo.Manutentore, false, result.tipoDiManutentore);
      this.aggiungiEmitter.emit(registrazioneRequest);

    });
  }

  /**
   * Apre una finestra di dialogo con form ed emette le informazioni al componente padre
   */
  aggiornaRuolo(element: GetManutentoriResponse) {
    const formDialogData: FormDialogDto = new FormDialogDto("Modifica Ruolo a '" + element.nome + " " + element.cognome + "'",
      "Modifica", [
      new FormDialogInput('Select', 'Ruolo', "ruolo", [Validators.required],undefined,undefined,Object.keys(Ruolo),Ruolo.Manutentore),
    ]);
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
  eliminaUtente(element: GetManutentoriResponse) {
    const confirmDialogData: ConfirmDialogDto = new ConfirmDialogDto("Elimina utente","Vuoi davvero eliminare l'utente '" + element.nome + " " + element.cognome + "'?","Elimina")
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
