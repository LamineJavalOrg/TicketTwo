package it.unipv.posw.model.exception;

public class CredenzialiErrateException extends Exception {
	
	public CredenzialiErrateException() {
		super("email e/o password errata/e");
	}
}
