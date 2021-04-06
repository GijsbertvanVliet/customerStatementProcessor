package nl.gb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import nl.gb.error.ErrorRecord;
import nl.gb.error.Result;

@Getter
@Setter
public class ResponseDTO {
    Result result;
    ErrorRecord[] errorRecords;

    @JsonCreator
    public ResponseDTO(final @JsonProperty("result") Result result,
                               final @JsonProperty("errorRecords") ErrorRecord[] errorRecords) {
        this.result = result;
        this.errorRecords = errorRecords;
    }

    public static ResponseDTO createBadRequestResponse() {
        return new ResponseDTO(Result.BadRequest, new ErrorRecord[0]);
    }

    public static ResponseDTO createInternalServerErrorResponse() {
        return new ResponseDTO(Result.InternalServerError, new ErrorRecord[0]);
    }
}
