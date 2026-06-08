package it.unipv.posw.model.exception;

public class EmailEsistenteException extends Exception {
	
	public EmailEsistenteException() {
		super("Email già esistente");
	}

}
