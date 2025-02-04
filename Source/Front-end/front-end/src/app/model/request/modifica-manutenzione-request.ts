export class ModificaManutenzioneRequest {
  idManutenzione: number;
  descrizione: string;
  nome: string;

  constructor(idManutenzione: number, descrizione: string, nome: string) {
    this.idManutenzione = idManutenzione;
    this.descrizione = descrizione;
    this.nome = nome;
  }
}
