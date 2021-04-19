package nl.gb.cucumber;

import nl.gb.web.dto.RequestDTO;
import nl.gb.web.dto.ResponseDTO;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class TestHttpClient {
    private final String serverUrl = "http://localhost";
    private final String endpoint = "/customer/statement";

    @LocalServerPort
    private int port;
    private final RestTemplate restTemplate = new RestTemplate();

    private String thingsEndpoint() {
        return serverUrl + ":" + port + endpoint;
    }

    public ResponseEntity<ResponseDTO> post(final RequestDTO something) {
        return restTemplate.postForEntity(thingsEndpoint(), something, ResponseDTO.class);
    }

    public void clean() {
        restTemplate.delete(thingsEndpoint());
    }
}
