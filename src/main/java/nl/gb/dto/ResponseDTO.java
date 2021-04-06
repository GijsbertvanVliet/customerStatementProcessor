package nl.gb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import nl.gb.error.ErrorRecord;
import nl.gb.error.Result;

import java.util.List;

@Getter
public class ResponseDTO {
    final Result result;
    final List<ErrorRecord> errorRecords;
    static final private List<ErrorRecord> emptyList = List.of();

    @JsonCreator
    public ResponseDTO(final @JsonProperty("result") Result result,
                               final @JsonProperty("errorRecords") List<ErrorRecord> errorRecords) {
        this.result = result;
        this.errorRecords = errorRecords;
    }

    public static ResponseDTO createSuccessfulResponse() {
        return new ResponseDTO(Result.Successful, emptyList);
    }

    public static ResponseDTO createDuplicateReferenceResponse(Long transactionReference, String accountNumber) {
        ErrorRecord errorRecord = new ErrorRecord(transactionReference, accountNumber);
        List<ErrorRecord> errorRecords = List.of(errorRecord);
        return new ResponseDTO(Result.DuplicateReference, errorRecords);
    }

    public static ResponseDTO createIncorrectEndBalanceResponse(Long transactionReference, String accountNumber) {
        ErrorRecord errorRecord = new ErrorRecord(transactionReference, accountNumber);
        List<ErrorRecord> errorRecords = List.of(errorRecord);
        return new ResponseDTO(Result.IncorrectEndBalance, errorRecords);
    }

    public static ResponseDTO createDuplicateReferenceAndIncorrectEndBalanceResponse(Long transactionReference,
                                                                                     String accountNumber,
                                                                                     String duplicateAccountNumber) {
        List<ErrorRecord> errorRecords = List.of(new ErrorRecord(transactionReference, duplicateAccountNumber),
                new ErrorRecord(transactionReference, accountNumber));
        return new ResponseDTO(Result.DuplicateReferenceIncorrectEndBalance, errorRecords);
    }

    public static ResponseDTO createBadRequestResponse() {
        return new ResponseDTO(Result.BadRequest, emptyList);
    }

    public static ResponseDTO createInternalServerErrorResponse() {
        return new ResponseDTO(Result.InternalServerError, emptyList);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ResponseDTO) {
            ResponseDTO otherResponse = (ResponseDTO) obj;
            return this.result.equals(otherResponse.result)
                    && this.errorRecords.equals(otherResponse.errorRecords);
        } else {
            return false;
        }
    }
}
