package org.elis.manutenzione.mapper;

import org.elis.manutenzione.dto.response.GetMessaggioResponse;
import org.elis.manutenzione.model.entity.Chat;
import org.elis.manutenzione.model.entity.Messaggio;
import org.elis.manutenzione.repository.ChatRepository;
import org.springframework.stereotype.Component;

/**
 * Mapper per la conversione tra entità `Messaggio` e DTO `GetMessaggioResponse`.
 * Questa classe fornisce la logica per trasformare un'entità `Messaggio` in un DTO
 * contenente le informazioni necessarie per la risposta.
 */
@Component
public class MessaggioMapper {

    private final ChatRepository chatRepository;

    /**
     * Costruttore del mapper.
     * @param chatRepository Repository per l'accesso ai dati delle chat.
     */
    public MessaggioMapper(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    /**
     * Converte un'entità `Messaggio` in un DTO `GetMessaggioResponse`.
     * @param m L'entità `Messaggio` da convertire.
     * @return Un'istanza di `GetMessaggioResponse` contenente i dati del messaggio.
     * @throws NullPointerException se `m` è null.
     */
    public GetMessaggioResponse toGetMessaggioDTO(Messaggio m){
        // Ottieni la chat associata al messaggio dal repository
        Chat c= chatRepository.findById(m.getChat().getId()).orElse(null);

        // Crea una nuova istanza di GetMessaggioResponse
        GetMessaggioResponse response = new GetMessaggioResponse();

        // Imposta l'ID del messaggio nella response
        response.setId(m.getId());

        // Imposta il testo del messaggio nella response
        response.setTesto(m.getTesto());

        // Determina il mittente del messaggio in base al flag 'primoUtente'
        if(m.isPrimoUtente() && c != null && c.getUtenteUno() != null) {
            response.setMittente(c.getUtenteUno().getEmail());
        }
        else if(!m.isPrimoUtente() && c != null && c.getUtenteDue() != null) {
            response.setMittente(c.getUtenteDue().getEmail());
        }

        // Restituisci il DTO con le informazioni del messaggio
        return response;
    }
}