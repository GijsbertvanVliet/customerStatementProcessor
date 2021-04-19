package nl.gb.service.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CustomerStatementRecord {
    private final Long transactionReference;
    private final String accountNumber;
    private final BigDecimal startBalance;
    private final BigDecimal mutation;
    private final String description;
    private final BigDecimal endBalance;
}
