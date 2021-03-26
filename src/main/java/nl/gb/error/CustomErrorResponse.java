package nl.gb.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomErrorResponse {
    String result = "BAD_REQUEST";
    String errorRecords;

    public CustomErrorResponse(String errorRecords) {
        this.errorRecords = errorRecords;
    }
}
