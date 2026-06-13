package it.unipv.posw.model.exception;

public class PostiInsufficientiException extends Exception {
	
	public PostiInsufficientiException() {
		super("Posti insufficienti per il numero di biglietti richiesto.");
	}
}
