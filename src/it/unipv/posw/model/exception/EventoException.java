package it.unipv.posw.model.exception;

public class EventoException extends Exception {
    
	public EventoException(String message) {
        super(message);
    }

    public EventoException(String message, Throwable cause) {
        super(message, cause);
    }
}