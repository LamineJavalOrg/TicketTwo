package it.unipv.posw.model.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.exception.PostiInsufficientiException;
import it.unipv.posw.model.persistence.dao.interfaces.ITariffaDAO;

/**
 * @author gpelle
 */

public class TariffaDAO implements ITariffaDAO {
	
	// percorso transazionale
	@Override
	public void inserisciTariffa(List<Tariffa> tariffe, int idTappa, int idEvento, Connection c,
			Map<Integer, List<Integer>> postiPerSettore) throws SQLException {

		String queryTariffa = "INSERT INTO Tariffa (id_evento, id_tappa, tipologia_biglietto, prezzo_base, id_settore, qta_max) VALUES (?,?,?,?,?,?)";
		String queryBigliettoNumerato = "INSERT INTO Biglietto (id_tariffa, id_posto) VALUES (?,?)";
		String queryBigliettoNonNumerato = "INSERT INTO Biglietto (id_tariffa) VALUES (?)";

		PreparedStatement psTariffa = c.prepareStatement(queryTariffa, Statement.RETURN_GENERATED_KEYS);

		for (Tariffa t : tariffe) {
			psTariffa.setInt(1, idEvento);
			psTariffa.setInt(2, idTappa);
			psTariffa.setString(3, t.getTipob().name());
			psTariffa.setDouble(4, t.getPrezzo());
			psTariffa.setInt(5, t.getId_settore());
			psTariffa.setInt(6, t.getQuantita_massima());
			psTariffa.executeUpdate();

			int idTariffa;
			ResultSet rs = psTariffa.getGeneratedKeys();
			if (rs.next()) {
				idTariffa = rs.getInt(1);
			} else {
				throw new SQLException("Inserimento fallito: ID tariffa non generato dal DB.");
			}

			// generazione dei biglietti fisici
			List<Integer> posti;
			if (postiPerSettore != null) {
			    posti = postiPerSettore.get(t.getId_settore());
			} else {
			    posti = null;
			}

			if (posti != null && !posti.isEmpty()) {
				// settore numerato: ogni biglietto riceve un posto
				if (posti.size() < t.getQuantita_massima()) {
					throw new SQLException("Posti insufficienti per il settore " + t.getId_settore());
				}
				PreparedStatement psBiglietto = c.prepareStatement(queryBigliettoNumerato);
				for (int i = 0; i < t.getQuantita_massima(); i++) {
					psBiglietto.setInt(1, idTariffa);
					psBiglietto.setInt(2, posti.get(i));
					psBiglietto.addBatch();
				}
				psBiglietto.executeBatch();
			} else {
				// settore non numerato: id_posto resta NULL
				PreparedStatement psBiglietto = c.prepareStatement(queryBigliettoNonNumerato);
				for (int i = 0; i < t.getQuantita_massima(); i++) {
					psBiglietto.setInt(1, idTariffa);
					psBiglietto.addBatch();
				}
				psBiglietto.executeBatch();
			}
		}
	}

}
