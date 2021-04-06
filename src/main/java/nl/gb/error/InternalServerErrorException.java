package nl.gb.error;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String notFoundFieldNames) {
        super(notFoundFieldNames);
    }
}
