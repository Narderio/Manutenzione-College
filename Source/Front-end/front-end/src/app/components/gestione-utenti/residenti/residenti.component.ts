import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ConfirmDialogComponent} from "../../../dialogs/confirm-dialog/confirm-dialog.component";
import {Validators} from "@angular/forms";
import {FormDialogComponent} from "../../../dialogs/form-dialog/form-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {LuogoService} from "../../../services/luogo/luogo.service";
import {GetStanzeResponse} from "../../../model/response/get-stanze-response";
import {GetResidentiResponse} from "../../../model/response/get-residenti-response";
import {RegistrazioneRequest} from "../../../model/request/registrazione-request";
import {AggiornaRuoloRequest} from "../../../model/request/aggiorna-ruolo-request";
import {AggiungiResidenteInStanzaRequest} from "../../../model/request/aggiungi-residente-in-stanza-request";
import {EliminaResidenteDaStanzaRequest} from "../../../model/request/elimina-residente-da-stanza-request";
import {EliminaUtenteRequest} from "../../../model/request/elimina-utente-request";
import {FormDialogDto} from "../../../model/models/form-dialog-dto";
import {Ruolo} from "../../../model/models/ruolo";
import {ConfirmDialogDto} from "../../../model/models/confirm-dialog-dto";
import {SnackbarService} from "../../../services/snackbar/snackbar.service";
import {FormDialogInput} from "../../../model/models/form-dialog-input";

@Component({
  selector: 'app-residenti',
  templateUrl: './residenti.component.html',
  styleUrls: ['./residenti.component.css']
})
export class ResidentiComponent {

  // permette di fare l'aggiorna stanza
  stanze: GetStanzeResponse[];

  @Input()
  residenti: GetResidentiResponse[]; // per visualizzare i residenti

  // per emettere al componente padre le informazioni di aggiungi residente
  @Output()
  aggiungiEmitter: EventEmitter<RegistrazioneRequest> = new EventEmitter();

  // per emettere al componente padre le informazioni di aggiorna ruolo
  @Output()
  aggiornaRuoloEmitter: EventEmitter<AggiornaRuoloRequest> = new EventEmitter();

  // per emettere al componente padre le informazioni di aggiorna stanza
  @Output()
  aggiornaStanzaEmitter: EventEmitter<AggiungiResidenteInStanzaRequest | EliminaResidenteDaStanzaRequest> = new EventEmitter();

  // per emettere al componente padre le informazioni di elimina residente
  @Output()
  eliminaEmitter: EventEmitter<EliminaUtenteRequest> = new EventEmitter();

  // per visualizzare le colonne in tabella
  colonneMostrate: string[] = ["email", "nome", "cognome", "dataDiNascita", "actions"];

  constructor(private dialog: MatDialog, private _luogoService: LuogoService,
              private _snackbarService: SnackbarService) {
  }

  ngOnInit(): void {
    // richiede le stanze per fare l'aggiorna stanza
    this.getStanze();
  }

  /**
   * Apre una finestra di dialogo con form ed emette le informazioni al componente padre
   */
  aggiungiUtente() {
    // apre una finestra di dialogo
    const formDialogData: FormDialogDto = new FormDialogDto("Inserimento Residente", "Salva", [
      new FormDialogInput('Text', 'Nome', "nome", [Validators.required]),
      new FormDialogInput('Text', 'Cognome', "cognome", [Validators.required]),
      new FormDialogInput('Text', 'Email', "email", [Validators.required]),
      new FormDialogInput('Date', 'Data di Nascita', "dataDiNascita", [Validators.required])]);

    let dialogRef = this.dialog.open(FormDialogComponent, {
      height: '400px',
      width: '500px',
      data: formDialogData
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea un registrazioneRequest e lo emette al componente padre
      let registrazioneRequest = new RegistrazioneRequest(result.nome, result.cognome, result.email, result.dataDiNascita, Ruolo.Residente, false, "");
      this.aggiungiEmitter.emit(registrazioneRequest);

    });
  }

