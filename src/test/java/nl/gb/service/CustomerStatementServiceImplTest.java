package nl.gb.service;

import nl.gb.repository.Reference;
import nl.gb.repository.ReferenceRepository;
import nl.gb.service.data.CustomerStatementRecord;
import nl.gb.service.data.HandleTransactionErrorRecord;
import nl.gb.service.data.HandleTransactionResponse;
import nl.gb.service.data.HandleTransactionResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerStatementServiceImplTest {

    @InjectMocks
    private CustomerStatementServiceImpl testableService;

    @Mock
    private ReferenceRepository referenceRepository;

    @Test
    public void successfulTestCase() {
        CustomerStatementRecord request = new CustomerStatementRecord(1L,
                "NL06INBG12",
                new BigDecimal(100),
                new BigDecimal(-10),
                "some description",
                new BigDecimal(90));

        when(referenceRepository.findById(1L)).thenReturn(Optional.empty());

        HandleTransactionResponse expectedResponse = new HandleTransactionResponse(HandleTransactionResult.Successful, List.of());
        HandleTransactionResponse actualResponse = testableService.handleRequest(request);
        Assert.assertEquals(expectedResponse, actualResponse);

        Reference refToBeUploaded = new Reference(1L,"NL06INBG12");
        verify(referenceRepository, times(1)).save(refToBeUploaded);
    }

    @Test
    public void incorrectEndBalanceTestCase() {
        CustomerStatementRecord request = new CustomerStatementRecord(1L,
                "NL06INBG12",
                new BigDecimal(100),
                new BigDecimal(-1),
                "some description",
                new BigDecimal(80));
        when(referenceRepository.findById(1L)).thenReturn(Optional.empty());

        HandleTransactionResponse expectedResponse = new HandleTransactionResponse(HandleTransactionResult.IncorrectEndBalance, List.of(new HandleTransactionErrorRecord(1L, "NL06INBG12")));
        HandleTransactionResponse actualResponse = testableService.handleRequest(request);
        Assert.assertEquals(expectedResponse, actualResponse);

        verify(referenceRepository, never()).save(any(Reference.class));
    }

    @Test
    public void duplicateReferenceTestCase() {
        CustomerStatementRecord request = new CustomerStatementRecord(1L,
                "NL06INBG12",
                new BigDecimal(100),
                new BigDecimal(-10),
                "some description",
                new BigDecimal(90));
        Reference duplicateReference = new Reference(1L, "NL07INGB34");
        when(referenceRepository.findById(1L)).thenReturn(Optional.of(duplicateReference));

        HandleTransactionResponse expectedResponse = new HandleTransactionResponse(HandleTransactionResult.DuplicateReference,
                List.of(new HandleTransactionErrorRecord(1L, "NL06INBG12")));
        HandleTransactionResponse actualResponse = testableService.handleRequest(request);
        Assert.assertEquals(expectedResponse, actualResponse);

        Reference refToBeUploaded = new Reference(1L,                "NL06INBG12");
        verify(referenceRepository, times(1)).save(refToBeUploaded);
    }

    @Test
    public void duplidateReferenceAndIncorrectEndBalanceTestCase() {
        CustomerStatementRecord request = new CustomerStatementRecord(1L,
                "NL06INBG12",
                new BigDecimal(100),
                new BigDecimal(-10),
                "some description",
                new BigDecimal(80));
        Reference duplicateReference = new Reference(1L,                "NL07INGB34");
        when(referenceRepository.findById(1L)).thenReturn(Optional.of(duplicateReference));

        HandleTransactionResponse expectedResponse = new HandleTransactionResponse(HandleTransactionResult.DuplicateReferenceIncorrectEndBalance, List.of(
                new HandleTransactionErrorRecord(1L, "NL07INGB34"),
                new HandleTransactionErrorRecord(1L, "NL06INBG12")));
        HandleTransactionResponse actualResponse = testableService.handleRequest(request);
        Assert.assertEquals(expectedResponse, actualResponse);

        verify(referenceRepository, never()).save(any(Reference.class));
    }

}
