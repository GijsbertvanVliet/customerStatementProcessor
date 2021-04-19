package nl.gb.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
public class ResponseDTO {
    public final Result result;
    public final List<ErrorRecord> errorRecords;
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

}
