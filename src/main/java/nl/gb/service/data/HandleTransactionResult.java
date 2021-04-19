package nl.gb.service.data;

public enum HandleTransactionResult {
    Successful("SUCCESSFUL"),
    DuplicateReference("DUPLICATE_REFERENCE"),
    IncorrectEndBalance("INCORRECT_END_BALANCE"),
    DuplicateReferenceIncorrectEndBalance("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE"),
    BadRequest("BAD_REQUEST"),
    InternalServerError("INTERNAL_SERVER_ERROR");

    final String jsonString;

    HandleTransactionResult(String jsonString) {
        this.jsonString = jsonString;
    }
}
