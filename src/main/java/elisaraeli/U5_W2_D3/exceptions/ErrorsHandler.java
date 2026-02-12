package elisaraeli.U5_W2_D3.exceptions;

import elisaraeli.U5_W2_D3.payloads.ErrorsDTO;
import elisaraeli.U5_W2_D3.payloads.ErrorsWithListDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
// Questa classe gestisce gli errori
public class ErrorsHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorsWithListDTO handleValidationException(ValidationException ex) {

        return new ErrorsWithListDTO(ex.getMessage(), LocalDateTime.now(), ex.getErrorsMessages());
    }


    @ExceptionHandler(BadRequestException.class)
    // Tra le parentesi specifico il tipo di eccezione gestita da questo metodo
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorsDTO handleBadRequest(BadRequestException ex) {
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorsDTO handleNotFound(NotFoundException ex) {
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ErrorsDTO handleGenericServerError(Exception ex) {
        ex.printStackTrace();
        return new ErrorsDTO("C'è stato un errore, giuro che lo risolveremo presto!", LocalDateTime.now());

    }

}
