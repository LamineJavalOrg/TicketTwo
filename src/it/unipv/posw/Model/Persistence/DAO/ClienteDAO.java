package it.unipv.posw.Model.Persistence.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipv.posw.Model.Cliente;
import it.unipv.posw.Model.Persistence.DBConnection;
import it.unipv.posw.Model.Persistence.DAO.Interface.IClienteDAO;

/**
 * @author rkomi-dev
 */

public class ClienteDAO implements IClienteDAO {
	
	private Connection c;
	
	public ClienteDAO() {
		super();
	}

	@Override
	public boolean salvaCliente(Cliente cliente) {
		PreparedStatement ps;
        String query = "INSERT INTO Utente (nome, cognome, email, password, data_nascita) VALUES (?, ?, ?, ?, ?)";

        try {
        	
            c = DBConnection.getInstance().startConnection();

            ps = c.prepareStatement(query);
    
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCognome());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getPassword());
            ps.setDate(5, Date.valueOf(cliente.getData_nascita()));

            int result = ps.executeUpdate();
            return result > 0; // Ritorna true se l'inserimento è riuscito

        } catch (SQLException e) {
            System.err.println("Errore durante la registrazione: " + e.getMessage());

        } finally {
            DBConnection.getInstance().closeConnection(c);
        }
        return false;
	}
	
	@Override
    public Cliente trovaClientePerEmail(String email) {
        PreparedStatement ps;
        ResultSet rs;
        Cliente cliente = null;

        String query = "SELECT * FROM Utente WHERE email = ?";

        try {
            c = DBConnection.getInstance().startConnection();
            ps = c.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
              
                cliente = new Cliente(
                    
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getDate("data_nascita").toLocalDate(),
                    rs.getString("email"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.getInstance().closeConnection(c);
        }
        return cliente;
    }

}
