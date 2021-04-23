package nl.gb.web.dto;

import nl.gb.service.data.CustomerStatementRecord;
import nl.gb.service.data.HandleTransactionErrorRecord;
import nl.gb.service.data.HandleTransactionResponse;
import nl.gb.service.data.HandleTransactionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public class DataClassConverter {
    private static Logger logger = LoggerFactory.getLogger(DataClassConverter.class);

    public static ResponseDTO handleTransactionResponseToDataTransferobject(HandleTransactionResponse response) {
        logger.debug("Converting business response to data transfer object.");
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
        logger.debug("Converting request data transfer object to business request.");
        return new CustomerStatementRecord(request.getTransactionReference(),
                request.getAccountNumber(),
                request.getStartBalance(),
                request.getMutation(),
                request.getDescription(),
                request.getEndBalance());
    }
}
