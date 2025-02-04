import {Tipo} from "./tipo";
import {Residente} from "./residente";


export class Luogo {
  id: number;
  nome: string;
  nucleo: string;
  tipo: Tipo;
  piano: number;
  capienza: number;
  residenti: Residente[];
}
