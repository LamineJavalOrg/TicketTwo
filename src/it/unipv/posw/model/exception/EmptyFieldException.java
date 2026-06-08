package it.unipv.posw.model.exception;

public class EmptyFieldException extends Exception {
	
	public EmptyFieldException() {
		super("uno o più campi vuoti");
	}

}
