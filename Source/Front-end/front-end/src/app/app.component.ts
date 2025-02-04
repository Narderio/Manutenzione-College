import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UtenteService} from "./services/utente/utente.service";
import {LoginRequest} from "./model/request/login-request";
import {AuthService} from "./services/auth/auth.service";
import {MatDialog} from "@angular/material/dialog";
import {FormDialogComponent} from "./dialogs/form-dialog/form-dialog.component";
import {FormDialogDto} from "./model/models/form-dialog-dto";
import {PasswordDimenticataRequest} from "./model/request/password-dimenticata-request";
import {SnackbarService} from "./services/snackbar/snackbar.service";
import {FormDialogInput} from "./model/models/form-dialog-input";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  loginForm: FormGroup; // form del login

  constructor(private router: Router, private dialog: MatDialog, private _authService: AuthService,
              private _utenteService: UtenteService, private _snackbarService: SnackbarService,
              private formBuilder: FormBuilder) {
    // creazione del login form
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  /**
   * Se il form è valido invia la richiesta di login
   */
  invioLogin(): void {
    if (this.loginForm.valid && this.loginForm.value.email && this.loginForm.value.password) {
      // se il form è valido, crea un LoginRequest e invia la richiesta al BE
      let loginRequest: LoginRequest = new LoginRequest(this.loginForm.value.email, this.loginForm.value.password);
      this._authService.login(loginRequest);
    }
  }

  /**
   * Resetta il loginForm e chiama il servizio per fare il logoud
   */
  logout(): void {
    this.loginForm.reset();
    this._authService.logout()
  }

  /**
   * Riceve il path dal componente figlio e cambia path
   */
  changePage(path: string) {
    if (path == 'logout')
      // se il path è logout chiama il logout()
      this.logout()
    else
      // qualsiasi altro path viene reindirizzata la pagina
      this.router.navigate([path]);
  }

  /**
   * Verifica se l'utente è loggato
   */
  isAuthenticated() {
    return this._authService.isLoggedIn();
  }

  /**
   * Apre una finestra di dialogo con form e invia la richiesta di dimenticaPassword
   */
  apriDimenticaPassword() {
    const formDialogData: FormDialogDto = new FormDialogDto("Richiedi una password sostitutiva:",
      "Richiedi", [
        new FormDialogInput('Text', 'Email', "email", [Validators.required, Validators.email]),
      ]);
    // apre una finestra di dialogo
    let dialogRef = this.dialog.open(FormDialogComponent, {
      width: "200",
      height: "600",
      data: formDialogData
    })

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      // se ha successo recupera crea un passwordDimenticataRequest e lo invia al BE
      let passwordDimenticataRequest = new PasswordDimenticataRequest(result.email);
      this._utenteService.passwordDimenticata(passwordDimenticataRequest).subscribe({
        next: result => {
          // se ha successo visualizza un messaggio di successo
          this._snackbarService.openSuccess("Controlla la tua casella di posta");
        },
        error: error => {
          // se non ha successo visualizza un messaggio di errore
          this._snackbarService.openError(error.error.message.errore);
        }
      })


    })
  }
}
