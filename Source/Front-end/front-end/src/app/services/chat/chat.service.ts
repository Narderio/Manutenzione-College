import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {InviaMessaggioDTO} from "../../model/request/invia-messaggio-dto";
import {MessaggioDTO} from "../../model/response/messaggio-dto";
import {EliminaMessaggioDTO} from "../../model/request/elimina-messaggio-dto";
import {GetMessaggioResponse} from "../../model/response/get-messaggio-response";
import {ModificaMessaggioDTO} from "../../model/request/modifica-messaggio-dto";

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private http: HttpClient) {
  }

  /**
   * Invia la richiesta di invia messaggio al BE
   */
  inviaMessaggio(inviaMessaggioDTO: InviaMessaggioDTO) {
    return this.http.post(environment.baseUrl + environment.Messaggio.baseUrl + environment.Chat.invia, inviaMessaggioDTO);
  }

  /**
   * Invia la richiesta di ritrovamento chat al BE
   */
  getChat(username: string) {
    return this.http.get<MessaggioDTO[]>(environment.baseUrl + environment.Chat.baseUrl + "/" + username);
  }

  /**
   * Invia la richiesta di elimina messaggio al BE
   */
  eliminaMessaggio(eliminaMessaggioDTO: EliminaMessaggioDTO){
    return this.http.post<GetMessaggioResponse>(environment.baseUrl + environment.Chat.baseUrl + environment.Chat.eliminaMessaggio, eliminaMessaggioDTO );
  }

  /**
   * Invia la richiesta di modifica messaggio al BE
   */
  modificaMessaggio( modificaMessaggioDTO:ModificaMessaggioDTO){
    return this.http.patch<GetMessaggioResponse>(environment.baseUrl + environment.Chat.baseUrl + environment.Chat.modificaMessaggio, modificaMessaggioDTO);
  }
}
