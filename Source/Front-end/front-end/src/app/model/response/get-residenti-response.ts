export class GetResidentiResponse {
  email: string;
  nome: string;
  cognome: string;
  stanza: string;
  dataDiNascita: Date;

  constructor(email: string, nome: string, cognome: string, stanza: string, dataDiNascita: Date) {
    this.email = email;
    this.nome = nome;
    this.cognome = cognome;
    this.stanza = stanza;
    this.dataDiNascita = dataDiNascita;
  }
}
