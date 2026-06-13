package it.unipv.posw.model.exception;

public class DataPassataException extends Exception {

	public DataPassataException() {
		super("La data della tappa deve essere futura");
	}
}
