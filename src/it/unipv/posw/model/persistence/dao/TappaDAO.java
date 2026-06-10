package it.unipv.posw.model.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.dao.interfaces.ITappaDAO;

/**
 * @author gpelle
 */ 

public class TappaDAO implements ITappaDAO{
	
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

}
