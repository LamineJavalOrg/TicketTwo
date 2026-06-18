package it.unipv.posw.model.exception;

public class DataTappaDuplicataException extends Exception {

	public DataTappaDuplicataException() {
		super("Esiste già una tappa nella stessa data per questo evento");
	}
}