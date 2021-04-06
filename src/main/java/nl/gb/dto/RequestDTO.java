package nl.gb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestDTO {
    @NotNull
    public Long transactionReference;
    @NotBlank
    public String accountNumber;
    @NotNull
    public Long startBalance;
    @NotNull
    public Long mutation;
    @NotBlank
    public String description;
    @NotNull
    public Long endBalance;

    @JsonCreator
    public RequestDTO(final @JsonProperty("transaction reference") Long transactionReference,
                      final @JsonProperty("account number") String accountNumer,
                      final @JsonProperty("start balance") Long startBalance,
                      final @JsonProperty("mutation") Long mutation,
                      final @JsonProperty("description") String description,
                      final @JsonProperty("end balance") Long endBalance) {
        this.transactionReference = transactionReference;
        this.accountNumber = accountNumer;
        this.startBalance = startBalance;
        this.mutation = mutation;
        this.description = description;
        this.endBalance = endBalance;
    }
}
