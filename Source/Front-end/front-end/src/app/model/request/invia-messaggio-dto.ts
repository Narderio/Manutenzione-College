import {TipoMessaggioInviato} from "../models/tipo-messaggio-inviato";
import {TipoMessaggio} from "../models/tipo-messaggio";

export class InviaMessaggioDTO {
  usernameDestinatario: string;
  testo: string;
  idRiferimentoMessaggio: number;
  tipoMessaggio: TipoMessaggioInviato

  constructor(usernameDestinatario: string, testo: string, idRiferimentoMessaggio: number, tipoMessaggioInviato: TipoMessaggioInviato) {
    this.usernameDestinatario = usernameDestinatario;
    this.testo = testo;
    this.idRiferimentoMessaggio = idRiferimentoMessaggio;
    this.tipoMessaggio = tipoMessaggioInviato;
  }
}
