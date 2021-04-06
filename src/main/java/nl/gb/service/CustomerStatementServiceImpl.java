package nl.gb.service;

import nl.gb.dto.RequestDTO;
import nl.gb.dto.ResponseDTO;
import nl.gb.error.ErrorRecord;
import nl.gb.error.Result;
import org.springframework.stereotype.Service;

@Service
public class CustomerStatementServiceImpl implements CustomerStatementService {
    @Override
    public ResponseDTO handleRequest(RequestDTO request) {
        if(request.startBalance + request.mutation == request.endBalance) {
            return new ResponseDTO(Result.Successful, new ErrorRecord[0]);
        } else {
            ErrorRecord errorRecord = new ErrorRecord(request.transactionReference, request.accountNumber);
            ErrorRecord[] errorRecords = new ErrorRecord[1];
            errorRecords[0] = errorRecord;
            return new ResponseDTO(Result.IncorrectEndBalance, errorRecords);
        }
    }
}
