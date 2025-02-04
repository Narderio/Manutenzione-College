import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {UtenteService} from "../../services/utente/utente.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ChatService} from "../../services/chat/chat.service";
import {MessaggioDTO} from "../../model/response/messaggio-dto";
import {GetUtentiResponse} from "../../model/response/get-utenti-response";
import {InviaMessaggioDTO} from "../../model/request/invia-messaggio-dto";
import {LoginResponse} from "../../model/response/login-response";
import {WebSocketService} from "../../services/web-socket/web-socket.service";
import {EliminaMessaggioDTO} from "../../model/request/elimina-messaggio-dto";
import {ConfirmDialogComponent} from "../../dialogs/confirm-dialog/confirm-dialog.component";
import {ConfirmDialogDto} from "../../model/models/confirm-dialog-dto";
import {MatDialog} from "@angular/material/dialog";
import {FormDialogComponent} from "../../dialogs/form-dialog/form-dialog.component";
import {FormDialogDto} from "../../model/models/form-dialog-dto";
import {ModificaMessaggioDTO} from "../../model/request/modifica-messaggio-dto";
import {TipoMessaggioInviato} from "../../model/models/tipo-messaggio-inviato";
import {ManutenzioneService} from "../../services/manutenzione/manutenzione.service";
import {GetManutenzioniDto} from "../../model/response/get-manutenzioni-dto";
import {SnackbarService} from "../../services/snackbar/snackbar.service";
import {ImageDialogComponent} from "../../dialogs/image-dialog/image-dialog.component";
import {AuthService} from "../../services/auth/auth.service";
import {FormDialogInput} from "../../model/models/form-dialog-input";
import {Ruolo} from "../../model/models/ruolo";
import {ImageDialogDto} from "../../model/models/image-dialog-dto";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {

  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;

  // formGroup dei 2 form della pagina
  utenteForm: FormGroup;
  messaggioForm: FormGroup;

  utenteLoggato: LoginResponse; // per capire chi è il mittente e il destinatario dei messaggi
  destinatario: string; // per chiamate al BE e sottoscrizione a rabbitMQ

  chat: MessaggioDTO[]; // array di messaggi
  messaggiMap: { [id: number]: MessaggioDTO } = {}; // per i messaggi taggati

  utenti: GetUtentiResponse[]; // per la selezione dell'utente a cui scrivere
  manutenzioni: GetManutenzioniDto[]; // per la selezione della manutenzione da inviare

  @ViewChild('chatContainer') contenitoreChat: ElementRef; // per scrollare in basso quando si aggiorna la chat

  messaggioRiferimento: MessaggioDTO; // per essere visualizzato e inviato al BE.

  constructor(private _utenteService: UtenteService, private _chatService: ChatService,
              private _webSocketService: WebSocketService, private dialog: MatDialog,
              private _manutenzioneService: ManutenzioneService, private _snackbarService: SnackbarService,
              private _authService: AuthService, private formBuilder: FormBuilder
  ) {
    this.utenteForm = this.formBuilder.group({
      utenti: [""]
    });

    this.messaggioForm = this.formBuilder.group({
      messaggio: [""]
    });

  }

  ngOnInit() {
    // ritrovamento dell'utente
    this.utenteLoggato = this._authService.getUtente();
    this._webSocketService.connect(); // connessione alla websocket

    // sottoscrizione alla selezione dell'utente
    this.utenteForm.get('utenti')?.valueChanges.subscribe((valoreSelezionato) => {
      if (valoreSelezionato) {
        // se viene selezionato prende il valore lo inserisce nel destinatario
        this.destinatario = valoreSelezionato;

        // si sottoscrive al topic di rabbitMQ e manda una richiesta di aggiornamento chat
        this.subscribeToChat(valoreSelezionato);
        this.aggiornaChat();
      }
    });

    // ritrovamento di utenti e manutenzioni
    this.getUtenti();
    this.getManutenzioni();
  }

  /**
   * Elimina la connessione alla websocket
   */
  ngOnDestroy(): void {
    this._webSocketService.disconnect();
  }

  /**
   * Invio del messaggio al backend
   */
  inviaMessaggio(tipoMesssaggio: TipoMessaggioInviato) {
    // controlla che sia selezionato un destinatario e che il messaggio non sia vuoto
    if (!this.destinatario && !this.messaggioForm.value.messaggio) return;

    // crea il dto, imposta i dati che arrivano dal messaggioForm e invia la richiesta al backend
    const inviaMessaggioDTO: InviaMessaggioDTO = new InviaMessaggioDTO(this.destinatario, this.messaggioForm.value.messaggio, this.messaggioRiferimento ? this.messaggioRiferimento.id : 0, tipoMesssaggio);
    this._chatService.inviaMessaggio(inviaMessaggioDTO).subscribe({
      next: result => {
        // se la chiamata ha successo resetta il form e svuota il messaggio taggato
        this.messaggioForm.reset();
        this.setMessaggioDaRispondere();
      },
      error: err => {
        // se la chiamata non ha successo resetta il form e visualizza un messaggio di errore
        this.messaggioForm.reset();
        this._snackbarService.openError(err.error.message.errore);
      }
    })
  }

  /**
   * Converte l'immagine in base64 e la invia al backend
   */
  convertImageToBase64(event: any): void {
    // Controlla se esistono file caricati
    if (event.target.files && event.target.files[0]) {
      const file = event.target.files[0];
      const reader = new FileReader();

      // Quando il reader finisce di caricare
      reader.onload = (e) => {
        // prende l'immagine e la inserisce nel form
        const immagineBase64 = e.target?.result as string;
        this.messaggioForm.patchValue({messaggio: immagineBase64});

        // richiama l'invia messaggio con tipoMessaggio Immagine
        this.inviaMessaggio(TipoMessaggioInviato.IMMAGINE);
      };


      // Avvia la lettura del file come DataURL (base64)
      reader.readAsDataURL(file);
    }
  }

  /**
   * Recupera i messaggi e aggiorna la chat
   */
  aggiornaChat(): void {
    // svuota la mappa dei messaggi
    this.messaggiMap = {};

    // richiama l'api
    this._chatService.getChat(this.destinatario).subscribe({
      next: (result) => {
        if (result) {
          // se ha successo salvo i valori in chat e messaggiMap
          this.chat = result;
          result.forEach(messaggio => {
            this.messaggiMap[messaggio.id] = messaggio; // necessaria per i messaggi taggati
          });
          setTimeout(() => {
            this.scrollToBottom(); // Dopo un leggero ritardo scorre verso il basso la chat
          }, 100);
        } else {
          this.chat = [];
        }
      },
      error: (err) => {
        // se non ha successo mostra un messaggio di errore e svuota la chat
        this._snackbarService.openError(err.error.message.errore)
        this.chat = [];
        this.destinatario = "";
      },
    });
  }

  /**
   * Richiama la getUtenti per poter selezionare il destinatario della chat
   */
  getUtenti() {
    this._utenteService.getUtenti().subscribe({
      next: result => {
        // se ha successo salva gli utenti
        this.utenti = result;
      },
      error: err => {
        // se non ha successo mostra un messaggio di errore
        this._snackbarService.openError(err.error.message.errore)
      }
    })
  }

  /**
   * Richiama la getManutenzioni per essere inviate nella chat
   */
  getManutenzioni() {
    this._manutenzioneService.getManutenzioni().subscribe({
      next: result => {
        // se ha successo salva le manutenzioni
        this.manutenzioni = result
      },
      error: err => {
        // se non ha successo mostra un messaggio di errore
        this._snackbarService.openError(err.error.message.errore)
      }
    })
  }

  /**
   * Sottoscrizione al topic di rabbitMQ
   */
  subscribeToChat(email: string): void {
    this._webSocketService.subscribeToUserTopic(email, (message) => {
      this.aggiornaChat();
    });
  }

  /**
   * Apre una finestra di dialogo e invia la richiesta di elimina
   */
  eliminaMessaggio(messaggio: MessaggioDTO) {
    const confirmDialogData: ConfirmDialogDto = new ConfirmDialogDto("Elimina Messaggio",
      messaggio.tipoMessaggio == TipoMessaggioInviato.TESTO ? "Vuoi davvero eliminare il messaggio: '" + messaggio.testo + "'?" : "Vuoi davvero eliminare il messaggio?",
      "Elimina")
    //Apre una finestra di dialogo
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      height: '200px',
      width: '600px',
      data: confirmDialogData,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return; // se viene chiusa interrompe

      // altrimenti crea un eliminaMessaggioDto e invia la richiesta al BE
      const eliminaMessaggioDto: EliminaMessaggioDTO = new EliminaMessaggioDTO(messaggio.id);
      this._chatService.eliminaMessaggio(eliminaMessaggioDto).subscribe({
          next: result => {
            // se ha successo aggiorna la chat
            this.aggiornaChat();
          },
          error: err => {
            // se non ha successo mostra un messaggio di errore
            this._snackbarService.openError(err.error.message.errore);
          }
        }
      )
    });
  }

  /**
   * Apre una finestra di dialogo e invia la richiesta di modifica
   */
  modificaMessaggio(messaggio: MessaggioDTO) {
    const formDialogData: FormDialogDto = new FormDialogDto("Modifica messaggio", "Modifica", [
      new FormDialogInput('Text', 'Messaggio Modificato', "testo", [Validators.required], undefined, undefined, undefined, messaggio.testo),
    ])
    // apre una finestra di dialogo con un form
    let dialogRef = this.dialog.open(FormDialogComponent, {
      height: '230px',
      width: '500px',
      data: formDialogData
    })

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return; // se viene chiusa interrompe

      // altrimenti crea il modificaMessaggioDto e invia la richiesta al BE
      const modificaMessaggioDto: ModificaMessaggioDTO = new ModificaMessaggioDTO(messaggio.id, result.testo);
      this._chatService.modificaMessaggio(modificaMessaggioDto).subscribe({
          next: result => {
            // se ha successo aggiorna la chat
            this.aggiornaChat();
          },
          error: err => {
            // se non ha successo mostra un messaggio di errore
            this._snackbarService.openError(err.error.message.errore);
          }
        }
      )
    })
  }

  /**
   * Apre una finestra di dialogo e invia un messaggio di tipo manutenzione
   */
  inviaManutenzione() {
    if (!this.manutenzioni) {
      this._snackbarService.openError("Manutenzioni non presenti");
      return;
    }
    const formDialogData: FormDialogDto = new FormDialogDto("Invia Manutenzione", "Invia", [
      new FormDialogInput("Select", "Manutenzione", "manutenzione", [Validators.required], undefined, undefined, this.manutenzioni.map(manutenzione => manutenzione.idManutenzione + "| " + manutenzione.nome))
    ])
    // apre la finestra di dialogo con form
    let dialogRef = this.dialog.open(FormDialogComponent, {
      height: '230px',
      width: '500px',
      data: formDialogData
    })

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return; // se viene chiusa interrompe

      // altrimenti inserisce un testo custom nel messaggioForm
      this.messaggioForm.patchValue({messaggio: "Manutenzione@" + result.manutenzione.split('|')[0]})
      // richiama l'inviaMessaggio
      this.inviaMessaggio(this.TipoMessaggioInviato.MANUTENZIONE);
    })
  }

  /**
   * Trascina la chat verso il basso per vedere i nuovi messaggi
   */
  scrollToBottom() {
    this.contenitoreChat.nativeElement.scrollTop = this.contenitoreChat.nativeElement.scrollHeight;
  }

  /**
   * Inserisce o svuota la variabile messaggioRiferimento che viene mostrata
   * durante l'invio di un messaggio taggato
   */
  setMessaggioDaRispondere(messaggio?: MessaggioDTO) {
    this.messaggioRiferimento = messaggio ? messaggio : {} as MessaggioDTO;
  }

  /**
   * Al click dell'opzione invia immagine triggera il click
   * sull'input di tipo immagine
   */
  fileInputClick(): void {
    this.fileInput.nativeElement.click();
  }

  /**
   * Apre una finestra di dialogo per vedere l'immagine ingrandita
   */
  apriFinestraImmagine(image: string) {
    const immagineDataDto: ImageDialogDto = new ImageDialogDto(image);
    this.dialog.open(ImageDialogComponent, {
      height: '600px',
      width: '800px',
      data: immagineDataDto
    })
  }


  protected readonly TipoMessaggioInviato = TipoMessaggioInviato;
}
