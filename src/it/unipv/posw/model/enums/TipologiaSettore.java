package it.unipv.posw.model.enums;

/**
 * @author gpelle
 */
public enum TipologiaSettore {
	PLATEA(false), 
	PARTERRE(true), 
	TRIBUNA(false),
	CURVA(false);

	
	private final boolean soloNonNumerato;
	
	TipologiaSettore(boolean soloNonNumerato) {
		this.soloNonNumerato = soloNonNumerato;
	}
	
	public boolean isSoloNonNumerato() {
		return soloNonNumerato;
	}
	
	@Override
	public String toString() {
		String nome = name().toLowerCase();
		return nome.substring(0, 1).toUpperCase() + nome.substring(1);
	}
}
