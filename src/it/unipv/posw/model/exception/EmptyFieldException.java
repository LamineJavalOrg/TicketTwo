package it.unipv.posw.model.exception;

/**
 * @author rkomi-dev
 */

public class EmptyFieldException extends Exception {
	
	public EmptyFieldException() {
		super("Uno o più campi vuoti");
	}

}
