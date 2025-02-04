export class RiparaManutenzioneResponse {

  id: number;
  descrizione: string;
  luogo: string;
  nucleo: string;
  priorita: number;
  dataPrevista: Date;
  dataRiparazione: Date;

  constructor(id: number, descrizione: string, luogo: string, nucleo: string, priorita: number, dataPrevista: Date, dataRiparazione: Date) {
    this.id = id;
    this.descrizione = descrizione;
    this.luogo = luogo;
    this.nucleo = nucleo;
    this.priorita = priorita;
    this.dataPrevista = dataPrevista;
    this.dataRiparazione = dataRiparazione;
  }
}
