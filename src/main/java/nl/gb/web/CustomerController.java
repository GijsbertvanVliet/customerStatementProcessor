package nl.gb.web;

import nl.gb.service.CustomerStatementService;
import nl.gb.web.dto.RequestDTO;
import nl.gb.web.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static nl.gb.web.dto.DataClassConverter.handleTransactionResponseToDataTransferobject;
import static nl.gb.web.dto.DataClassConverter.requestToCustomerStatementRecord;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerStatementService service;

    public CustomerController(CustomerStatementService service){
        this.service = service;
    }

    @PostMapping(value = "/statement", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO> customerStatementEndpoint(@Valid @RequestBody RequestDTO request) {
        logger.debug("Customer statement request received: " + request);
        ResponseEntity<ResponseDTO> responseEntity = ResponseEntity.ok(
                handleTransactionResponseToDataTransferobject(
                        service.handleRequest(
                                requestToCustomerStatementRecord(request)
                        )
                )
        );
        logger.debug("Sending response: " + responseEntity.getBody());
        return responseEntity;
    }
}