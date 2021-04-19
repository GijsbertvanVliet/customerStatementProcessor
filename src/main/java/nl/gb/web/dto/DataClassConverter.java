package nl.gb.web.dto;

import nl.gb.service.data.CustomerStatementRecord;
import nl.gb.service.data.HandleTransactionErrorRecord;
import nl.gb.service.data.HandleTransactionResponse;
import nl.gb.service.data.HandleTransactionResult;

import java.util.stream.Collectors;

public class DataClassConverter {
    public static ResponseDTO handleTransactionResponseToDataTransferobject(HandleTransactionResponse response) {
        return new ResponseDTO(resultToDataTransferObject(response.result),
                response.errorRecords.stream().map(DataClassConverter::errorRecordToDataTransferObject).collect(Collectors.toList()));
    }

    private static ErrorRecord errorRecordToDataTransferObject(HandleTransactionErrorRecord errorRecord) {
        return new ErrorRecord(errorRecord.reference, errorRecord.accountNumber);
    }

    private static Result resultToDataTransferObject(HandleTransactionResult result) {
        Result resultToReturn = Result.Successful;
        switch (result) {
            case Successful:
                resultToReturn = Result.Successful;
                break;
            case DuplicateReference:
                resultToReturn = Result.DuplicateReference;
                break;
            case IncorrectEndBalance:
                resultToReturn = Result.IncorrectEndBalance;
                break;
            case DuplicateReferenceIncorrectEndBalance:
                resultToReturn = Result.DuplicateReferenceIncorrectEndBalance;
                break;
            case BadRequest:
                resultToReturn = Result.BadRequest;
                break;
            case InternalServerError:
                resultToReturn = Result.InternalServerError;
                break;
        }
        return resultToReturn;
    }

    public static CustomerStatementRecord requestToCustomerStatementRecord(RequestDTO request) {
        return new CustomerStatementRecord(request.getTransactionReference(),
                request.getAccountNumber(),
                request.getStartBalance(),
                request.getMutation(),
                request.getDescription(),
                request.getEndBalance());
    }
}
