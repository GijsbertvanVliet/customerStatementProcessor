package nl.gb.service;

import nl.gb.dto.RequestDTO;
import nl.gb.dto.ResponseDTO;
import nl.gb.error.ErrorRecord;
import nl.gb.error.Result;
import nl.gb.repository.Reference;
import nl.gb.repository.ReferenceRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CustomerStatementServiceImplTest {

    @InjectMocks
    private CustomerStatementServiceImpl testableService;

    @Mock
    private ReferenceRepository referenceRepository;

    @Test
    public void successfulTestCase() {
        RequestDTO request = new RequestDTO(1L,
                "NL06INBG12",
                100L,
                -10L,
                "some description",
                90L);

        when(referenceRepository.findById("1")).thenReturn(Optional.empty());

        ResponseDTO expectedResponse = new ResponseDTO(Result.Successful, List.of());
        ResponseDTO actualResponse = testableService.handleRequest(request);
        Assert.assertEquals(expectedResponse, actualResponse);

        Reference refToBeUploaded = new Reference();
        refToBeUploaded.setTrxReference("1");
        refToBeUploaded.setAccountNumber("NL06INBG12");
        verify(referenceRepository, times(1)).save(refToBeUploaded);
    }

    @Test
    public void incorrectEndBalanceTestCase() {
        RequestDTO request = new RequestDTO(1L,
                "NL06INBG12",
                100L,
                -1L,
                "some description",
                80L);
        when(referenceRepository.findById("1")).thenReturn(Optional.empty());

        ResponseDTO expectedResponse = new ResponseDTO(Result.IncorrectEndBalance, List.of(new ErrorRecord(1L, "NL06INBG12")));
        ResponseDTO actualResponse = testableService.handleRequest(request);
        Assert.assertEquals(expectedResponse, actualResponse);

        verify(referenceRepository, never()).save(any(Reference.class));
    }

    @Test
    public void duplicateReferenceTestCase() {
        RequestDTO request = new RequestDTO(1L,
                "NL06INBG12",
                100L,
                -10L,
                "some description",
                90L);
        Reference duplicateReference = new Reference();
        duplicateReference.setTrxReference("1");
        duplicateReference.setAccountNumber("NL07INGB34");
        when(referenceRepository.findById("1")).thenReturn(Optional.of(duplicateReference));

        ResponseDTO expectedResponse = new ResponseDTO(Result.DuplicateReference, List.of(new ErrorRecord(1L, "NL06INBG12")));
        ResponseDTO actualResponse = testableService.handleRequest(request);
        Assert.assertEquals(expectedResponse, actualResponse);

        Reference refToBeUploaded = new Reference();
        refToBeUploaded.setTrxReference("1");
        refToBeUploaded.setAccountNumber("NL06INBG12");
        verify(referenceRepository, times(1)).save(refToBeUploaded);
    }

    @Test
    public void duplidateReferenceAndIncorrectEndBalanceTestCase() {
        RequestDTO request = new RequestDTO(1L,
                "NL06INBG12",
                100L,
                -10L,
                "some description",
                80L);
        Reference duplicateReference = new Reference();
        duplicateReference.setTrxReference("1");
        duplicateReference.setAccountNumber("NL07INGB34");
        when(referenceRepository.findById("1")).thenReturn(Optional.of(duplicateReference));

        ResponseDTO expectedResponse = new ResponseDTO(Result.DuplicateReferenceIncorrectEndBalance, List.of(
                new ErrorRecord(1L, "NL07INGB34"),
                new ErrorRecord(1L, "NL06INBG12")));
        ResponseDTO actualResponse = testableService.handleRequest(request);
        Assert.assertEquals(expectedResponse, actualResponse);

        verify(referenceRepository, never()).save(any(Reference.class));
    }

}
