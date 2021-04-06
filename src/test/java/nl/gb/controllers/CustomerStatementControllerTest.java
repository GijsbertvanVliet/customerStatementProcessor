package nl.gb.controllers;

import nl.gb.dto.RequestDTO;
import nl.gb.dto.ResponseDTO;
import nl.gb.error.ErrorRecord;
import nl.gb.error.Result;
import nl.gb.service.CustomerStatementService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerStatementControllerTest {

    @MockBean
    private CustomerStatementService service;

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSucceed() throws Exception {
        String requestBody = "{\"transaction reference\":1,\"account number\":\"someAccount\",\"start balance\":100,\"mutation\":-10,\"description\":\"something\",\"end balance\":90}";
        RequestDTO request = new RequestDTO(1L, "someAccount", 100L, -10L, "something", 90L);
        Mockito.when(service.handleRequest(request)).thenReturn(new ResponseDTO(Result.Successful, List.of()));
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/customer/statement")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assert.assertEquals(response, "{\"result\":\"SUCCESSFUL\",\"errorRecords\":[]}");
    }

    @Test
    public void testDuplicateReference() throws Exception {
        String requestBody = "{\"transaction reference\":1,\"account number\":\"someAccount\",\"start balance\":100,\"mutation\":-10,\"description\":\"something\",\"end balance\":90}";
        RequestDTO request = new RequestDTO(1L, "someAccount", 100L, -10L, "something", 90L);
        Mockito.when(service.handleRequest(request)).thenReturn(new ResponseDTO(Result.DuplicateReference, List.of(new ErrorRecord(1L, "someAccount"))));
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/customer/statement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assert.assertEquals(response, "{\"result\":\"DUPLICATE_REFERENCE\",\"errorRecords\":[{\"reference\":1,\"accountNumber\":\"someAccount\"}]}");
    }

    @Test
    public void testIncorrectEndBalance() throws Exception {
        String requestBody = "{\"transaction reference\":1,\"account number\":\"someAccount\",\"start balance\":100,\"mutation\":-10,\"description\":\"something\",\"end balance\":80}";
        RequestDTO request = new RequestDTO(1L, "someAccount", 100L, -10L, "something", 80L);
        Mockito.when(service.handleRequest(request)).thenReturn(new ResponseDTO(Result.IncorrectEndBalance, List.of(new ErrorRecord(1L, "someAccount"))));
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/customer/statement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assert.assertEquals(response, "{\"result\":\"INCORRECT_END_BALANCE\",\"errorRecords\":[{\"reference\":1,\"accountNumber\":\"someAccount\"}]}");
    }

    @Test
    public void testDuplicateReferenceAndIncorrectEndBalance() throws Exception {
        String requestBody = "{\"transaction reference\":1,\"account number\":\"someAccount\",\"start balance\":100,\"mutation\":-10,\"description\":\"something\",\"end balance\":80}";
        RequestDTO request = new RequestDTO(1L, "someAccount", 100L, -10L, "something", 80L);
        Mockito.when(service.handleRequest(request)).thenReturn(new ResponseDTO(Result.DuplicateReferenceIncorrectEndBalance, List.of(new ErrorRecord(1L, "someAccount"))));
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/customer/statement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assert.assertEquals(response, "{\"result\":\"DUPLICATE_REFERENCE_INCORRECT_END_BALANCE\",\"errorRecords\":[{\"reference\":1,\"accountNumber\":\"someAccount\"}]}");
    }

    @Test
    public void testJsonParsingError() throws Exception {
        String requestBody = "{\"typo\":1,\"account number\":\"someAccount\",\"start balance\":100,\"mutation\":-10,\"description\":\"something\",\"end balance\":80}";
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/customer/statement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assert.assertEquals(response, "{\"result\":\"BAD_REQUEST\",\"errorRecords\":[]}");
    }

    @Test
    public void testInternalServerError() throws Exception {
        String requestBody = "{\"transaction reference\":1,\"account number\":\"someAccount\",\"start balance\":100,\"mutation\":-10,\"description\":\"something\",\"end balance\":90}";
        RequestDTO request = new RequestDTO(1L, "someAccount", 100L, -10L, "something", 90L);
        Mockito.when(service.handleRequest(request)).thenThrow(new NullPointerException("something failed"));
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/customer/statement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assert.assertEquals(response, "{\"result\":\"INTERNAL_SERVER_ERROR\",\"errorRecords\":[]}");
    }
}
