package it.unipv.posw.model.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unipv.posw.model.entities.Artista;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.dao.interfaces.IArtistaDAO;

/**
 * @author rkomi-dev
 */

public class ArtistaDAO implements IArtistaDAO {

	
	public ArtistaDAO() {
	}


	@Override
	public List<Artista> trovaArtisti(String parziale) {
		List<Artista> risultati = new ArrayList<>();
		PreparedStatement ps;
	    String query = "SELECT * FROM Artista WHERE nome_darte LIKE ?";
	    Connection c = null;
	    try {
	        c = DBConnection.getInstance().startConnection();
	        ps = c.prepareStatement(query);
	        ps.setString(1, parziale + "%");
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            risultati.add(new Artista(
	                rs.getInt("id_artista"),
	                rs.getString("nome_darte")              
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBConnection.getInstance().closeConnection(c);
	    }
	    return risultati;
	}

}
