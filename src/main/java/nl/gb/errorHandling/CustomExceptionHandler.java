package nl.gb.errorHandling;

import nl.gb.web.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@ControllerAdvice
public class CustomExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    protected ResponseEntity<ResponseDTO> handleMethodArgumentNotValid(Exception ex, WebRequest request) {
        logger.error("Invalid json", ex);
        ResponseDTO errorResponse = ResponseDTO.createBadRequestResponse();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> catchAll(Exception ex, WebRequest request) throws IOException {
        logger.error("Unknown error", ex);
        ResponseDTO internalServerErrorResponse = ResponseDTO.createInternalServerErrorResponse();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(internalServerErrorResponse);
    }
}
