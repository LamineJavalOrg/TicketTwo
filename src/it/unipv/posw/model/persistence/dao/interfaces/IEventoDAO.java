package it.unipv.posw.model.persistence.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import it.unipv.posw.model.entities.Evento;

/**
 * @author rkomi-dev
 */

public interface IEventoDAO {
	
	public List<Evento> trovaEventiPerNome(String parziale);
	List<Evento> trovaEventiPerArtista(String parziale);
	int salvaEvento(Evento evento, Connection c) throws SQLException;

}
