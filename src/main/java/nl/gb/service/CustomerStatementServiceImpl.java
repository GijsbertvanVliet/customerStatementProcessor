package nl.gb.service;

import nl.gb.dto.RequestDTO;
import nl.gb.dto.ResponseDTO;
import nl.gb.repository.Reference;
import nl.gb.repository.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerStatementServiceImpl implements CustomerStatementService {

    @Autowired
    ReferenceRepository referenceRepository;

    @Override
    public ResponseDTO handleRequest(RequestDTO request) {
        String transactionReferenceString = String.valueOf(request.transactionReference);
        Optional<String> maybeDuplicateReferenceAccountNumber = getAccountNrOfDuplicateRef(transactionReferenceString);
        boolean isDuplicateReference = maybeDuplicateReferenceAccountNumber.isPresent();
        boolean isCorrectBalance = isCorrectBalance(request);

        if(isCorrectBalance) {
            uploadReference(transactionReferenceString, request.accountNumber);
            if(!isDuplicateReference) {
                return ResponseDTO.createSuccessfulResponse();
            } else {
                return ResponseDTO.createDuplicateReferenceResponse(request.transactionReference, request.accountNumber);
            }
        } else {
            if(!isDuplicateReference) {
                return ResponseDTO.createIncorrectEndBalanceResponse(request.transactionReference, request.accountNumber);
            } else {
                return ResponseDTO.createDuplicateReferenceAndIncorrectEndBalanceResponse(request.transactionReference,
                        request.accountNumber,
                        maybeDuplicateReferenceAccountNumber.get());
            }
        }
    }

    private void uploadReference(String transactionReference, String accountNumber) {
        Reference referenceToUpload = new Reference();
        referenceToUpload.setTrxReference(transactionReference);
        referenceToUpload.setAccountNumber(accountNumber);
        referenceRepository.save(referenceToUpload);
    }

    private Optional<String> getAccountNrOfDuplicateRef(String transactionReference) {
        return referenceRepository.findById(transactionReference).map(Reference::getAccountNumber);
    }

    private boolean isCorrectBalance(RequestDTO request) {
        return request.startBalance + request.mutation == request.endBalance;
    }
}
