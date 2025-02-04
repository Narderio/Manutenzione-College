import {Utente} from "./utente";
import {Manutenzione} from "./manutenzione";


export class Manutentore extends Utente {
  manutenzioniEffettuate: Manutenzione[];
  tipoManutentore: string;
}
