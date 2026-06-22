package it.unipv.posw.model.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.enums.TipologiaEvento;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.dao.interfaces.IEventoDAO;

/**
 * @author rkomi-dev
 * @author gpelle
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
	
	@Override
	public List<Evento> trovaEventiPerArtista(String parziale) {
		List<Evento> risultati = new ArrayList<>();
		PreparedStatement ps;
	    String query = "SELECT * FROM Evento JOIN Artista ON Evento.id_artista = Artista.id_artista WHERE nome_darte LIKE ?"; // Usiamo LIKE
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
	
	// percorso transazionale
	@Override
	public int salvaEvento(Evento evento, Connection c) throws SQLException {
		String query = "INSERT INTO Evento (nome, tipologia, email_organizzatore, id_artista) VALUES (?,?,?,?)";
		PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		ps.setString(1, evento.getNome());
		ps.setString(2, evento.getTipo().name());
		ps.setString(3, evento.getEmail_organizzatore());
		
		if (evento.getId_artista() > 0) {
			ps.setInt(4, evento.getId_artista());
		} else {
			ps.setNull(4, Types.INTEGER);
		}
			ps.executeUpdate();

		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			return rs.getInt(1);
		}
		throw new SQLException("ID evento non generato.");
	}
	
	
	@Override
	public boolean eliminaEvento(int idEvento) {
		PreparedStatement ps;
		String query = "DELETE FROM Evento WHERE id_evento = ?";
		Connection c = null;
		
		try {
			c = DBConnection.getInstance().startConnection();
			ps = c.prepareStatement(query);
			
			ps.setInt(1, idEvento);
			
			int result = ps.executeUpdate();
			return result > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.getInstance().closeConnection(c);
		}
		
		return false;
	}
	
	@Override
	public List<Evento> trovaEventiPerOrganizzatore(String email) {
		List<Evento> risultati = new ArrayList<>();
		String query = "SELECT * FROM Evento WHERE email_organizzatore = ?"; 
		Connection c = null;
		
		try {
			c = DBConnection.getInstance().startConnection();
			PreparedStatement ps = c.prepareStatement(query);
			
			ps.setString(1, email);
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
