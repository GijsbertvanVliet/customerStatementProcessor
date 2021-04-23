package nl.gb.web.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.ToString;

@ToString
public enum Result {
    Successful("SUCCESSFUL"),
    DuplicateReference("DUPLICATE_REFERENCE"),
    IncorrectEndBalance("INCORRECT_END_BALANCE"),
    DuplicateReferenceIncorrectEndBalance("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE"),
    BadRequest("BAD_REQUEST"),
    InternalServerError("INTERNAL_SERVER_ERROR");

    final String jsonString;

    Result(String jsonString) {
        this.jsonString = jsonString;
    }

    @JsonValue
    public String resultStatus() {
        return jsonString;
    }
}
