import {TipoMessaggioInviato} from "../models/tipo-messaggio-inviato";
import {TipoMessaggio} from "../models/tipo-messaggio";

export class MessaggioDTO {
  id: number;
  mittente: string;
  nomeMittente: string;
  cognomeMittente: string;
  testo: string;
  data: Date;
  idRiferimentoMessaggio: number;
  tipoMessaggio: TipoMessaggioInviato

  constructor(mittente: string, testo: string, data: Date, tipoMessaggio: TipoMessaggioInviato, id?: number, idRiferimentoMessaggio?: number) {
    this.id = id ? id : 0;
    this.mittente = mittente;
    this.nomeMittente = "";
    this.cognomeMittente = "";
    this.testo = testo;
    this.data = data;
    this.idRiferimentoMessaggio = idRiferimentoMessaggio ? idRiferimentoMessaggio : 0;
    this.tipoMessaggio = tipoMessaggio
  }
}
