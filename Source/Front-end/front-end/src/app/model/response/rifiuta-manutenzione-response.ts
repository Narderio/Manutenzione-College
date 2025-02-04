export class RifiutaManutenzioneResponse {

  id: number;
  descrizione: string;
  luogo: string;
  nucleo: string;
  priorita: number;

  constructor(id: number, descrizione: string, luogo: string, nucleo: string, priorita: number) {
    this.id = id;
    this.descrizione = descrizione;
    this.luogo = luogo;
    this.nucleo = nucleo;
    this.priorita = priorita;
  }

}
