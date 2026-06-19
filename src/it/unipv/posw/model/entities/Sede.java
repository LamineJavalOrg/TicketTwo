package it.unipv.posw.model.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gpelle
 */

public class Sede {
	private int id_sede;
    private String nome;
    private String indirizzo;
    private String email_organizzatore;
    private List<Settore> settori = new ArrayList<>();
    
    
	public Sede(int id_sede, String nome, String indirizzo, String email_organizzatore, List<Settore> settori) {
		super();
		this.id_sede = id_sede;
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.email_organizzatore = email_organizzatore;
		this.settori = settori;
	}

	// Costruttore vuoto per nuova sede in configurazione
	public Sede() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	
	public String getEmail_organizzatore() {
		return email_organizzatore;
	}

	public void setEmail_organizzatore(String email_organizzatore) {
		this.email_organizzatore = email_organizzatore;
	}

	public int getId_sede() {
		return id_sede;
	}

	public void setId_sede(int id_sede) {
		this.id_sede = id_sede;
	}
	
	public List<Settore> getSettori() {
		if (settori == null) {
	        return new ArrayList<>();
	    }
		return settori;
	}
	
	public void svuotaSettori() {
		settori.clear();
	}

	public void aggiungiSettore(Settore settore) {
		if (contienePrefisso(settore.getPrefisso())) {
			throw new IllegalArgumentException("Esiste già un settore con il prefisso " + settore.getPrefisso() + ". Usane uno diverso.");
		}
		settori.add(settore);
	}
	
	public boolean contienePrefisso(String prefisso) {
		for (Settore s : settori) {
			if (s.getPrefisso().equalsIgnoreCase(prefisso)) {
				return true;
			}
		}
		return false;
	}
	
	public int getCapienzaTotale() {
		int totale = 0;
		for (Settore s : settori) {
			totale += s.getCapienza_max();
		}
		return totale;
	}
	
	public boolean possiedeSettori() {
		return settori != null && !settori.isEmpty();
	}
	
	@Override
	public String toString() {
	    return nome + " - " + indirizzo;
	}
}
