import {Ruolo} from "../models/ruolo";


export class AggiornaRuoloRequest {
  email: string;
  ruoloNuovo: Ruolo;

  constructor(email: string, ruoloNuovo: Ruolo) {
    this.email = email;
    this.ruoloNuovo = ruoloNuovo;
  }
}
