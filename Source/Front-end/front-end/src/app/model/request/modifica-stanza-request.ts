export class ModificaStanzaRequest {
  idStanza: number;
  capienza: number;

  constructor(idStanza: number, capienza: number) {
    this.idStanza = idStanza;
    this.capienza = capienza;
  }
}
