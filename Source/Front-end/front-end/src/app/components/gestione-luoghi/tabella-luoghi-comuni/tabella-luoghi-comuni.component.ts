import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../../../dialogs/confirm-dialog/confirm-dialog.component";
import {FormDialogComponent} from "../../../dialogs/form-dialog/form-dialog.component";
import {Validators} from "@angular/forms";
import {GetLuoghiComuniResponse} from "../../../model/response/get-luoghi-comuni-response";
import {EliminaLuogoRequest} from "../../../model/request/elimina-luogo-request";
import {AggiungiLuogoRequest} from "../../../model/request/aggiungi-luogo-request";
import {ConfirmDialogDto} from "../../../model/models/confirm-dialog-dto";
import {FormDialogDto} from "../../../model/models/form-dialog-dto";
import {Tipo} from "../../../model/models/tipo";
import {FormDialogInput} from "../../../model/models/form-dialog-input";

@Component({
  selector: 'app-tabella-luoghi-comuni',
  templateUrl: './tabella-luoghi-comuni.component.html',
  styleUrls: ['./tabella-luoghi-comuni.component.css']
})
export class TabellaLuoghiComuniComponent {

  colonneMostrate: string[] = ["nome", "actions"]; // colonne per visualizzare la tabella

  @Input()
  luoghiComuni: GetLuoghiComuniResponse[]; // per visualizzare i luoghi comuni presenti

  // per emettere il dato da eliminare al componente padre
  @Output()
  eliminaEmitter: EventEmitter<EliminaLuogoRequest> = new EventEmitter();

  // per emettere il dato da aggiungere al componente padre
  @Output()
  aggiungiEmitter: EventEmitter<AggiungiLuogoRequest> = new EventEmitter();

  constructor(private dialog: MatDialog) {
  }

  /**
   * Apre una finestra di dialogo ed emette le informazioni al componente padre per eliminare il luogo comune
   */
  eliminaLuogoComune(element: GetLuoghiComuniResponse) {
    const confirmDialogData: ConfirmDialogDto = new ConfirmDialogDto("Elimina Luogo comune",
      "Vuoi davvero eliminare il luogo comune:  '" + element.nome + "'?", "Elimina")
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      height: '200px',
      width: '400px',
      data: confirmDialogData
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea l'eliminaLuogoRequest e lo emette al componente padre
      let eliminaLuogoRequest = new EliminaLuogoRequest(element.id);
      this.eliminaEmitter.emit(eliminaLuogoRequest);

    });
  }

  /**
   * Apre una finestra di dialogo ed emette le informazioni al componente padre per aggiungere il luogo
   */
  aggiungiLuogo() {
    const formDialogData: FormDialogDto = new FormDialogDto("Inserimento Luogo Comune", "Salva", [
      new FormDialogInput("Text", 'Nome', "nome", [Validators.required])
    ])
    let dialogRef = this.dialog.open(FormDialogComponent, {
      height: '380px',
      width: '500px',
      data: formDialogData
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea l'aggiungiLuogoRequest e lo emette al componente padre
      let aggiungiLuogoRequest = new AggiungiLuogoRequest(result.nome, "", Tipo.LuogoComune, 0, 1);
      this.aggiungiEmitter.emit(aggiungiLuogoRequest);
    });
  }

}
