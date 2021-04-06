package nl.gb.controllers;

import nl.gb.dto.RequestDTO;
import nl.gb.dto.ResponseDTO;
import nl.gb.service.CustomerStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CustomerStatementController {

    @Autowired
    CustomerStatementService service;

    @RequestMapping(value = "/customer/statement", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseDTO customerStatementEndpoint(@Valid @RequestBody RequestDTO request) throws Exception {
        return service.handleRequest(request);
    }
}