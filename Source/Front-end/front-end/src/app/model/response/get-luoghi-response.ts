import {Tipo} from "../models/tipo";

export class GetLuoghiResponse {
  id: number;
  nome: string;
  nucleo: string;
  tipo: Tipo;
  piano: number;

  constructor(id: number, nome: string, nucleo: string, tipo: Tipo, piano: number) {
    this.id = id;
    this.nome = nome;
    this.nucleo = nucleo;
    this.tipo = tipo;
    this.piano = piano;

  }
}
