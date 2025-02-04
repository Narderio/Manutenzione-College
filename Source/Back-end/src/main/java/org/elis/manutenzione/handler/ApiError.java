package org.elis.manutenzione.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe per la rappresentazione degli errori API.
 * Questa classe contiene informazioni sull'errore, come il codice di stato HTTP,
 * la data e l'ora dell'errore e un messaggio di errore.
 * Utilizzata per uniformare le risposte di errore dell'API.
 */
@Data
public class ApiError {

    /**
     * Il codice di stato HTTP dell'errore.
     */
    private HttpStatus status;

    /**
     * La data e l'ora in cui si è verificato l'errore.
     * Formattato come "dd-MM-yyyy hh:mm:ss".
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    /**
     * Mappa contenente i messaggi di errore, con il nome del campo come chiave e il messaggio come valore.
     */
    Map<String, String> message = new HashMap<>();

    /**
     * Costruttore privato per inizializzare la data e l'ora dell'errore.
     */
    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    /**
     * Costruttore per inizializzare l'errore con un codice di stato HTTP.
     *
     * @param status Il codice di stato HTTP dell'errore.
     */
    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    /**
     * Imposta il messaggio di errore per una {@link MethodArgumentNotValidException}.
     *
     * @param e L'eccezione {@link MethodArgumentNotValidException} che contiene i dettagli dell'errore.
     */
    public void setMethodArgumentNotValidExceptionMessage(MethodArgumentNotValidException e) {
        e.getBindingResult().getFieldErrors().forEach(fe -> {
            message.put(fe.getField(), fe.getDefaultMessage());
        });
    }

    /**
     * Imposta il messaggio di errore per una {@link BadRequestException}.
     *
     * @param e L'eccezione {@link BadRequestException} che contiene il messaggio dell'errore.
     */
    public void setBadRequestExceptionMessage(BadRequestException e) {
        message.put("errore", e.getMessage());
    }

    /**
     * Imposta il messaggio di errore per una {@link BadGatewayException}.
     *
     * @param e L'eccezione {@link BadGatewayException} che contiene il messaggio dell'errore.
     */
    public void setBadGatewayExceptionMessage(BadGatewayException e) {
        message.put("errore",e.getMessage());
    }



    /**
     * Imposta il messaggio di errore per una {@link ForbiddenException}.
     *
     * @param e L'eccezione {@link ForbiddenException} che contiene il messaggio dell'errore.
     */
    public void setForbiddenExceptionMessage(ForbiddenException e) {
        message.put("errore", e.getMessage());
    }

    /**
     * Imposta il messaggio di errore per una {@link NoContentException}.
     *
     * @param e L'eccezione {@link NoContentException} che contiene il messaggio dell'errore.
     */
    public void setNoContentExceptionMessage(NoContentException e) {
        message.put("errore", e.getMessage());
    }
}