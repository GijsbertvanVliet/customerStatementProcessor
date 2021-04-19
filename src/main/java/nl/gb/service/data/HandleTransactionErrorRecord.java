package nl.gb.service.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class HandleTransactionErrorRecord {
    public final Long reference;
    public final String accountNumber;
}
