export class RichiediManutenzioneResponse {
  id: number;
  descrizione: string;
  luogo: string;
  nucleo: string;
  dataRichiesta: Date;
  nome: string;

  constructor(id: number, descrizione: string, luogo: string, nucleo: string, dataRichiesta: Date, nome: string) {
    this.id = id;
    this.descrizione = descrizione;
    this.luogo = luogo;
    this.nucleo = nucleo;
    this.dataRichiesta = dataRichiesta;
    this.nome = nome;
  }
}
