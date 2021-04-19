package nl.gb.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.gb.repository.Reference;
import nl.gb.repository.ReferenceRepository;
import nl.gb.service.CustomerStatementService;
import nl.gb.web.dto.ErrorRecord;
import nl.gb.web.dto.RequestDTO;
import nl.gb.web.dto.ResponseDTO;
import nl.gb.web.dto.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CustomerControllerTest {

    @Autowired
    private CustomerStatementService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ReferenceRepository repository;

    @Before
    public void beforeEach(){
        repository.deleteAll();
        repository.save(new Reference(1101L, "abnAmro"));
    }

    public void processRequestAndTestResponse(RequestDTO request, ResponseDTO expectedResponse) throws Exception {
        String requestBody = mapper.writeValueAsString(request);

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/customer/statement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ResponseDTO responseObject = mapper.readValue(response, ResponseDTO.class);
        Assert.assertEquals(responseObject, expectedResponse);
    }

    @Test
    public void testSucceed() throws Exception {
        RequestDTO request = new RequestDTO(1L,
                "someAccount",
                new BigDecimal(100),
                new BigDecimal(-10),
                "something",
                new BigDecimal(90));
        ResponseDTO expectedResponse = new ResponseDTO(Result.Successful, List.of());
        processRequestAndTestResponse(request, expectedResponse);

        ResponseDTO secondExpectedResponse = new ResponseDTO(Result.DuplicateReference, List.of(new ErrorRecord(1L, "someAccount")));
        processRequestAndTestResponse(request, secondExpectedResponse);
    }

    @Test
    public void testDuplicateReference() throws Exception {
        RequestDTO request = new RequestDTO( 1101L,
                "someAccount",
                new BigDecimal(100),
                new BigDecimal(-10),
                "something",
                new BigDecimal(90));
        ResponseDTO expectedResponse = new ResponseDTO(Result.DuplicateReference, List.of(new ErrorRecord(1101L, "someAccount")));
        processRequestAndTestResponse(request, expectedResponse);
    }

    @Test
    public void testIncorrectEndBalance() throws Exception {
        RequestDTO request = new RequestDTO(1L,
                "someAccount",
                new BigDecimal(100),
                new BigDecimal(-10),
                "something",
                new BigDecimal(80));
        ResponseDTO expectedResponse = new ResponseDTO(Result.IncorrectEndBalance, List.of(new ErrorRecord(1L, "someAccount")));
        processRequestAndTestResponse(request, expectedResponse);
    }

    @Test
    public void testDuplicateReferenceAndIncorrectEndBalance() throws Exception {
        RequestDTO request = new RequestDTO(1101L,
                "someAccount",
                new BigDecimal(100),
                new BigDecimal(-10),
                "something",
                new BigDecimal(80L));
        ResponseDTO expectedResponse = new ResponseDTO(Result.DuplicateReferenceIncorrectEndBalance,
                List.of(new ErrorRecord(1101L, "abnAmro"), new ErrorRecord(1101L, "someAccount")));
        processRequestAndTestResponse(request, expectedResponse);
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
        ResponseDTO responseObject = mapper.readValue(response, ResponseDTO.class);
        Assert.assertEquals(responseObject, new ResponseDTO(Result.BadRequest, List.of()));
    }
}
