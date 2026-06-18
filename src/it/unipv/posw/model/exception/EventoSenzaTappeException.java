package it.unipv.posw.model.exception;

public class EventoSenzaTappeException extends EventoException {
	 
	public EventoSenzaTappeException() {
		super("L'evento deve avere almeno una tappa.");
	}
}