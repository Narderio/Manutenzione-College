import {Chat} from "./chat";


export class Messaggio {
  id: number;
  testo: string;
  dataOra: Date;
  primoUtente: boolean;
  chat: Chat;
  idRiferimentoMessaggio: number;
}
