package it.unipv.posw.model.exception;

public class SedeEsistenteException extends Exception {

	public SedeEsistenteException() {
		super("Sede già esistente con questo nome e indirizzo");
	}
	

}
