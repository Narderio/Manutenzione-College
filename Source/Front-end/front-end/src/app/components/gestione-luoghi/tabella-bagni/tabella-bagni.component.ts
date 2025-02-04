import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../../../dialogs/confirm-dialog/confirm-dialog.component";
import {FormDialogComponent} from "../../../dialogs/form-dialog/form-dialog.component";
import {Validators} from "@angular/forms";
import {GetBagniResponse} from "../../../model/response/get-bagni-response";
import {EliminaLuogoRequest} from "../../../model/request/elimina-luogo-request";
import {AggiungiLuogoRequest} from "../../../model/request/aggiungi-luogo-request";
import {ConfirmDialogDto} from "../../../model/models/confirm-dialog-dto";
import {FormDialogDto} from "../../../model/models/form-dialog-dto";
import {Tipo} from "../../../model/models/tipo";
import {FormDialogInput} from "../../../model/models/form-dialog-input";

@Component({
  selector: 'app-tabella-bagni',
  templateUrl: './tabella-bagni.component.html',
  styleUrls: ['./tabella-bagni.component.css']
})
export class TabellaBagniComponent {

  colonneMostrate: string[] = ["nucleo"];   // colonne per visualizzare la tabella

  @Input()
  bagni: GetBagniResponse[]; // per visualizzare i bagni presenti

  // per emettere il dato da eliminare al componente padre
  @Output()
  eliminaEmitter: EventEmitter<EliminaLuogoRequest> = new EventEmitter();

  // per emettere il dato da aggiungere al componente padre
  @Output()
  aggiungiEmitter: EventEmitter<AggiungiLuogoRequest> = new EventEmitter();

  constructor(private dialog: MatDialog) {
  }


  /**
   * Apre una finestra di dialogo ed emette le informazioni al componente padre per aggiungere il luogo
   */
  aggiungiLuogo() {
    const formDialogData: FormDialogDto = new FormDialogDto("Inserimento Bagno",
      "Salva", [
        new FormDialogInput('Text', 'Nucleo', "nucleo", [Validators.required]),
      ]);
    let dialogRef = this.dialog.open(FormDialogComponent, {
      height: '380px',
      width: '500px',
      data: formDialogData
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea l'aggiungiLuogoRequest e lo emette al componente padre
      let aggiungiLuogoRequest = new AggiungiLuogoRequest("bagno", result.nucleo, Tipo.Bagno, 0, 1);
      this.aggiungiEmitter.emit(aggiungiLuogoRequest);
    });
  }
}
