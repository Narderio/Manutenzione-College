import {TipoMessaggio} from "./tipo-messaggio";


export class CustomMessage {
  content: string;
  sender: string;
  idMessage: number;
  type: TipoMessaggio;
  idRiferimentoMessaggio: number;
}
