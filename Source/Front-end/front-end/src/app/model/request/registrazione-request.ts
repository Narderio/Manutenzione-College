import {Ruolo} from "../models/ruolo";

export class RegistrazioneRequest {
  nome: string;
  cognome: string;
  email: string;
  dataDiNascita: Date;
  ruolo: Ruolo;
  bloccato: boolean;
  tipoManutentore: string;

  constructor(nome: string, cognome: string, email: string, dataDiNascita: Date, ruolo: Ruolo, bloccato: boolean, tipoManutentore: string) {
    this.nome = nome;
    this.cognome = cognome;
    this.email = email;
    this.dataDiNascita = dataDiNascita;
    this.ruolo = ruolo;
    this.bloccato = bloccato;
    this.tipoManutentore = tipoManutentore;
  }
}
