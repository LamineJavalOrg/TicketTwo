package it.unipv.posw.model.persistence.dao.interfaces;

import java.sql.Connection; 
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.enums.TipologiaBiglietto;

/**
 * @author gpelle
 */

public interface ITariffaDAO {

	void inserisciTariffa(List<Tariffa> tariffe, int idTappa, int idEvento, Connection c,
			Map<Integer, List<Integer>> postiPerSettore) throws SQLException;
	Tariffa getTariffaCompleta(int id_tappa, int id_settore, TipologiaBiglietto tipo);
	List<TipologiaBiglietto> trovaTipologieTappaSettore(int idTappa, int idSettore);

}
