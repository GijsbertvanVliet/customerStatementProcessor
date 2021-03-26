package nl.gb.controllers;

import nl.gb.error.CustomErrorResponse;
import nl.gb.error.JsonParsingErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class JsonParsingExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(JsonParsingErrorException.class)
    public ResponseEntity<CustomErrorResponse> couldNotParseJson(Exception ex, WebRequest request) throws IOException {
        CustomErrorResponse errorResponse = new CustomErrorResponse(ex.getMessage());
        return new ResponseEntity<CustomErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
