package it.unipv.posw.model.entities;

/**
 * @author rkomi-dev
 */
public class Artista {
	
	private int id_artista;
	private String nome_darte;

	public Artista(int id_artista, String nome_darte) {
		super();
		this.id_artista = id_artista;
		this.nome_darte = nome_darte;
	}
	public int getId_artista() {
		return id_artista;
	}
	public String getNome_darte() {
		return nome_darte;
	}
	public void setId_artista(int id_artista) {
		this.id_artista = id_artista;
	}
	public void setNome_darte(String nome_darte) {
		this.nome_darte = nome_darte;
	}
	
	
}
