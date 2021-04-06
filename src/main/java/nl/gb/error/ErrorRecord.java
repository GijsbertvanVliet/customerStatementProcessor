package nl.gb.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorRecord {
    public Long reference;
    public String accountNumber;

    @JsonCreator
    public ErrorRecord(final @JsonProperty("reference") Long reference,
                       final @JsonProperty("accountNumber") String accountNumber) {
        this.reference = reference;
        this.accountNumber = accountNumber;
    }
}
