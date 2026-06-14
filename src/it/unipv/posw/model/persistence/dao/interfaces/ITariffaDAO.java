package it.unipv.posw.model.persistence.dao.interfaces;

import java.sql.Connection; 
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import it.unipv.posw.model.entities.Tariffa;

/**
 * @author gpelle
 */

public interface ITariffaDAO {

	void inserisciTariffa(List<Tariffa> tariffe, int idTappa, int idEvento, Connection c,
			Map<Integer, List<Integer>> postiPerSettore) throws SQLException;

}
