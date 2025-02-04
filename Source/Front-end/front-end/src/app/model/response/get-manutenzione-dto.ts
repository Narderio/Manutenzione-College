export class GetManutenzioneDTO {

  idManutenzione: number;
  nome: string;
  descrizione: string;
  utenteRichiedente: string;
  luogo: string;
  stato: string;
  manutentore: string;
  dataRichiesta: Date;
  dataRiparazione: Date;
  dataPrevista: Date;
  priorita: number;
  immagine: string;
}
