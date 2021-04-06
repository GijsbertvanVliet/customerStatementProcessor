package nl.gb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class RequestDTO {
    @NotNull
    public final Long transactionReference;
    @NotBlank
    public final String accountNumber;
    @NotNull
    public final Long startBalance;
    @NotNull
    public final Long mutation;
    @NotBlank
    public final String description;
    @NotNull
    public final Long endBalance;

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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RequestDTO) {
            RequestDTO otherRequest = (RequestDTO) obj;
            return this.transactionReference.equals(otherRequest.transactionReference)
                    && this.accountNumber.equals(otherRequest.accountNumber)
                    && this.startBalance.equals(otherRequest.startBalance)
                    && this.mutation.equals(otherRequest.mutation)
                    && this.description.equals(otherRequest.description)
                    && this.endBalance.equals(otherRequest.endBalance);
        } else {
            return false;
        }
    }
}
