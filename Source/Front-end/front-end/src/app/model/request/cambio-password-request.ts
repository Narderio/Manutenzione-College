export class CambioPasswordRequest {
  ultimaPassword: string;
  nuovaPassword: string;
  confermaPassword: string;

  constructor(ultimaPassword: string, nuovaPassword: string, confermaPassword: string) {
    this.ultimaPassword = ultimaPassword;
    this.nuovaPassword = nuovaPassword;
    this.confermaPassword = confermaPassword;

  }
}
