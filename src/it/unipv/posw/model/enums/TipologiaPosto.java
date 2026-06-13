package it.unipv.posw.model.enums;

/**
 * @author gpelle
 */
public enum TipologiaPosto {
	NUMERATO, NON_NUMERATO;
	
	@Override
	public String toString() {
		String nome = name().replace("_", " ").toLowerCase();
		return nome.substring(0, 1).toUpperCase() + nome.substring(1);
	}
}
