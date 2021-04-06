package nl.gb.dto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RequestDTOTEST {
    private final RequestDTO request = new RequestDTO(1L,
            "NL06INBG12",
            100L,
            -1L,
            "some description",
            90L);

    @Test
    public void notEqualsOtherClass() {
        Assert.assertNotEquals("some other thing", request);
    }

    @Test
    public void notEqualsSameClassOtherArguments() {
        RequestDTO otherRequest = new RequestDTO(1L,
                "other account number",
                100L,
                -1L,
                "some description",
                90L);
        Assert.assertNotEquals(otherRequest, request);
    }

    @Test
    public void equalsSameClassSameArguments() {
        RequestDTO otherRequest = new RequestDTO(1L,
                "NL06INBG12",
                100L,
                -1L,
                "some description",
                90L);
        Assert.assertEquals(otherRequest, request);
    }
}
