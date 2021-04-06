package nl.gb.service;

import nl.gb.dto.RequestDTO;
import nl.gb.dto.ResponseDTO;
import nl.gb.error.ErrorRecord;
import nl.gb.error.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CustomerStatementServiceImplTest {
    private final CustomerStatementServiceImpl testableService = new CustomerStatementServiceImpl();

    @Test
    public void successfullTestCase() {
        RequestDTO request = new RequestDTO(1L,
                "NL06INBG12",
                100L,
                -10L,
                "some description",
                90L);
        ResponseDTO expectedResponse = new ResponseDTO(Result.Successful, List.of());
        ResponseDTO actualResponse = testableService.handleRequest(request);
        Assert.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void incorrectEndBalanceTestCase() {
        RequestDTO request = new RequestDTO(1L,
                "NL06INBG12",
                100L,
                -1L,
                "some description",
                80L);
        ResponseDTO expectedResponse = new ResponseDTO(Result.IncorrectEndBalance, List.of(new ErrorRecord(1L, "NL06INBG12")));
        ResponseDTO actualResponse = testableService.handleRequest(request);
        Assert.assertEquals(expectedResponse, actualResponse);
    }

}
