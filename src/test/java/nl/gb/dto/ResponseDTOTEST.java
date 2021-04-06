package nl.gb.dto;

import nl.gb.error.ErrorRecord;
import nl.gb.error.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ResponseDTOTEST {
    @Test
    public void createSuccessfulResponse() {
        ResponseDTO successResponse = ResponseDTO.createSuccessfulResponse();
        Assert.assertEquals(successResponse.result, Result.Successful);
        Assert.assertTrue(successResponse.errorRecords.isEmpty());
    }

    @Test
    public void createDuplicateReferenceResponse() {
        Long transactionReference = 1L;
        String accountNumber = "NL06INGB12";
        ResponseDTO duplicateReferenceResponse = ResponseDTO.createDuplicateReferenceResponse(transactionReference, accountNumber);
        Assert.assertEquals(duplicateReferenceResponse.result, Result.DuplicateReference);
        ErrorRecord expectedErrorRecord = duplicateReferenceResponse.errorRecords.get(0);
        Assert.assertEquals(expectedErrorRecord.reference, transactionReference);
        Assert.assertEquals(expectedErrorRecord.accountNumber, accountNumber);
    }

    @Test
    public void createIncorrectEndBalanceResponse() {
        Long transactionReference = 1L;
        String accountNumber = "NL06INGB12";
        ResponseDTO duplicateReferenceResponse = ResponseDTO.createIncorrectEndBalanceResponse(transactionReference, accountNumber);
        Assert.assertEquals(duplicateReferenceResponse.result, Result.IncorrectEndBalance);
        ErrorRecord expectedErrorRecord = duplicateReferenceResponse.errorRecords.get(0);
        Assert.assertEquals(expectedErrorRecord.reference, transactionReference);
        Assert.assertEquals(expectedErrorRecord.accountNumber, accountNumber);
    }

    @Test
    public void createDuplicateReferenceAndIncorrectEndBalanceResponse() {
        Long transactionReference = 1L;
        String accountNumber = "NL06INGB12";
        String duplicateAccountNumber = "NL08INGB34";
        ResponseDTO duplicateReferenceResponse = ResponseDTO.createDuplicateReferenceAndIncorrectEndBalanceResponse(transactionReference,
                accountNumber,
                duplicateAccountNumber);
        Assert.assertEquals(duplicateReferenceResponse.result, Result.DuplicateReferenceIncorrectEndBalance);
        ErrorRecord firstExpectedErrorRecord = duplicateReferenceResponse.errorRecords.get(0);
        Assert.assertEquals(firstExpectedErrorRecord.reference, transactionReference);
        Assert.assertEquals(firstExpectedErrorRecord.accountNumber, duplicateAccountNumber);
        ErrorRecord secondExpectedErrorRecord = duplicateReferenceResponse.errorRecords.get(1);
        Assert.assertEquals(secondExpectedErrorRecord.reference, transactionReference);
        Assert.assertEquals(secondExpectedErrorRecord.accountNumber, accountNumber);
    }

    @Test
    public void createBadRequestResponse() {
        ResponseDTO badRequestResponse = ResponseDTO.createBadRequestResponse();
        Assert.assertEquals(badRequestResponse.result, Result.BadRequest);
        Assert.assertTrue(badRequestResponse.errorRecords.isEmpty());
    }

    @Test
    public void createInternalServerErrorResponse() {
        ResponseDTO badRequestResponse = ResponseDTO.createInternalServerErrorResponse();
        Assert.assertEquals(badRequestResponse.result, Result.InternalServerError);
        Assert.assertTrue(badRequestResponse.errorRecords.isEmpty());
    }

    @Test
    public void notEqualsOtherClass() {
        ResponseDTO response = ResponseDTO.createSuccessfulResponse();
        Assert.assertNotEquals("something else", response);
    }

    @Test
    public void notEqualsSameCLassOtherArguments() {
        ResponseDTO response = ResponseDTO.createDuplicateReferenceResponse(1L, "NL06INGB12");
        ResponseDTO otherResponse = new ResponseDTO(Result.DuplicateReference, List.of(new ErrorRecord(1L, "otherAccountNumber")));
        Assert.assertNotEquals(otherResponse, response);
    }

    @Test
    public void equalsSameClass() {
        ResponseDTO response = ResponseDTO.createDuplicateReferenceAndIncorrectEndBalanceResponse(1L, "NL06INGB12", "NL07INGB34");
        ResponseDTO otherResponse = ResponseDTO.createDuplicateReferenceAndIncorrectEndBalanceResponse(1L, "NL06INGB12", "NL07INGB34");
        Assert.assertEquals(otherResponse, response);
    }
}
