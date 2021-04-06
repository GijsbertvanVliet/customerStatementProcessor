package nl.gb.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ErrorRecord {
    public final Long reference;
    public final String accountNumber;

    @JsonCreator
    public ErrorRecord(final @JsonProperty("reference") Long reference,
                       final @JsonProperty("accountNumber") String accountNumber) {
        this.reference = reference;
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ErrorRecord) {
            ErrorRecord otherRecord = (ErrorRecord) obj;
            return this.reference.equals(otherRecord.reference)
                    && this.accountNumber.equals(otherRecord.accountNumber);
        } else {
            return false;
        }
    }
}
