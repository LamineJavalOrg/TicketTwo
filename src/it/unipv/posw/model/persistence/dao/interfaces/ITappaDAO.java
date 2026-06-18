package it.unipv.posw.model.persistence.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import it.unipv.posw.model.entities.Tappa;

/**
 * @author gpelle
 */

public interface ITappaDAO {

	int contaTappePerSede(int idSede);
	int salvaTappa(Tappa tappa, int idEvento, Connection c) throws SQLException;
	List<Tappa> trovaTappePerEvento(int idEvento);

}
