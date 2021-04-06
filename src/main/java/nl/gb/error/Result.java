package nl.gb.error;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Result {
    Successful,
    DuplicateReference,
    IncorrectEndBalance,
    DuplicateReferenceIncorrectEndBalance,
    BadRequest,
    InternalServerError;

    @JsonValue
    public String resultStatus() {
        String resultString = "UNKNOWN_ENUM_VALUE";
        switch (this) {
            case Successful:
                resultString = "SUCCESSFUL";
                break;
            case DuplicateReference:
                resultString = "DUPLICATE_REFERENCE";
                break;
            case IncorrectEndBalance:
                resultString = "INCORRECT_END_BALANCE";
                break;
            case DuplicateReferenceIncorrectEndBalance:
                resultString = "DUPLICATE_REFERENCE_INCORRECT_END_BALANCE";
                break;
            case BadRequest:
                resultString = "BAD_REQUEST";
                break;
            case InternalServerError:
                resultString = "INTERNAL_SERVER_ERROR";
                break;
        }
        return resultString;
    }
}
