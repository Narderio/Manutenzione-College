export class GetMessaggioResponse {
  id: number;
  mittente: string;
  testo: string;

  constructor(id: number, mittente: string, testo: string) {
    this.id = id;
    this.mittente = mittente;
    this.testo = testo;
  }
}
