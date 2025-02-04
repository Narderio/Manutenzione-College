import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {NavigazioneComponent} from './components/navigazione/navigazione.component';
import {SegnalaManutenzioneComponent} from './components/segnala-manutenzione/segnala-manutenzione.component';
import {StatoManutenzioniComponent} from './components/stato-manutenzioni/stato-manutenzioni.component';
import {GestioneManutenzioniComponent} from './components/gestione-manutenzioni/gestione-manutenzioni.component';
import {GestioneLuoghiComponent} from './components/gestione-luoghi/gestione-luoghi.component';
import {GestioneUtentiComponent} from './components/gestione-utenti/gestione-utenti.component';
import {ProfiloComponent} from './components/profilo/profilo.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from "@angular/material/button";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatSelectModule} from "@angular/material/select";
import {MatTableModule} from "@angular/material/table";
import {MatTabsModule} from "@angular/material/tabs";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatInputModule} from "@angular/material/input";
import {MatDialogModule} from "@angular/material/dialog";
import {MatCardModule} from "@angular/material/card";
import {ReactiveFormsModule} from "@angular/forms";
import {MatMenuModule} from "@angular/material/menu";
import {ManutentoriComponent} from './components/gestione-utenti/manutentori/manutentori.component';
import {ConfirmDialogComponent} from './dialogs/confirm-dialog/confirm-dialog.component';
import {FormDialogComponent} from './dialogs/form-dialog/form-dialog.component';
import {MatCheckboxModule} from "@angular/material/checkbox";
import {TabellaStanzeComponent} from './components/gestione-luoghi/tabella-stanze/tabella-stanze.component';
import {TabellaBagniComponent} from './components/gestione-luoghi/tabella-bagni/tabella-bagni.component';
import {
  TabellaLuoghiComuniComponent
} from './components/gestione-luoghi/tabella-luoghi-comuni/tabella-luoghi-comuni.component';
import {AdminComponent} from './components/gestione-utenti/admin/admin.component';
import {SupervisoriComponent} from './components/gestione-utenti/supervisori/supervisori.component';
import {ResidentiComponent} from './components/gestione-utenti/residenti/residenti.component';
import {ChatComponent} from './components/chat/chat.component';
import {AuthInterceptor} from "./interceptor/auth.interceptor";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import { ImageDialogComponent } from './dialogs/image-dialog/image-dialog.component';
import { DettaglioManutenzioneComponent } from './components/dettaglio-manutenzione/dettaglio-manutenzione.component';


@NgModule({
  declarations: [
    AppComponent,
    NavigazioneComponent,
    SegnalaManutenzioneComponent,
    StatoManutenzioniComponent,
    GestioneManutenzioniComponent,
    GestioneLuoghiComponent,
    GestioneUtentiComponent,
    ProfiloComponent,
    ManutentoriComponent,
    ConfirmDialogComponent,
    FormDialogComponent,
    TabellaStanzeComponent,
    TabellaBagniComponent,
    TabellaLuoghiComuniComponent,
    AdminComponent,
    SupervisoriComponent,
    ResidentiComponent,
    ChatComponent,
    ImageDialogComponent,
    DettaglioManutenzioneComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    NoopAnimationsModule,
    HttpClientModule,
    MatFormFieldModule,
    MatIconModule,
    MatButtonModule,
    MatDatepickerModule,
    MatSelectModule,
    MatTableModule,
    MatTabsModule,
    MatTooltipModule,
    MatPaginatorModule,
    MatInputModule,
    MatDialogModule,
    MatCardModule,
    ReactiveFormsModule,
    MatMenuModule,
    MatCheckboxModule,
    MatSnackBarModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
