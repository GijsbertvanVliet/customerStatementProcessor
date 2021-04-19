package nl.gb.service.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.gb.web.dto.ErrorRecord;

import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
public class HandleTransactionResponse {
    public final HandleTransactionResult result;
    public final List<HandleTransactionErrorRecord> errorRecords;
    static final private List<HandleTransactionErrorRecord> emptyList = List.of();

    public static HandleTransactionResponse createSuccessfulResponse() {
        return new HandleTransactionResponse(HandleTransactionResult.Successful, emptyList);
    }

    public static HandleTransactionResponse createDuplicateReferenceResponse(Long transactionReference, String accountNumber) {
        HandleTransactionErrorRecord errorRecord = new HandleTransactionErrorRecord(transactionReference, accountNumber);
        List<HandleTransactionErrorRecord> errorRecords = List.of(errorRecord);
        return new HandleTransactionResponse(HandleTransactionResult.DuplicateReference, errorRecords);
    }

    public static HandleTransactionResponse createIncorrectEndBalanceResponse(Long transactionReference, String accountNumber) {
        HandleTransactionErrorRecord errorRecord = new HandleTransactionErrorRecord(transactionReference, accountNumber);
        List<HandleTransactionErrorRecord> errorRecords = List.of(errorRecord);
        return new HandleTransactionResponse(HandleTransactionResult.IncorrectEndBalance, errorRecords);
    }

    public static HandleTransactionResponse createDuplicateReferenceAndIncorrectEndBalanceResponse(Long transactionReference,
                                                                                                   String accountNumber,
                                                                                                   String duplicateAccountNumber) {
        List<HandleTransactionErrorRecord> errorRecords = List.of(new HandleTransactionErrorRecord(transactionReference, duplicateAccountNumber),
                new HandleTransactionErrorRecord(transactionReference, accountNumber));
        return new HandleTransactionResponse(HandleTransactionResult.DuplicateReferenceIncorrectEndBalance, errorRecords);
    }

    public static HandleTransactionResponse createBadRequestResponse() {
        return new HandleTransactionResponse(HandleTransactionResult.BadRequest, emptyList);
    }

    public static HandleTransactionResponse createInternalServerErrorResponse() {
        return new HandleTransactionResponse(HandleTransactionResult.InternalServerError, emptyList);
    }

}
