export class AccettaManutenzioneRequest {
  idManutenzione: number;
  dataPrevista: Date;

  constructor(idManutenzione: number, dataPrevista: Date) {
    this.idManutenzione = idManutenzione;
    this.dataPrevista = dataPrevista;
  }
}
