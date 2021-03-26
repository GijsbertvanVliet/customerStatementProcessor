package nl.gb.controllers;

import nl.gb.dto.RequestDTO;
import nl.gb.dto.ResponseDTO;
import nl.gb.error.JsonParsingErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerStatementController {
    @RequestMapping(value = "/something", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseDTO greetingText(@RequestBody RequestDTO request) {
        String name = request.getName();
        if (name == null) {
            throw new JsonParsingErrorException("name not given");
        } else {
            ResponseDTO response = new ResponseDTO();
            response.setName(name);
            return response;
        }
    }
}