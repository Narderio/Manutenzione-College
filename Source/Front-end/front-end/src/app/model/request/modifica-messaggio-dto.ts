export class ModificaMessaggioDTO {
  id: number;
  testo: string;

  constructor(id: number, testo: string) {
    this.id = id;
    this.testo = testo;
  }
}
