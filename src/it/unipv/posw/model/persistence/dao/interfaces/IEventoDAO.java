package it.unipv.posw.model.persistence.dao.interfaces;

import java.util.List;

import it.unipv.posw.model.entities.Evento;

/**
 * @author rkomi-dev
 */

public interface IEventoDAO {
	
	public List<Evento> trovaEventiPerNome(String parziale);

}
