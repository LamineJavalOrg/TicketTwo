package it.unipv.posw.model.exception;

public class WrongEmailFormatException extends Exception {
	
	public WrongEmailFormatException() {
		super("l'email deve contenere '@'");
	}

}
