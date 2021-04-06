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

    @RequestMapping(value = "/something", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseDTO customerStatementEndpoint(@Valid @RequestBody RequestDTO request) throws Exception {
        System.out.println(request.transactionReference);
        System.out.println(request.accountNumber);
        System.out.println(request.startBalance);
        System.out.println(request.mutation);
        System.out.println(request.description);
        System.out.println(request.endBalance);

        return service.handleRequest(request);
    }
}