export class AccettaManutenzioneResponse {
  id: number;
  descrizione: string;
  luogo: string;
  nucleo: string;
  priorita: number;
  dataPrevista: Date;
  nome: string;

  constructor(id: number, descrizione: string, luogo: string, nucleo: string, priorita: number, dataPrevista: Date, nome: string) {
    this.id = id;
    this.descrizione = descrizione;
    this.luogo = luogo;
    this.nucleo = nucleo;
    this.priorita = priorita;
    this.dataPrevista = dataPrevista;
    this.nome = nome;
  }
}
