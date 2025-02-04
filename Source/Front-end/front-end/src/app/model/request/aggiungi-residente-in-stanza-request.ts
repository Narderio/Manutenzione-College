export class AggiungiResidenteInStanzaRequest {
  email: string;
  stanza: string;

  constructor(email: string, stanza: string) {
    this.email = email;
    this.stanza = stanza;
  }
}
