import {Tipo} from "../models/tipo";

export class GetStanzeResponse {
  id: number;
  nome: string;
  nucleo: string;
  piano: number;
  capienza: number;
  residenti: number;

  constructor(id: number, nome: string, nucleo: string, piano: number, capienza: number, residenti: number) {
    this.id = id;
    this.nome = nome;
    this.nucleo = nucleo;
    this.piano = piano;
    this.capienza = capienza;
    this.residenti = residenti;
  }
}
