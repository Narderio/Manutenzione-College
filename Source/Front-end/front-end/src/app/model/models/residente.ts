import {Luogo} from "./luogo";
import {Utente} from "./utente";

export class Residente extends Utente {
  luoghi: Luogo[];
}
