package org.elis.manutenzione.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.elis.manutenzione.model._enum.TipoMessaggioInviato;

/**
 * DTO che rappresenta la richiesta di invio di un messaggio.
 */
@Data
public class InviaMessaggioDTO {

    /**
     * Lo username del destinatario del messaggio.
     * Non può essere nullo o vuoto e deve contenere almeno 4 caratteri.
     */
    @NotBlank
    @Size(min = 4)
    private String usernameDestinatario;

    /**
     * Il testo del messaggio.
     * Non può essere nullo o vuoto.
     */
    @NotBlank
    private String testo;

    /**
     * L'ID del messaggio di riferimento.
     */
    private long idRiferimentoMessaggio;

    /**
     * Il tipo del messaggio. Immagine o testo
     */
    private TipoMessaggioInviato tipoMessaggio;
}