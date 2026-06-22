package it.unipv.posw.model.entities;

import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;
import it.unipv.posw.model.exception.SettoreNonValidoException;

/**
 * @author gpelle
 */

public class Settore {
	private int id_settore;
	private int id_sede;
    private TipologiaSettore nome_settore; 
    private TipologiaPosto tipo;    
    private int capienza_max;
    private int num_file;
    private int posti_per_fila;
    private String prefisso;
    
	public Settore(int id_settore, int id_sede, TipologiaSettore nome_settore, TipologiaPosto tipo, int capienza_max,
			int num_file, int posti_per_fila, String prefisso) {
		super();
		this.id_settore = id_settore;
		this.id_sede = id_sede;
		this.nome_settore = nome_settore;
		this.tipo = tipo;
		this.capienza_max = capienza_max;
		this.num_file = num_file;
		this.posti_per_fila = posti_per_fila;
		this.prefisso = prefisso;
	}
	
	public static Settore creaNumerato(TipologiaSettore nome, String prefisso, int numFile, int postiPerFila) 
			throws SettoreNonValidoException {
		controlloComune(nome, prefisso);
		if (nome.isSoloNonNumerato()) {
			throw new SettoreNonValidoException ("Il settore " + nome + " ammette solo posti non numerati.");
		}
		if (numFile <= 0 || postiPerFila <=0) {
			throw new SettoreNonValidoException("File e colonne devono essere maggiori di 0.");
		}
		int capienza = numFile * postiPerFila;
		return new Settore(0, 0, nome, TipologiaPosto.NUMERATO, capienza, numFile, postiPerFila, prefisso.trim());
	}
	
	public static Settore creaNonNumerato(TipologiaSettore nome, String prefisso, int capienza) 
			throws SettoreNonValidoException {
		controlloComune(nome, prefisso);
		if (capienza <= 0) {
			throw new SettoreNonValidoException("La capienza deve essere maggiore di 0.");
		}
		return new Settore(0, 0, nome, TipologiaPosto.NON_NUMERATO, capienza, 0, 0, prefisso.trim());
	}
	
	public static void controlloComune(TipologiaSettore nome, String prefisso) throws SettoreNonValidoException {
		if (nome == null) {
			throw new SettoreNonValidoException ("Seleziona un tipo di settore.");
		}
		if (prefisso == null || prefisso.trim().isEmpty()) {
			throw new SettoreNonValidoException ("Il prefisso è obbligatorio.");
		}
	}
	

	public int getId_settore() {
		return id_settore;
	}

	public int getId_sede() {
		return id_sede;
	}

	public TipologiaSettore getNome_settore() {
		return nome_settore;
	}

	public TipologiaPosto getTipo() {
		return tipo;
	}

	public int getCapienza_max() {
		return capienza_max;
	}

	public int getNum_file() {
		return num_file;
	}

	public int getPosti_per_fila() {
		return posti_per_fila;
	}

	public String getPrefisso() {
		return prefisso;
	}

	public void setId_sede(int id_sede) {
		this.id_sede = id_sede;
	}

	public void setId_settore(int id_settore) {
		this.id_settore = id_settore;
	}
	
	
	public void setNome_settore(TipologiaSettore nome_settore) {
		this.nome_settore = nome_settore;
	}

	public void setTipo(TipologiaPosto tipo) {
		this.tipo = tipo;
	}

	public void setCapienza_max(int capienza_max) {
		this.capienza_max = capienza_max;
	}

	public void setNum_file(int num_file) {
		this.num_file = num_file;
	}

	public void setPosti_per_fila(int posti_per_fila) {
		this.posti_per_fila = posti_per_fila;
	}

	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    
	    sb.append(nome_settore);
	    sb.append(" (").append(prefisso).append(")");
	    sb.append(" | ");
	    sb.append(tipo.toString());
	    sb.append(" | Capienza: ").append(getCapienza_max());
	    
	    if (tipo == TipologiaPosto.NUMERATO) {
	        sb.append(" (").append(num_file).append(" file × ").append(posti_per_fila).append(")");
	    }
	    
	    return sb.toString();
	}
	
	
}
