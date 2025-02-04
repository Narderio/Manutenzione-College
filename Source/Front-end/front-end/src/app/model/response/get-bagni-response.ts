export class GetBagniResponse {
  id: number;
  nucleo: string;

  constructor(id: number, nucleo: string) {
    this.id = id;
    this.nucleo = nucleo;
  }
}
