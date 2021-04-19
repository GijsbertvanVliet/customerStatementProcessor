package nl.gb.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
public class RequestDTO {
    @NotNull
    private final Long transactionReference;
    @NotBlank
    private final String accountNumber;
    @NotNull
    private final BigDecimal startBalance;
    @NotNull
    private final BigDecimal mutation;
    @NotBlank
    private final String description;
    @NotNull
    private final BigDecimal endBalance;

    @JsonCreator
    public RequestDTO(final @JsonProperty("transaction reference") Long transactionReference,
                      final @JsonProperty("account number") String accountNumer,
                      final @JsonProperty("start balance") BigDecimal startBalance,
                      final @JsonProperty("mutation") BigDecimal mutation,
                      final @JsonProperty("description") String description,
                      final @JsonProperty("end balance") BigDecimal endBalance) {
        this.transactionReference = transactionReference;
        this.accountNumber = accountNumer;
        this.startBalance = startBalance;
        this.mutation = mutation;
        this.description = description;
        this.endBalance = endBalance;
    }
}
