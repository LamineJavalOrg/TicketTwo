package it.unipv.posw.model.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.dao.interfaces.ISettoreDAO;

/**
 * @author gpelle
 */

public class SettoreDAO implements ISettoreDAO {
	
	// percorso transazionale
	@Override
	public Settore salvaSettore(Settore settore, Connection c) throws SQLException {
		String query = "INSERT INTO Settore (id_sede, nome_settore, tipo_posti, capienza_max, "
				+ "num_file, posti_per_fila, prefisso) VALUES (?,?,?,?,?,?,?)";
		
		PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
		ps.setInt(1, settore.getId_sede());
		ps.setString(2, settore.getNome_settore().name());
		ps.setString(3, settore.getTipo().name());
		ps.setInt(4, settore.getCapienza_max());
		ps.setInt(5, settore.getNum_file());
		ps.setInt(6, settore.getPosti_per_fila());
		ps.setString(7, settore.getPrefisso());
			
		ps.executeUpdate();
			
		ResultSet rs = ps.getGeneratedKeys();
           	if(rs.next()) {
           		settore.setId_settore(rs.getInt(1));
           	}
           	return settore;
		}
	
	
	@Override
	public List<Settore> getSettoriDaSede(int idSede) {
		List<Settore> settori = new ArrayList<>();
		String query = "SELECT * FROM Settore WHERE id_sede = ?";
		
		Connection c = null;
		try {
			c = DBConnection.getInstance().startConnection();
			PreparedStatement ps = c.prepareStatement(query);
			
			ps.setInt(1, idSede);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				TipologiaPosto tipoPosto = TipologiaPosto.valueOf(rs.getString("tipo_posti").toUpperCase());
				TipologiaSettore nomeSettore = TipologiaSettore.valueOf(rs.getString("nome_settore").toUpperCase());
			
				settori.add(new Settore(
						rs.getInt("id_settore"),
						rs.getInt("id_sede"),
						nomeSettore,
						tipoPosto,
						rs.getInt("capienza_max"),
						rs.getInt("num_file"),
						rs.getInt("posti_per_fila"),
						rs.getString("prefisso")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.getInstance().closeConnection(c);
		}
		return settori;
	}
	
	
}          	
        

