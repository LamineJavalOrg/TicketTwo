package it.unipv.posw.model.exception;

public class SedeSenzaSettoriException extends Exception {
	 
	public SedeSenzaSettoriException() {
		super("La configurazione deve includere almeno un settore.");
	}
}
	 