export class RichiediManutenzioneRequest {
  idLuogo: number;
  descrizione: string;
  nome: string;
  immagine: string;

  constructor(idLuogo: number, descrizione: string, nome: string, immagine: string) {
    this.idLuogo = idLuogo;
    this.descrizione = descrizione;
    this.nome = nome;
    this.immagine = immagine;
  }

}
