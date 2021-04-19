package nl.gb.service;

import nl.gb.repository.Reference;
import nl.gb.repository.ReferenceRepository;
import nl.gb.service.data.CustomerStatementRecord;
import nl.gb.service.data.HandleTransactionResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

;

@Service
public class CustomerStatementServiceImpl implements CustomerStatementService {

    private final ReferenceRepository referenceRepository;

    public CustomerStatementServiceImpl(ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    @Override
    public HandleTransactionResponse handleRequest(CustomerStatementRecord request) {
        Optional<String> maybeDuplicateReferenceAccountNumber = getAccountNrOfDuplicateRef(request.getTransactionReference());
        boolean isDuplicateReference = maybeDuplicateReferenceAccountNumber.isPresent();
        boolean isCorrectBalance = isCorrectBalance(request);


        if(isCorrectBalance) {
            uploadReference(request.getTransactionReference(), request.getAccountNumber());
            if(!isDuplicateReference) {
                return HandleTransactionResponse.createSuccessfulResponse();
            } else {
                return HandleTransactionResponse.createDuplicateReferenceResponse(request.getTransactionReference(), request.getAccountNumber());
            }
        } else {
            if(!isDuplicateReference) {
                return HandleTransactionResponse.createIncorrectEndBalanceResponse(request.getTransactionReference(), request.getAccountNumber());
            } else {
                return HandleTransactionResponse.createDuplicateReferenceAndIncorrectEndBalanceResponse(request.getTransactionReference(),
                        request.getAccountNumber(),
                        maybeDuplicateReferenceAccountNumber.get());
            }
        }
    }

    private void uploadReference(Long transactionReference, String accountNumber) {
        Reference referenceToUpload = new Reference(transactionReference,accountNumber);
        referenceRepository.save(referenceToUpload);
    }

    private Optional<String> getAccountNrOfDuplicateRef(Long transactionReference) {
        return referenceRepository.findById(transactionReference).map(Reference::getAccountNumber);
    }

    private boolean isCorrectBalance(CustomerStatementRecord request) {
        return request.getStartBalance().add(request.getMutation()).equals(request.getEndBalance());
    }
}
