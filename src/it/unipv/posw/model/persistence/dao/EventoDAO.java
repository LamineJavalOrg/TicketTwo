package it.unipv.posw.model.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unipv.posw.model.Evento;
import it.unipv.posw.model.enums.TipologiaEvento;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.dao.interfaces.IEventoDAO;

/**
 * @author rkomi-dev
 */

public class EventoDAO implements IEventoDAO {
	
	public EventoDAO() {
	}

	@Override
	public List<Evento> trovaEventiPerNome(String parziale) {
	    List<Evento> risultati = new ArrayList<>();
	    PreparedStatement ps;
	    String query = "SELECT * FROM Evento WHERE nome LIKE ?"; 
	    Connection c = null;
	
	    try {
	    	
	    	c = DBConnection.getInstance().startConnection();
	    	
	        ps = c.prepareStatement(query);
	        
	        ps.setString(1, parziale + "%"); // Cerca tutto ciò che inizia con 'parziale'
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            risultati.add(new Evento(
	                rs.getInt("id_evento"),
	                rs.getString("nome"),
	                TipologiaEvento.valueOf((rs.getString("tipologia")).toUpperCase()),
	                rs.getString("email_organizzatore"),
	                rs.getInt("id_artista")
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
