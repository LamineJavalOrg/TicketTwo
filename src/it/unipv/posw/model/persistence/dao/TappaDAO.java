package it.unipv.posw.model.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
	
	@Override
	public List<Tappa> trovaTappePerEvento(int idEvento) {
		String query = "SELECT t.id_tappa, t.id_evento, t.id_sede, s.nome, t.data_ora "
				+ "FROM Tappa t JOIN Sede s ON t.id_sede = s.id_sede "
				+ "WHERE t.id_evento = ? ORDER BY t.data_ora";
		
		List<Tappa> risultati = new ArrayList<Tappa>();
		
		Connection c = null;
		try {
			c = DBConnection.getInstance().startConnection();
			PreparedStatement ps = c.prepareStatement(query);
			
			ps.setInt(1, idEvento);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int idTappa = rs.getInt("id_tappa");
				int idEv = rs.getInt("id_evento");
				int idSede = rs.getInt("id_sede");
				String nomeSede = rs.getString("nome");
				Timestamp ts = rs.getTimestamp("data_ora");
				
				Tappa t = new Tappa(idTappa, idEv, idSede, nomeSede, ts.toLocalDateTime());
				risultati.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.getInstance().closeConnection(c);
		}
		return risultati;
	}

}
