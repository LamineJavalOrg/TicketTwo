package it.unipv.posw.model.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.persistence.DBConnection;
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
	
	@Override
	public Tariffa getTariffaCompleta(int id_tappa, int id_settore, TipologiaBiglietto tipo) {
		Tariffa tariffa = null;
	    String query = "SELECT * FROM Tariffa WHERE id_tappa = ? AND id_settore = ? AND tipologia_biglietto = ?";
	    Connection c = null;
	    
	    try {
	    	c = DBConnection.getInstance().startConnection();
	    	PreparedStatement ps = c.prepareStatement(query);
	    	
	    	ps.setInt(1, id_tappa);
	        ps.setInt(2, id_settore);
	        ps.setString(3, tipo.name()); 
	        
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
                return new Tariffa(
                        rs.getInt("id_evento"),
                        rs.getInt("id_settore"),
                        TipologiaBiglietto.valueOf(rs.getString("tipologia_biglietto")),
                        rs.getDouble("prezzo_base"),
                        rs.getInt("qta_max"),
                        rs.getInt("id_tappa")
                    );
                }
	        
	    } catch (SQLException e) { 
	        e.printStackTrace(); 
	    } finally {
	    	DBConnection.getInstance().closeConnection(c);
	    }	    
	    return tariffa;
	}
	
	@Override
	public List<TipologiaBiglietto> trovaTipologieTappaSettore(int idTappa, int idSettore) {
	    List<TipologiaBiglietto> tipologie = new ArrayList<>();
	    String query = "SELECT DISTINCT tipologia_biglietto FROM Tariffa WHERE id_tappa = ? AND id_settore = ?";

	    Connection c = null;
	    try {
	    	c = DBConnection.getInstance().startConnection();
	    	PreparedStatement ps = c.prepareStatement(query);
	    	
	    	ps.setInt(1, idTappa);
	        ps.setInt(2, idSettore);
	        
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	String tipoString = rs.getString("tipologia_biglietto");
                tipologie.add(TipologiaBiglietto.valueOf(tipoString));
            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBConnection.getInstance().closeConnection(c);
	    }
	    return tipologie;
	}
}
