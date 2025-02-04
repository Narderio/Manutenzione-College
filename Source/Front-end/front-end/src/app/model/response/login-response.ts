import {Ruolo} from "../models/ruolo";

export class LoginResponse {
  email: string;
  nome: string;
  cognome: string;
  dataDiNascita: Date;
  ruolo: Ruolo;
  luoghi: string[];
  emailBloccate: boolean;
  token: string;

  constructor(email: string, nome: string, cognome: string, dataDiNascita: Date, ruolo: Ruolo, luoghi: string[], emailBloccate: boolean, token: string) {
    this.email = email;
    this.nome = nome;
    this.cognome = cognome;
    this.dataDiNascita = dataDiNascita;
    this.ruolo = ruolo;
    this.luoghi = luoghi;
    this.emailBloccate = emailBloccate;
    this.token = token;
  }
}
