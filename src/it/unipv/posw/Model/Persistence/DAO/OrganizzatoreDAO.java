package it.unipv.posw.Model.Persistence.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.unipv.posw.Model.Organizzatore;
import it.unipv.posw.Model.Persistence.DBConnection;
import it.unipv.posw.Model.Persistence.DAO.Interface.IOrganizzatoreDAO;

/**
 * @author gpelle
 */
public class OrganizzatoreDAO implements IOrganizzatoreDAO {
	private Connection c;
	
	public OrganizzatoreDAO() {
		super();
	}
	

	@Override
	public boolean salvaOrganizzatore(Organizzatore org) {
		String query = "INSERT INTO Utente (nome, cognome, email, password, data_nascita, nome_organizzazione) VALUES (?,?,?,?,?,?)";
        try {
            c = DBConnection.getInstance().startConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setString(1, org.getNome());
            ps.setString(2, org.getCognome());
            ps.setString(3, org.getEmail());
            ps.setString(4, org.getPassword());
            ps.setDate(5, Date.valueOf(org.getData_nascita()));
            ps.setString(6, org.getNome_organizzazione());
            
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

}
