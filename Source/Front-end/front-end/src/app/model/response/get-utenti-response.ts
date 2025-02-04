import {Ruolo} from "../models/ruolo";

export class GetUtentiResponse {
  email: string;
  nome: string;
  cognome: string;
  ruolo: Ruolo;
  dataDiNascita: Date;

  constructor(email: string, nome: string, cognome: string, dataDiNascita: Date, ruolo: Ruolo) {
    this.email = email;
    this.nome = nome;
    this.cognome = cognome;
    this.ruolo = ruolo;
    this.dataDiNascita = dataDiNascita;

  }
}
