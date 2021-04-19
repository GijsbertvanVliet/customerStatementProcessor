package nl.gb.web;

import static nl.gb.web.dto.DataClassConverter.handleTransactionResponseToDataTransferobject;
import static nl.gb.web.dto.DataClassConverter.requestToCustomerStatementRecord;
import nl.gb.web.dto.RequestDTO;
import nl.gb.web.dto.ResponseDTO;
import nl.gb.service.CustomerStatementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerStatementService service;

    public CustomerController(CustomerStatementService service){
        this.service = service;
    }

    @PostMapping(value = "/statement", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO> customerStatementEndpoint(@Valid @RequestBody RequestDTO request) {
        return ResponseEntity.ok(handleTransactionResponseToDataTransferobject(service.handleRequest(requestToCustomerStatementRecord(request))));
    }
}