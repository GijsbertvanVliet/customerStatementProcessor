package nl.gb.service;

import nl.gb.service.data.CustomerStatementRecord;
import nl.gb.service.data.HandleTransactionResponse;

public interface CustomerStatementService {
    HandleTransactionResponse handleRequest(CustomerStatementRecord request);
}
