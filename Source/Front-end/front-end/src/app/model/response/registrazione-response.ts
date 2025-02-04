export class RegistrazioneResponse {
  email: string;
  nome: string;
  cognome: string;
  dataDiNascita: Date;

  constructor(email: string, nome: string, cognome: string, dataDiNascita: Date) {
    this.email = email;
    this.nome = nome;
    this.cognome = cognome;
    this.dataDiNascita = dataDiNascita;
  }
}
