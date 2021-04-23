package nl.gb.service;

import nl.gb.repository.Reference;
import nl.gb.repository.ReferenceRepository;
import nl.gb.service.data.CustomerStatementRecord;
import nl.gb.service.data.HandleTransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

;

@Service
public class CustomerStatementServiceImpl implements CustomerStatementService {
    private Logger logger = LoggerFactory.getLogger(CustomerStatementServiceImpl.class);

    private final ReferenceRepository referenceRepository;

    public CustomerStatementServiceImpl(ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    @Override
    public HandleTransactionResponse handleRequest(CustomerStatementRecord request) {
        Optional<String> maybeDuplicateReferenceAccountNumber = getAccountNrOfDuplicateRef(request.getTransactionReference());
        boolean isDuplicateReference = maybeDuplicateReferenceAccountNumber.isPresent();
        boolean isCorrectBalance = isCorrectBalance(request);


        String referenceAlreadyExistsReport = "reference nr " + request.getTransactionReference() + " already exists from an earlier customer statement.";
        if(isCorrectBalance) {
            uploadReference(request.getTransactionReference(), request.getAccountNumber());
            if(!isDuplicateReference) {
                logger.debug("Transaction values are correct and reference is unique.");
                return HandleTransactionResponse.createSuccessfulResponse();
            } else {
                logger.debug("Transaction values are correct, but " + referenceAlreadyExistsReport);
                return HandleTransactionResponse.createDuplicateReferenceResponse(request.getTransactionReference(), request.getAccountNumber());
            }
        } else {
            String invalidTransactionValuesReport = "Transaction values are incorrect: StartBalance (" + request.getStartBalance() +
                    ") + Mutation (" + request.getMutation() + ") is not equal to EndBalance (" + request.getEndBalance() + ").";
            if(!isDuplicateReference) {
                logger.debug(invalidTransactionValuesReport);
                return HandleTransactionResponse.createIncorrectEndBalanceResponse(request.getTransactionReference(), request.getAccountNumber());
            } else {
                logger.debug(invalidTransactionValuesReport + " Also the " + referenceAlreadyExistsReport);
                return HandleTransactionResponse.createDuplicateReferenceAndIncorrectEndBalanceResponse(request.getTransactionReference(),
                        request.getAccountNumber(),
                        maybeDuplicateReferenceAccountNumber.get());
            }
        }
    }

    private void uploadReference(Long transactionReference, String accountNumber) {
        logger.debug("Uploading transaction reference " + transactionReference + " with account number " + accountNumber + ".");
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
