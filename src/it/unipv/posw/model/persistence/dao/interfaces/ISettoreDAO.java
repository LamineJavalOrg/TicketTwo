package it.unipv.posw.model.persistence.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import it.unipv.posw.model.entities.Settore;

/**
 * @author gpelle
 */

public interface ISettoreDAO {

	Settore salvaSettore(Settore settore, Connection c) throws SQLException;
	List<Settore> getSettoriDaSede(int idSede);

}
