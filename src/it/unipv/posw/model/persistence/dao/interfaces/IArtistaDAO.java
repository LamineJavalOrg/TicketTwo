package it.unipv.posw.model.persistence.dao.interfaces;

import java.util.List;

import it.unipv.posw.model.entities.Artista;

/**
 * @author rkomi-dev
 */

public interface IArtistaDAO {
	
	List<Artista> trovaArtisti(String parziale);
	int salvaArtista(Artista artista);

}
