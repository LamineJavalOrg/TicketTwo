package it.unipv.posw.model.exception;

public class EventoSenzaTappeException extends Exception {
	 
	public EventoSenzaTappeException() {
		super("L'evento deve avere almeno una tappa.");
	}
}