package it.unipv.posw.model.persistence.DAO.interfaces;

import java.util.List;

import it.unipv.posw.model.Evento;

/**
 * @author rkomi-dev
 */

public interface IEventoDAO {
	
	public List<Evento> trovaEventiPerNome(String parziale);

}
