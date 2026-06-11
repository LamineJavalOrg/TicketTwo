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
}
