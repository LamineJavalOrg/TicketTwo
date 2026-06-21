package it.unipv.posw.model.exception;

/**
 * @author rkomi-dev
 */

public class WrongEmailFormatException extends Exception {
	
	public WrongEmailFormatException() {
		super("l'email deve contenere '@'");
	}

}
