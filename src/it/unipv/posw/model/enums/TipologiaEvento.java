package it.unipv.posw.model.enums;

/**
 * @author gpelle
 */
public enum TipologiaEvento {
	CONCERTO, TEATRO;
	
	@Override
	public String toString() {
		String nome = this.name().toLowerCase();
		return nome.substring(0, 1).toUpperCase() + nome.substring(1);		
	}
}
