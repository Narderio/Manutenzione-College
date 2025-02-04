import {Component, EventEmitter, Input, Output} from '@angular/core';

import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../../../dialogs/confirm-dialog/confirm-dialog.component";
import {FormDialogComponent} from "../../../dialogs/form-dialog/form-dialog.component";
import {Validators} from "@angular/forms";
import {GetStanzeResponse} from "../../../model/response/get-stanze-response";
import {ModificaStanzaRequest} from "../../../model/request/modifica-stanza-request";
import {EliminaLuogoRequest} from "../../../model/request/elimina-luogo-request";
import {AggiungiLuogoRequest} from "../../../model/request/aggiungi-luogo-request";
import {ConfirmDialogDto} from "../../../model/models/confirm-dialog-dto";
import {FormDialogDto} from "../../../model/models/form-dialog-dto";
import {Tipo} from "../../../model/models/tipo";
import {FormDialogInput} from "../../../model/models/form-dialog-input";

@Component({
  selector: 'app-tabella-stanze',
  templateUrl: './tabella-stanze.component.html',
  styleUrls: ['./tabella-stanze.component.css']
})
export class TabellaStanzeComponent {

  // colonne per visualizzare la tabella
  colonneMostrate: string[] = ["nome", "nucleo", "piano", "capienza", "residenti", "actions"];

  @Input()
  stanze: GetStanzeResponse[]; // per visualizzare le stanze presenti

  // per emettere il dato da modificare al componente padre
  @Output()
  modificaEmitter: EventEmitter<ModificaStanzaRequest> = new EventEmitter();

  // per emettere il dato da eliminare al componente padre
  @Output()
  eliminaEmitter: EventEmitter<EliminaLuogoRequest> = new EventEmitter();

  // per emettere il dato da aggiungere al componente padre
  @Output()
  aggiungiEmitter: EventEmitter<AggiungiLuogoRequest> = new EventEmitter();

  constructor(private dialog: MatDialog) {
  }

  /**
   * Apre una finestra di dialogo e invia la richiesta di eliminazione del bagno
   */
  eliminaStanza(element: GetStanzeResponse) {
    const confirmDialogData: ConfirmDialogDto = new ConfirmDialogDto("Elimina Stanza", "Vuoi davvero eliminare la stanza:  '" + element.nome + "'?", "Elimina")
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      height: '200px',
      width: '400px',
      data: confirmDialogData,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea l'eliminaLuogoRequest e lo emette al componente padre
      let eliminaLuogoRequest = new EliminaLuogoRequest(element.id);
      this.eliminaEmitter.emit(eliminaLuogoRequest);

    });
  }

  /**
   * Apre una finestra di dialogo e invia la richiesta di eliminazione del bagno
   */
  modificaStanza(element: GetStanzeResponse) {
    const formDialogData: FormDialogDto = new FormDialogDto("Modifica capienza stanza: '" + element.nome + "'", "Modifica", [
      new FormDialogInput("Number", "Capienza", "capienza", [Validators.required], undefined, undefined, undefined, element.capienza.toString()),
    ])
    let dialogRef = this.dialog.open(FormDialogComponent, {
      height: '230px',
      width: '500px',
      data: formDialogData
    })

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea il modificaStanzaRequest e lo emette al componente padre
      let modificaStanzaRequest = new ModificaStanzaRequest(element.id, result.capienza);
      this.modificaEmitter.emit(modificaStanzaRequest);
    })
  }

  /**
   * Apre una finestra di dialogo e invia la richiesta di eliminazione del bagno
   */
  aggiungiLuogo() {
    const formDialogData: FormDialogDto = new FormDialogDto("Inserimento stanza", "Salva", [
      new FormDialogInput("Text", "Nome", "nome", [Validators.required]),
      new FormDialogInput("Number", "Piano", "piano", [Validators.required]),
      new FormDialogInput("Text", "Nucleo", "nucleo", [Validators.required]),
      new FormDialogInput("Number", "Capienza", "capienza", [Validators.required]),
    ])
    //apre una finestra di dialogo
    let dialogRef = this.dialog.open(FormDialogComponent, {
      height: '380px',
      width: '500px',
      data: formDialogData
    })

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea l'aggiungiLuogoRequest e lo emette al componente padre
      let aggiungiLuogoRequest = new AggiungiLuogoRequest(result.nome, result.nucleo, Tipo.Stanza, result.piano, result.capienza);
      this.aggiungiEmitter.emit(aggiungiLuogoRequest);
    });
  }
}
