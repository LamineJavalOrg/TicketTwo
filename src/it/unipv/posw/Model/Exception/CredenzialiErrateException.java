package it.unipv.posw.Model.Exception;

public class CredenzialiErrateException extends Exception {
	
	public CredenzialiErrateException() {
		super("email e/o password errata/e");
	}
}
