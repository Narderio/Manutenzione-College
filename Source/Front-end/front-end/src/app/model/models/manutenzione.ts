
import {Manutentore} from "./manutentore";
import {Luogo} from "./luogo";
import {Residente} from "./residente";

export class Manutenzione {
  id: number;
  utenteRichiedente: Residente;
  manutentore: Manutentore;
  descrizione: string;
  nome: string;
  dataRichiesta: Date;
  dataRiparazione: Date;
  dataPrevista: Date;
  priorita: number;
  statoManutenzione: string;
  luogo: Luogo;
}
