import {Utente} from "../models/utente";
import {Luogo} from "../models/luogo";

export class ManutenzioneDTO {
  id: number;
  descrizione: string;
  dataRichiesta: Date;
  dataRiparazione: Date;
  dataPrevista: Date;
  priorita: number;
  utenteRichiedente: string;
  manutentore: Utente;
  statoManutenzione: string;
  nome: string;

  constructor(id: number, descrizione: string, dataRichiesta: Date,dataRiparazione: Date, dataPrevista: Date, priorita: number, utenteRichiedente: string, manutentore: Utente,statoManutenzione: string, nome:string) {
    this.id = id;
    this.descrizione = descrizione;
    this.dataRichiesta = dataRichiesta;
    this.dataRiparazione = dataRiparazione;
    this.dataPrevista = dataPrevista;
    this.priorita = priorita;
    this.utenteRichiedente = utenteRichiedente;
    this.manutentore = manutentore;
    this.statoManutenzione = statoManutenzione;
    this.nome = nome;
  }
}
