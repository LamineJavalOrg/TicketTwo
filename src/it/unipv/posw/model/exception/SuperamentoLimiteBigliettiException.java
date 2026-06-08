package it.unipv.posw.model.exception;

public class SuperamentoLimiteBigliettiException extends Exception {
	
	public SuperamentoLimiteBigliettiException() {
		super("Limite superato: max 4 biglietti per concerto");
	} 

}
