package nl.gb.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class ErrorRecord {
    public final Long reference;
    public final String accountNumber;

    @JsonCreator
    public ErrorRecord(final @JsonProperty("reference") Long reference,
                       final @JsonProperty("accountNumber") String accountNumber) {
        this.reference = reference;
        this.accountNumber = accountNumber;
    }
}
