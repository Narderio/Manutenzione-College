export class EmailDTO {
  destinatario: string[];
  oggetto: string;
  testo: string;

  constructor(destinatario: string[], oggetto: string, testo: string) {
    this.destinatario = destinatario;
    this.oggetto = oggetto;
    this.testo = testo;
  }
}
