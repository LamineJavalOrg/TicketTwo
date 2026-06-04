package it.unipv.posw.model.persistence.DAO.interfaces;

import java.util.List;

import it.unipv.posw.model.Artista;

/**
 * @author rkomi-dev
 */

public interface IArtistaDAO {
	
	List<Artista> trovaArtisti(String parziale);

}
