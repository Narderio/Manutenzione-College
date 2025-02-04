export class FiltraManutenzioneResponse {
  id: number;
  descrizione: string;
  luogo: string;
  nucleo: string;
  dataRichiesta: Date;
  richiedente: string;
  manutentore: string;
  priorita: number;

  constructor(id: number, descrizione: string, luogo: string, nucleo: string, dataRichiesta: Date, richiedente: string, manutentore: string, priorita: number) {
    this.id = id;
    this.descrizione = descrizione;
    this.luogo = luogo;
    this.nucleo = nucleo;
    this.dataRichiesta = dataRichiesta;
    this.richiedente = richiedente;
    this.manutentore = manutentore;
    this.priorita = priorita;
  }
}
