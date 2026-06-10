package it.unipv.posw.model.exception;

public class SedeNonEliminabileException extends Exception {

	public SedeNonEliminabileException() {
		super("Impossibile eliminare la sede: è occupata da uno o più eventi.");
	}
}
