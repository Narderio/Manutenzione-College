import {inject, Injectable} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {
  private _snackBar = inject(MatSnackBar);

  constructor() {
  }

  /**
   * Mostra un messaggio di successo della durata di 2.5s
   */
  openSuccess(message: string) {
    this._snackBar.open("SUCCESS: " + message, 'OK', {
      panelClass: 'success-snackbar',
      duration: 2500,
    });
  }

  /**
   * Mostra un messaggio di errore della durata di 2.5s
   */
  openError(message: string) {
    this._snackBar.open("ERROR: " + message, 'OK', {
      panelClass: 'error-snackbar',
      duration: 2500,
    });
  }
}
