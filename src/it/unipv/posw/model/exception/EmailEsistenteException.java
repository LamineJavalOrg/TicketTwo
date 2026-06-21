package it.unipv.posw.model.exception;

/**
 * @author rkomi-dev
 */

public class EmailEsistenteException extends Exception {
	
	public EmailEsistenteException() {
		super("Email già esistente");
	}

}
