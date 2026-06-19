package it.unipv.posw.model.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;
import it.unipv.posw.model.persistence.dao.interfaces.ISedeDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ISettoreDAO;

/**
 * @author gpelle
 */
public class SedeDAO implements ISedeDAO {
    
    
    @Override
    public boolean isSedeEsistente(String nome, String indirizzo) {
    	String query = "SELECT COUNT(*) FROM Sede WHERE nome = ? AND indirizzo = ?";
        Connection c = null;
        
        try {
            c = DBConnection.getInstance().startConnection();            
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setString(1, nome); 
            ps.setString(2, indirizzo);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { 
        	e.printStackTrace(); 
        }
        finally { 
        	DBConnection.getInstance().closeConnection(c); 
        }
        return false;
    }

    
	// percorso transazionale
	@Override
    public Sede salvaSede(Sede sede, Connection c) throws SQLException {
	    String query = "INSERT INTO Sede (nome, indirizzo, email_organizzatore) VALUES (?,?,?)";
	    
	    PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        
	    ps.setString(1, sede.getNome()); 
	    ps.setString(2, sede.getIndirizzo());
		ps.setString(3, sede.getEmail_organizzatore());
	        
	    ps.executeUpdate();
	        
		ResultSet rs = ps.getGeneratedKeys();
		
		if (rs.next()) {
			sede.setId_sede(rs.getInt(1));
		}
		return sede;
		}
		
	
	@Override
	public boolean eliminaSede(int idSede) {
		String query = "DELETE FROM Sede WHERE id_sede = ?";
		Connection c = null;
		
		try {
			c = DBConnection.getInstance().startConnection();
			PreparedStatement ps = c.prepareStatement(query);
			
			ps.setInt(1, idSede);
			
			return ps.executeUpdate() > 0;   // Cascade elimina settori e posti
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.getInstance().closeConnection(c);
		}
		return false;
	}
    
    
	@Override
	public List<Sede> getTutteLeSedi() {
		List<Sede> sedi = new ArrayList<>();
		ISettoreDAO settoreDAO = MYSQLDAOFactory.getInstance().getSettoreDAO();
		String query = "SELECT * FROM Sede";
		Connection c = null;
		try {
			c = DBConnection.getInstance().startConnection();
			PreparedStatement ps = c.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int idSede = rs.getInt("id_sede");
				List<Settore> settori = settoreDAO.getSettoriDaSede(idSede);
				sedi.add(new Sede(
						idSede,
						rs.getString("nome"),
						rs.getString("indirizzo"),
						rs.getString("email_organizzatore"),
						settori));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.getInstance().closeConnection(c);
		}
		return sedi;
	}
	
	@Override
	public List<Sede> getSediPerOrganizzatore(String email) {
		List<Sede> sedi = new ArrayList<>();
		ISettoreDAO settoreDAO = MYSQLDAOFactory.getInstance().getSettoreDAO();
		String query = "SELECT * FROM Sede WHERE email_organizzatore = ?";
		Connection c = null;
		try {
			c = DBConnection.getInstance().startConnection();
			PreparedStatement ps = c.prepareStatement(query);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int idSede = rs.getInt("id_sede");
				List<Settore> settori = settoreDAO.getSettoriDaSede(idSede);
				sedi.add(new Sede(
						idSede,
						rs.getString("nome"),
						rs.getString("indirizzo"),
						rs.getString("email_organizzatore"),
						settori));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.getInstance().closeConnection(c);
		}
		return sedi;
	}
}
