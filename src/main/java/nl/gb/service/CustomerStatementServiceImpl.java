package nl.gb.service;

import nl.gb.dto.RequestDTO;
import nl.gb.dto.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class CustomerStatementServiceImpl implements CustomerStatementService {
    @Override
    public ResponseDTO handleRequest(RequestDTO request) {
        if(request.startBalance + request.mutation == request.endBalance) {
            return ResponseDTO.createSuccessfulResponse();
        } else {
            return ResponseDTO.createIncorrectEndBalanceResponse(request.transactionReference, request.accountNumber);
        }
    }
}
