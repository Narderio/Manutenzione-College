import {Component} from '@angular/core';
import {GetManutenzioneDTO} from "../../model/response/get-manutenzione-dto";
import {ActivatedRoute,} from "@angular/router";
import {ManutenzioneService} from "../../services/manutenzione/manutenzione.service";
import {ImageDialogComponent} from "../../dialogs/image-dialog/image-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {SnackbarService} from "../../services/snackbar/snackbar.service";
import {ImageDialogDto} from "../../model/models/image-dialog-dto";

@Component({
  selector: 'app-dettaglio-manutenzione',
  templateUrl: './dettaglio-manutenzione.component.html',
  styleUrls: ['./dettaglio-manutenzione.component.css']
})
export class DettaglioManutenzioneComponent {

  manutenzione: GetManutenzioneDTO; // per salvare la manutenzione

  id: string; // per salvare l'id per richiamare il getManutenzioneById

  constructor(private route: ActivatedRoute, private _manutenzioneService: ManutenzioneService,
              private dialog: MatDialog, private _snackbarService: SnackbarService) {
    // ritrovamento dell'id dalla rotta
    this.id = <string>this.route.snapshot.paramMap.get('id');

  }

  ngOnInit() {
    // ritrovamento della manutenzione sulla base dell'id
    this._manutenzioneService.getManutenzioneById(this.id).subscribe({
      next: result => {
        // se ha successo viene salvata in manutenzione
        this.manutenzione = result;
      },
      error: error => {
        // se non ha successo probabilmente l'utente non ha accesso
        // quindi viene visualizzato un messaggio di errore e reindirizza alla pagina precedente
        this._snackbarService.openError(error.error.message.errore);
        this.tornaIndietro();
      }
    })
  }

  /**
   * Apre la finestra di dialogo per ingrandire l'immagine
   */
  openImageDialog(image: string) {
    const imageDialogData: ImageDialogDto = new ImageDialogDto(image);
    this.dialog.open(ImageDialogComponent, {
      height: '600px',
      width: '800px',
      data: imageDialogData
    })
  }

  /**
   * Reindirizza alla pagina da cui proviene l'utente
   */
  tornaIndietro() {
    window.history.back();
  }
}
