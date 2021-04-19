package nl.gb.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nl.gb.repository.Reference;
import nl.gb.repository.ReferenceRepository;
import nl.gb.web.dto.RequestDTO;
import nl.gb.web.dto.ResponseDTO;
import nl.gb.web.dto.Result;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public class StepDefinitions {
    @Autowired
    private ReferenceRepository repository;

    @Before("@resetDataBase")
    public void cleanupDataBase() {
        repository.deleteAll();
        repository.save(new Reference(1101L, "abnAmro"));
    }

    @Autowired
    private TestHttpClient httpClient;

    private RequestDTO request;
    private ResponseDTO response;
    private int httpStatus;

    @Given("^a customer statement with ([^/s]*) reference and ([^/s]*) end balance$")
    public void createRequest(String referenceType, String endBalance) throws Exception {
        Long transactionReference;
        switch(referenceType) {
            case "unique":
                transactionReference = 1L;
                break;
            case "duplicate":
                transactionReference = 1101L;
                break;
            default:
                throw new Exception("Error encountered, the value of reference in cucumber should either be 'unique' or 'duplicate'.");
        }
        BigDecimal transactionEndBalance;
        switch(endBalance) {
            case "correct":
                transactionEndBalance = new BigDecimal(100);
                break;
            case "incorrect":
                transactionEndBalance = new BigDecimal(110);
                break;
            default:
                throw new Exception("Error encountered, the value of end balance in cucumber should either be 'correct' or 'incorrect'.");
        }

        request = new RequestDTO(transactionReference,
                "some account",
                new BigDecimal(90),
                new BigDecimal(10),
                "some description",
                transactionEndBalance);
    }

    @When("^a post is done to /customer/statement$")
    public void doPost() {
        ResponseEntity<ResponseDTO> responseEntity = httpClient.post(request);
        response = responseEntity.getBody();
        httpStatus = responseEntity.getStatusCodeValue();
    }

    @Then("a status code of {int} is expected")
    public void matchStatusCode(int expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode, httpStatus);
    }

    @And("^result is ([^/s]*)$")
    public void matchResponseResult(String result) throws Exception {
        switch(result) {
            case "SUCCESSFUL":
                Assert.assertEquals(Result.Successful, response.result);
                break;
            case "DUPLICATE_REFERENCE":
                Assert.assertEquals(Result.DuplicateReference, response.result);
                break;
            case "INCORRECT_END_BALANCE":
                Assert.assertEquals(Result.IncorrectEndBalance, response.result);
                break;
            case "DUPLICATE_REFERENCE_INCORRECT_END_BALANCE":
                Assert.assertEquals(Result.DuplicateReferenceIncorrectEndBalance, response.result);
                break;
            default:
                throw new Exception("incorrect value given for result in the cucumber test.");
        }
    }
}
