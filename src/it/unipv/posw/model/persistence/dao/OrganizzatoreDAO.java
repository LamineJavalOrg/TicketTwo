package it.unipv.posw.model.persistence.dao;

import java.sql.Connection; 
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipv.posw.model.entities.Organizzatore;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.dao.interfaces.IOrganizzatoreDAO;

/**
 * @author gpelle
 */
public class OrganizzatoreDAO implements IOrganizzatoreDAO {
	
	public OrganizzatoreDAO() {
		super();
	}
	

	@Override
	public boolean salvaOrganizzatore(Organizzatore org) {
		PreparedStatement ps;
       
		String query = "INSERT INTO Utente (nome, cognome, email, password, data_nascita, nome_organizzazione, tipo_utente) VALUES (?,?,?,?,?,?,?)";
        Connection c = null;
		try {
            c = DBConnection.getInstance().startConnection();
            ps = c.prepareStatement(query);
            
            ps.setString(1, org.getNome());
            ps.setString(2, org.getCognome());
            ps.setString(3, org.getEmail());
            ps.setString(4, org.getPassword());
            ps.setDate(5, Date.valueOf(org.getData_nascita()));
            ps.setString(6, org.getNome_organizzazione());
            ps.setString(7, "ORGANIZZATORE");
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) { 
        	e.printStackTrace();
        }
        finally { 
        	DBConnection.getInstance().closeConnection(c); 
        }
        return false;
	}
	
    @Override
    public Organizzatore trovaOrganizzatorePerEmail(String email) {
    	PreparedStatement ps;
        ResultSet rs;
        Organizzatore org = null;
        
        String query = "SELECT * FROM Utente WHERE email = ? AND nome_organizzazione IS NOT NULL";
        Connection c = null;
        try {
            c = DBConnection.getInstance().startConnection();
            ps = c.prepareStatement(query);
            
            ps.setString(1, email);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                org = new Organizzatore(
                    rs.getString("nome"), 
                    rs.getString("cognome"),
                    rs.getDate("data_nascita").toLocalDate(),
                    rs.getString("email"), 
                    rs.getString("password"),
                    rs.getString("nome_organizzazione")
                );
            }
        } catch (SQLException e) {
        	e.printStackTrace(); 
        }
        finally {
        	DBConnection.getInstance().closeConnection(c); 
        }
        return org;
    }
    
    @Override
    public boolean isEmailEsistente(String email) {
    	PreparedStatement ps;
    	
        String query = "SELECT COUNT(*) FROM Utente WHERE email = ?";
        Connection c = null;
        try {
            c = DBConnection.getInstance().startConnection();
            ps = c.prepareStatement(query);
            ps.setString(1, email);
            
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
    
    @Override
    public boolean deleteOrganizzatore(String email) {
    	PreparedStatement ps;
        String query = "DELETE FROM Utente WHERE email = ?";
        Connection c = null;
        try {
            c = DBConnection.getInstance().startConnection();
            ps = c.prepareStatement(query);
            ps.setString(1, email);
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.getInstance().closeConnection(c);
        }
        return false;
    }

}