  /**
   * Apre una finestra di dialogo con form ed emette le informazioni al componente padre
   */
  aggiornaStanzaResidente(element: GetResidentiResponse) {
    const formDialogData: FormDialogDto = new FormDialogDto("Modifica Stanza Residente: '" + element.nome + " " + element.cognome + "'",
      "Modifica Stanza", [
        new FormDialogInput('Select', 'Stanza', "stanza", [Validators.required], undefined, undefined, undefined, element.stanza)]);
    // apre una finestra di dialogo
    let dialogRef = this.dialog.open(FormDialogComponent, {
      height: '250px',
      width: '550px',
      data: formDialogData
    })

    dialogRef.afterClosed().subscribe(result => {
      if (element.stanza) {
        // se l'utente precedentemente era in una stanza
        if (result.stanza != null) {
          // se il risultato è presente significa che è stata aggiornata la sua stanza
          // quindi crea un aggiungiResidenteInStanzaRequest e lo emette al componente padre
          let eliminaResidenteDaStanza = new EliminaResidenteDaStanzaRequest(element.email);
          this.aggiornaStanzaEmitter.emit(eliminaResidenteDaStanza);
          setTimeout(() => {
            let aggiungiResidenteInStanzaRequest = new AggiungiResidenteInStanzaRequest(element.email, result.stanza);
            this.aggiornaStanzaEmitter.emit(aggiungiResidenteInStanzaRequest);
          }, 2000);
        } else {
          // se il risultato non è presente significa che è stato eliminato dalla stanza
          // quindi crea un eliminaResidenteDaStanza e lo emette al componente padre
          let eliminaResidenteDaStanza = new EliminaResidenteDaStanzaRequest(element.email);
          this.aggiornaStanzaEmitter.emit(eliminaResidenteDaStanza);
        }
      } else {
        // se l'utente precedentemente non era in una stanza
        if (result) {
          // se il risultato è presente significa che è stato aggiunto ad una stanza
          // quindi crea un aggiungiResidenteInStanzaRequest e lo emette al componente padre
          let aggiungiResidenteInStanzaRequest = new AggiungiResidenteInStanzaRequest(element.email, result.stanza);
          this.aggiornaStanzaEmitter.emit(aggiungiResidenteInStanzaRequest);
        }
      }
    })

  }

  /**
   * Apre una finestra di dialogo con form ed emette le informazioni al componente padre
   */
  aggiornaRuolo(element: GetResidentiResponse) {
    const formDialogData: FormDialogDto = new FormDialogDto("Modifica Ruolo a '" + element.nome + " " + element.cognome + "'",
      "Modifica", [
        new FormDialogInput('Select', 'Ruolo', "ruolo", [Validators.required], undefined, undefined, Object.keys(Ruolo), Ruolo.Residente),
      ]);
    // apre una finestra di dialogo
    let dialogRef = this.dialog.open(FormDialogComponent, {
      height: '230px',
      width: '500px',
      data: formDialogData
    })

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo crea un aggiornRuoloRequest e lo  emette al padre
      let aggiornaRuoloRequest = new AggiornaRuoloRequest(element.email, result.ruolo);
      this.aggiornaRuoloEmitter.emit(aggiornaRuoloRequest);
    })
  }

  /**
   * Apre una finestra di dialogo ed emette le informazioni al componente padre
   */
  eliminaUtente(element: GetResidentiResponse) {
    const confirmDialogData: ConfirmDialogDto = new ConfirmDialogDto("Elimina utente", "Vuoi davvero eliminare l'utente '" + element.nome + " " + element.cognome + "'?", "Elimina")
    // apre la finestra di dialogo
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

  /**
   * Ritrovamento delle stanze
   */
  getStanze() {
    this._luogoService.getStanze().subscribe({
        next: result => {
          // se ha successo le salva instanze
          this.stanze = result;
        },
        error: error => {
          // se non ha successo visualizza un messaggio di errore
          this._snackbarService.openError(error.error.message.errore);
        }
      }
    )
  }

}
