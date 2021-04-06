package nl.gb.service;

import nl.gb.dto.RequestDTO;
import nl.gb.dto.ResponseDTO;

public interface CustomerStatementService {
    public abstract ResponseDTO handleRequest(RequestDTO request);
}
