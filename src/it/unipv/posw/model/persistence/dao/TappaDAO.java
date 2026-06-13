package it.unipv.posw.model.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.dao.interfaces.ITappaDAO;

/**
 * @author gpelle
 */ 

public class TappaDAO implements ITappaDAO {
	
	@Override
	public int contaTappePerSede(int idSede) {
		String query = "SELECT COUNT(*) FROM Tappa WHERE id_sede = ?";
		
		Connection c = null;
		try {
			c = DBConnection.getInstance().startConnection();
			PreparedStatement ps = c.prepareStatement(query);
			
			ps.setInt(1, idSede);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.getInstance().closeConnection(c);
		}
		return 0;
	}
	
	// percorso transazionale
	@Override
	public int salvaTappa(Tappa tappa, int idEvento, Connection c) throws SQLException {
		String query = "INSERT INTO Tappa (id_evento, id_sede, data_ora) VALUES (?,?,?)";

		PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		ps.setInt(1, idEvento);
		ps.setInt(2, tappa.getId_sede());
		ps.setTimestamp(3, Timestamp.valueOf(tappa.getData_ora()));

		ps.executeUpdate();

		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			return rs.getInt(1);
		}
		throw new SQLException("ID tappa non generato.");
	}

}
