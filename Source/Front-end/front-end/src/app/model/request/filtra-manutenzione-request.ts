export class FiltraManutenzioneRequest {
  idManutenzione: number;
  emailManutentore: string;
  priorita: number;
  nomeManutenzione: string;

  constructor(idManutenzione: number, emailManutenzione: string, priority: number, nomeManutenzione: string) {
    this.idManutenzione = idManutenzione;
    this.emailManutentore = emailManutenzione;
    this.priorita = priority;
    this.nomeManutenzione = nomeManutenzione;
  }
}
