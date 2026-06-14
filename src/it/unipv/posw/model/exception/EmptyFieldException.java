package it.unipv.posw.model.exception;

public class EmptyFieldException extends Exception {
	
	public EmptyFieldException() {
		super("Uno o più campi vuoti");
	}

}
