export class FiltroManutenzione {
  descrizione: string;
  dataRichiesta: string;
  dataRiparazione: string;
  dataPrevista: string;
  priorita: number;
  manutentore: string;
  statoManutenzione: string;
  utenteRichiedente: string;
  nome: string;

  constructor(descrizione: string, dataRichiesta: string, dataRiparazione: string, dataPrevista: string, priorita: number, manutentore: string, statoManutenzione: string, utenteRichiedente: string, nome: string)
  constructor();


  constructor(descrizione?: string, dataRichiesta?: string, dataRiparazione?: string, dataPrevista?: string, priorita?: number, manutentore?: string, statoManutenzione?: string, utenteRichiedente?: string, nome?: string) {
    this.descrizione = descrizione ?? "";
    this.dataRichiesta = dataRichiesta ?? "";
    this.dataRiparazione = dataRiparazione ?? "";
    this.dataPrevista = dataPrevista ?? "";
    this.priorita = priorita ?? 0;
    this.manutentore = manutentore ?? "";
    this.statoManutenzione = statoManutenzione ?? "";
    this.utenteRichiedente = utenteRichiedente ?? "";
    this.nome = nome ?? "";
  }

}
