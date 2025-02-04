import {Component, OnInit} from '@angular/core';
import {ManutenzioneService} from "../../services/manutenzione/manutenzione.service";
import {LuogoService} from "../../services/luogo/luogo.service";
import {GetLuoghiResponse} from "../../model/response/get-luoghi-response";
import {RichiediManutenzioneRequest} from "../../model/request/richiedi-manutenzione-request";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SnackbarService} from "../../services/snackbar/snackbar.service";

@Component({
  selector: 'app-segnala-manutenzione',
  templateUrl: './segnala-manutenzione.component.html',
  styleUrls: ['./segnala-manutenzione.component.css']
})
export class SegnalaManutenzioneComponent implements OnInit {

  // per inviare le informazioni al BE
  manutenzioneForm: FormGroup;
  // per selezionare i luoghi nel form
  luoghi: GetLuoghiResponse[];

  nomeImmagine: string;

  constructor(private _manutenzioneService: ManutenzioneService, private _luogoService: LuogoService,
              private formBuilder: FormBuilder, private _snackbarService: SnackbarService) {
    // crea il manutenzione form
    this.manutenzioneForm = this.formBuilder.group({
      idLuogo: [null, Validators.required], // idLuogo
      descrizione: [null, Validators.required], // Descrizione
      nome: [null, Validators.required], // Nome manutenzione
      immagine: [null], // Immagine manutenzione
    });
  }

  ngOnInit() {
    // richiede i luoghi per selezionarli
    this.getLuoghi();
  }

  /**
   * Ritrovamento dei luoghi
   */
  getLuoghi() {
    this._luogoService.getLuoghi().subscribe({
        next: data => {
          // se ha successo li salva in luoghi
          this.luoghi = data;
        },
        error: (error) => {
          // se non ha successo visualizza un messaggio di errore
          this._snackbarService.openError(error.error.message.errore);
        }
      }
    )
  }

  /**
   * Invia una richiesta di aggiunta manutenzione
   */
  richiediManutenzione() {
    if (this.manutenzioneForm.valid) {
      // se il form è valido crea un RichiediManutenzioneRequest e lo invia al BE
      let manutenzioneRichiesta: RichiediManutenzioneRequest = new RichiediManutenzioneRequest(this.manutenzioneForm.value.idLuogo, this.manutenzioneForm.value.descrizione, this.manutenzioneForm.value.nome, this.manutenzioneForm.value.immagine);
      this._manutenzioneService.richiediManutenzione(manutenzioneRichiesta).subscribe({
          next: result => {
            // se ha successo pulisce il form
            this.clear();
          },
          error: error => {
            // se non ha successo visualizza un messaggio di errore
            this._snackbarService.openError(error.error.message.errore)
          }
        }
      )
    }
  }

  /**
   * Converte l'immagine in base64
   */
  convertImageToBase64(event: any): void {
    // Controllo se esistono file caricati
    if (event.target.files && event.target.files[0]) {
      const immagine = event.target.files[0];
      const reader = new FileReader();

      this.nomeImmagine = immagine.name;

      // Quando il reader finisce di caricare
      reader.onload = (e) => {
        // e.target?.result è la stringa base64
        const immagineBase64 = e.target?.result as string;
        // la stringa viene inserita nel form
        this.manutenzioneForm.patchValue({immagine: immagineBase64});
      };

      // Avvia la lettura del file come DataURL (base64)
      reader.readAsDataURL(immagine);

    }
  }

  /**
   * Pulisce il manutenzioneForm
   */
  clear(): void {
    this.manutenzioneForm.reset();
    Object.keys(this.manutenzioneForm.controls).forEach(key => {
      this.manutenzioneForm.controls[key].setErrors(null)
    });
  }

  /**
   * Pulisce il manutenzioneForm
   */
  pulisciImmagine() {
    if (this.manutenzioneForm.value.immagine) {
      this.manutenzioneForm.patchValue({immagine: ""});
      this.nomeImmagine = "";
    }
  }

}
