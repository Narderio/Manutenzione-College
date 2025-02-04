import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {StatoManutenzioniComponent} from "./components/stato-manutenzioni/stato-manutenzioni.component";
import {SegnalaManutenzioneComponent} from "./components/segnala-manutenzione/segnala-manutenzione.component";
import {GestioneManutenzioniComponent} from "./components/gestione-manutenzioni/gestione-manutenzioni.component";
import {GestioneLuoghiComponent} from "./components/gestione-luoghi/gestione-luoghi.component";
import {GestioneUtentiComponent} from "./components/gestione-utenti/gestione-utenti.component";
import {ProfiloComponent} from "./components/profilo/profilo.component";
import {ChatComponent} from "./components/chat/chat.component";
import {AuthGuard} from "./auth-guard/auth.guard";
import {Ruolo} from "./model/models/ruolo";
import {DettaglioManutenzioneComponent} from "./components/dettaglio-manutenzione/dettaglio-manutenzione.component";

const routes: Routes = [
  {
    path: 'segnala-manutenzione',
    component: SegnalaManutenzioneComponent,
    canActivate: [AuthGuard],
    data: {
      label: 'Segnala Manutenzione',
      ruoli: [Ruolo.Residente]
    }
  },
  {
    path: 'gestione-manutenzioni',
    component: GestioneManutenzioniComponent,
    canActivate: [AuthGuard],
    data: {
      ruoli: [Ruolo.Supervisore, Ruolo.Admin],
      label: 'Gestione Manutenzioni'
    }
  },
  {
    path: 'gestione-luoghi',
    component: GestioneLuoghiComponent,
    canActivate: [AuthGuard],
    data: {
      ruoli: [Ruolo.Admin],
      label: 'Gestione Luoghi'
    }
  },
  {
    path: 'gestione-utenti',
    component: GestioneUtentiComponent,
    canActivate: [AuthGuard],
    data: {
      label: 'Gestione Utenti',
      ruoli: [Ruolo.Admin]
    }
  },
  {
    path: 'stato-manutenzioni',
    component: StatoManutenzioniComponent,
    canActivate: [AuthGuard],
    data: {
      label: 'Stato Manutenzioni',
      ruoli: [Ruolo.Manutentore, Ruolo.Residente]
    }
  },
  {
    path: 'chat',
    component: ChatComponent,
    canActivate: [AuthGuard],
    data: {
      ruoli: [Ruolo.Residente, Ruolo.Manutentore, Ruolo.Supervisore],
      label: 'Chat',
    }
  },
  {
    path: 'dettaglio-manutenzione/:id',
    component: DettaglioManutenzioneComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'profilo',
    component: ProfiloComponent,
    canActivate: [AuthGuard],
    data: {
      label: 'Profilo'
    }
  },

  {
    path: '**',
    redirectTo: 'profilo',
    pathMatch: 'full'
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
