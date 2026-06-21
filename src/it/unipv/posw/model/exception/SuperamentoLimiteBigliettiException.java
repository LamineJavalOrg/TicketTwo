package it.unipv.posw.model.exception;

/**
 * @author rkomi-dev
 */

public class SuperamentoLimiteBigliettiException extends Exception {
	
	public SuperamentoLimiteBigliettiException() {
		super("Limite superato: max 5 biglietti per evento");
	} 

}
