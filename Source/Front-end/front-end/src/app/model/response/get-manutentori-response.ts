export class GetManutentoriResponse {
  email: string;
  nome: string;
  cognome: string;
  dataDiNascita: Date;
  manutenzioniEffettuate: number;
  tipoManutentore: string;

  constructor(email: string, nome: string, cognome: string, dataDiNascita: Date, manutenzioniEffettuate: number, tipoManutentore: string) {
    this.email = email;
    this.nome = nome;
    this.cognome = cognome;
    this.dataDiNascita = dataDiNascita;
    this.manutenzioniEffettuate = manutenzioniEffettuate;
    this.tipoManutentore = tipoManutentore;
  }
}
