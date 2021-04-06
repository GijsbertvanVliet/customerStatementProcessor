package nl.gb.error;

import nl.gb.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@ControllerAdvice
public class ExceptionHanlder {

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    protected ResponseEntity<ResponseDTO> handleMethodArgumentNotValid(Exception ex, WebRequest request) {
        ResponseDTO errorResponse = ResponseDTO.createBadRequestResponse();
        return new ResponseEntity<ResponseDTO>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> couldNotParseJson(Exception ex, WebRequest request) throws IOException {
        ResponseDTO internalServerErrorResponse = ResponseDTO.createInternalServerErrorResponse();
        return new ResponseEntity<ResponseDTO>(internalServerErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
