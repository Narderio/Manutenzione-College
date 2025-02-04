import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ConfirmDialogDto} from "../../model/models/confirm-dialog-dto";
import {FormBuilder} from "@angular/forms";
import {ImageDialogDto} from "../../model/models/image-dialog-dto";

@Component({
  selector: 'app-image-dialog',
  templateUrl: './image-dialog.component.html',
  styleUrls: ['./image-dialog.component.css']
})
export class ImageDialogComponent {

  constructor(private _dialogRef: MatDialogRef<ImageDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: ImageDialogDto) {
  }

  /**
   * Chiude la finestra di dialogo
   */
  close(bool: boolean) {
    this._dialogRef.close(bool);
  }
}
