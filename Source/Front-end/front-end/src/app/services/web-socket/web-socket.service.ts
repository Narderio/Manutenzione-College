import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private client: Client; // valore della websocket da raggiungere
  private connected: boolean = false; // booleano che identifica la connessione alla websocket

  constructor() {
    // creazione del client
    this.client = new Client({
      brokerURL: 'ws://localhost:15674/ws',
      reconnectDelay: 5000,
    });

    // inizializzazione del processo di connessione
    this.client.onConnect = () => {
      console.log('Connesso a RabbitMQ');
      this.connected = true;
    };

    // inizializzazione del processo di disconnessione
    this.client.onDisconnect = () => {
      console.log('Disconnesso da RabbitMQ');
      this.connected = false;
    };

    // inizializzazione del processo di errore
    this.client.onStompError = (frame) => {
      console.error('Errore STOMP:', frame);
    };
  }

  /**
   * Connessione a rabbitMQ
   */
  connect(): void {
    this.client.activate();
  }

  /**
   * Disconnessione da rabbitMQ
   */
  disconnect(): void {
    this.client.deactivate();
  }

  /**
   * Iscrizione ad uno specifico topic basandosi sull'email
   */
  subscribeToUserTopic(email: string, callback: (message: IMessage) => void): void {
    if (!this.connected) {
      // se è già connesso stampa in console un messaggio di errore
      console.error('WebSocket non connesso.');
      return;
    }
    // se non è connesso si connette al topic
    const topic = `/exchange/TopicTestExchange/${email}`; // Topic basato sull'email
    this.client.subscribe(topic, (message) => callback(message));
  }

}
