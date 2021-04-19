package nl.gb.web.dto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResponseDTOTest {
    @Test
    public void testCSuccessfulResponse() {
        ResponseDTO successResponse = ResponseDTO.createSuccessfulResponse();
        Assert.assertEquals(successResponse.result, Result.Successful);
        Assert.assertTrue(successResponse.errorRecords.isEmpty());
    }

    @Test
    public void testCDuplicateReferenceResponse() {
        Long transactionReference = 1L;
        String accountNumber = "NL06INGB12";
        ResponseDTO duplicateReferenceResponse = ResponseDTO.createDuplicateReferenceResponse(transactionReference, accountNumber);
        Assert.assertEquals(duplicateReferenceResponse.result, Result.DuplicateReference);
        ErrorRecord expectedErrorRecord = duplicateReferenceResponse.errorRecords.get(0);
        Assert.assertEquals(expectedErrorRecord.reference, transactionReference);
        Assert.assertEquals(expectedErrorRecord.accountNumber, accountNumber);
    }

    @Test
    public void testCIncorrectEndBalanceResponse() {
        Long transactionReference = 1L;
        String accountNumber = "NL06INGB12";
        ResponseDTO duplicateReferenceResponse = ResponseDTO.createIncorrectEndBalanceResponse(transactionReference, accountNumber);
        Assert.assertEquals(duplicateReferenceResponse.result, Result.IncorrectEndBalance);
        ErrorRecord expectedErrorRecord = duplicateReferenceResponse.errorRecords.get(0);
        Assert.assertEquals(expectedErrorRecord.reference, transactionReference);
        Assert.assertEquals(expectedErrorRecord.accountNumber, accountNumber);
    }

    @Test
    public void testCDuplicateReferenceAndIncorrectEndBalanceResponse() {
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
    public void testCBadRequestResponse() {
        ResponseDTO badRequestResponse = ResponseDTO.createBadRequestResponse();
        Assert.assertEquals(badRequestResponse.result, Result.BadRequest);
        Assert.assertTrue(badRequestResponse.errorRecords.isEmpty());
    }

    @Test
    public void testCInternalServerErrorResponse() {
        ResponseDTO badRequestResponse = ResponseDTO.createInternalServerErrorResponse();
        Assert.assertEquals(badRequestResponse.result, Result.InternalServerError);
        Assert.assertTrue(badRequestResponse.errorRecords.isEmpty());
    }
}
