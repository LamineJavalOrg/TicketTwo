package it.unipv.posw.model.exception;

public class SedeException extends Exception {
	public SedeException(String message) {
        super(message);
    }

    public SedeException(String message, Throwable cause) {
        super(message, cause);
    }
}
