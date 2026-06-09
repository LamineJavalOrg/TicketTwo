package it.unipv.posw.model.persistence.dao.interfaces;

import java.util.List;

import it.unipv.posw.model.Artista;

/**
 * @author rkomi-dev
 */

public interface IArtistaDAO {
	
	List<Artista> trovaArtisti(String parziale);

}
