package dev.yudin.exceptions;

public class XMLDataException extends RuntimeException {

    public XMLDataException() {
        super();
    }

    public XMLDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLDataException(String message) {
        super(message);
    }
}
