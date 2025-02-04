export class AggiungiLuogoResponse{
  id: number;
  nome: string;
  tipo: string;
  nucleo: string;
  capienza: number;

  constructor(id: number, nome: string, tipo: string, nucleo: string, capienza: number) {
    this.id = id;
    this.nome = nome;
    this.tipo = tipo;
    this.nucleo = nucleo;
    this.capienza = capienza;
  }
}
