package it.unipv.posw.model.exception;

public class DataNascitaException extends Exception {

	public DataNascitaException() {
		super("La data di nascita non può essere futura");
	}
	

}
