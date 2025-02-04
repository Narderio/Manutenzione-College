export class FeedbackManutenzioneRequest {
  idManutenzione: number;
  risolto: boolean;
  problema: string;

  constructor(idManutenzione: number, risolto: boolean, problema: string) {
    this.idManutenzione = idManutenzione;
    this.risolto = risolto;
    this.problema = problema;
  }
}
