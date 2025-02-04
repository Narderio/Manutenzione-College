import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup} from "@angular/forms";
import {FormDialogDto} from "../../model/models/form-dialog-dto";

@Component({
  selector: 'app-form-dialog',
  templateUrl: './form-dialog.component.html',
  styleUrls: ['./form-dialog.component.css']
})
export class FormDialogComponent {
  // form della finestra di dialogo
  form: FormGroup;

  constructor(private _dialogRef: MatDialogRef<Component>, @Inject(MAT_DIALOG_DATA) public data: FormDialogDto,
              private formBuilder: FormBuilder) {
    this.form = formBuilder.group({});
  }

  ngOnInit(): void {
    // crea il form sulla base dei dati inviati dal cliente padre
    this.data.form.forEach(input => {
      // crea il control e lo aggiunge all'interno del form
      const control = this.formBuilder.control(input.value || null, input.validator);
      this.form.addControl(input.header, control);
    });
  }

  /**
   * Chiude la finestra di dialogo
   */
  close(bool: boolean): void {
    if (!bool) {
      // se il bool è false la finestra è stata chiusa
      this._dialogRef.close();
    } else {
      // se il bool è presente la finestra è stata chiusa tramite il bottone di invio
      if (this.form.valid)
        // se il form è validato vengono emessi i dati al componente padre
        this._dialogRef.close(this.form.value);
    }
  }
}
