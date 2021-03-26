package nl.gb.error;

public class JsonParsingErrorException extends RuntimeException {
    public JsonParsingErrorException(String notFoundFieldNames) {
        super("Could not parse fieldnames " + notFoundFieldNames);
    }
}

