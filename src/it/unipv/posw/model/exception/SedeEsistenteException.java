package it.unipv.posw.model.exception;

public class SedeEsistenteException extends SedeException {

	public SedeEsistenteException() {
		super("Sede già esistente con questo nome e indirizzo");
	}
	

}
