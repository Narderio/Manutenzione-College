export class AggiungiLuogoRequest {
  nome: string;
  nucleo: string;
  tipo: string;
  piano: number;
  capienza: number;

  constructor(nome: string, nucleo: string, tipo: string, piano: number, capienza: number) {
    this.nome = nome;
    this.nucleo = nucleo;
    this.tipo = tipo;
    this.piano = piano;
    this.capienza = capienza;
  }

}
