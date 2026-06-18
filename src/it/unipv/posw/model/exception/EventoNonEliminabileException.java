package it.unipv.posw.model.exception;

public class EventoNonEliminabileException extends EventoException {
	 
	public EventoNonEliminabileException() {
		super("Impossibile eliminare l'evento: sono già stati venduti dei biglietti.");
	}
}