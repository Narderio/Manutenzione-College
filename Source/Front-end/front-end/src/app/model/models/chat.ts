import {Utente} from "./utente";
import {Messaggio} from "./messaggio";

export class Chat {
  id: number;
  utenteUno: Utente;
  utenteDue: Utente;
  messaggi: Messaggio[];
}
