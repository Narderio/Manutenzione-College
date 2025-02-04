package org.elis.manutenzione.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Gestore globale delle eccezioni per l'applicazione.
 * Questa classe intercetta le eccezioni lanciate dall'applicazione e restituisce risposte HTTP appropriate.
 * Le eccezioni sono gestite in base al loro tipo e viene restituito un {@link ApiError} con il codice di stato HTTP corretto.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * Costruisce un {@link ResponseEntity} con l'{@link ApiError} e il codice di stato HTTP.
     *
     * @param apiError L'oggetto {@link ApiError} contenente le informazioni sull'errore.
     * @return Un {@link ResponseEntity} contenente l'oggetto {@link ApiError} e il codice di stato HTTP.
     */
    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    /**
     * Gestisce le eccezioni di tipo {@link MethodArgumentNotValidException}.
     *
     * @param ex L'eccezione lanciata.
     * @return Un {@link ResponseEntity} contenente l'oggetto {@link ApiError} con codice di stato HTTP 400 BAD_REQUEST.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidExceptionException(MethodArgumentNotValidException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMethodArgumentNotValidExceptionMessage(ex);
        return buildResponseEntity(apiError);
    }

    /**
     * Gestisce le eccezioni di tipo {@link BadRequestException}.
     *
     * @param ex L'eccezione lanciata.
     * @return Un {@link ResponseEntity} contenente l'oggetto {@link ApiError} con codice di stato HTTP 400 BAD_REQUEST.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestExceptionException(BadRequestException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setBadRequestExceptionMessage(ex);
        return buildResponseEntity(apiError);
    }

    /**
     * Gestisce le eccezioni di tipo {@link BadGatewayException}.
     *
     * @param ex L'eccezione lanciata.
     * @return Un {@link ResponseEntity} contenente l'oggetto {@link ApiError} con codice di stato HTTP 502 BAD_GATEWAY.
     */
    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<ApiError> handleBadGatewayExceptionException(BadGatewayException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_GATEWAY);
        apiError.setBadGatewayExceptionMessage(ex);
        return buildResponseEntity(apiError);
    }

    /**
     * Gestisce le eccezioni di tipo {@link BadGatewayException}.
     *
     * @param ex L'eccezione lanciata.
     * @return Un {@link ResponseEntity} contenente l'oggetto {@link ApiError} con codice di stato HTTP 502 BAD_GATEWAY.
     */
    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ApiError> handleBadGatewayExceptionException(NoContentException ex) {
        ApiError apiError = new ApiError(HttpStatus.NO_CONTENT);
        apiError.setNoContentExceptionMessage(ex);
        return buildResponseEntity(apiError);
    }


    /**
     * Gestisce le eccezioni di tipo {@link ForbiddenException}.
     *
     * @param ex L'eccezione lanciata.
     * @return Un {@link ResponseEntity} contenente l'oggetto {@link ApiError} con codice di stato HTTP 403 FORBIDDEN.
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> handleForbiddenExceptionException(ForbiddenException ex) {
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
        apiError.setForbiddenExceptionMessage(ex);
        return buildResponseEntity(apiError);
    }
}