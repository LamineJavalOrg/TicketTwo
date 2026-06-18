package it.unipv.posw.model.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unipv.posw.model.entities.Biglietto;
import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.dao.interfaces.IBigliettoDAO;

/** 
 * @author rkomi-dev
 * @author gpelle
 */

public class BigliettoDAO implements IBigliettoDAO {

	public BigliettoDAO() {}

	@Override
	public List<Biglietto> getBigliettiDisponibili(int idTappa, int idSettore, TipologiaBiglietto tipo, int quantita) {
	    List<Biglietto> risultati = new ArrayList<>();
	    
	    String query = "SELECT b.*, t.id_evento, t.id_tappa, t.id_settore, t.prezzo_base, t.tipologia_biglietto " +
	                   "FROM Biglietto b " +
	                   "JOIN Tariffa t ON b.id_tariffa = t.id_tariffa " +
	                   "WHERE t.id_tappa = ? AND t.id_settore = ? AND t.tipologia_biglietto = ? " +
	                   "AND b.stato = 'disponibile' LIMIT ?";
	    Connection c= null;
	    try {
	    	c = DBConnection.getInstance().startConnection();
	        PreparedStatement ps = c.prepareStatement(query); 
	        
	        ps.setInt(1, idTappa);
	        ps.setInt(2, idSettore);
	        ps.setString(3, tipo.name());
	        ps.setInt(4, quantita);

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                
	                Tariffa t = new Tariffa(
	                    rs.getInt("id_evento"),
	                    rs.getInt("id_settore"),            
	                    TipologiaBiglietto.valueOf(rs.getString("tipologia_biglietto")),
	                    rs.getDouble("prezzo_base"),
	                    0, // qtaMax non serve qui
	                    rs.getInt("id_tappa")
	                );

	                risultati.add(new Biglietto(
	                    rs.getInt("id_biglietto"),
	                    (Integer) rs.getObject("id_posto"), 
	                    rs.getString("email_cliente"),
	                    rs.getString("nominativo"),
	                    rs.getString("stato"),
	                    rs.getString("qr_code"),
	                    t,
	                    t.getPrezzo()	                    
	                ));
	            }
	        }
	    } catch(SQLException e) {
	        e.printStackTrace();
	    } finally {
			DBConnection.getInstance().closeConnection(c);
		}
	    return risultati;
	    
	}

	@Override
	public void updatePostAcquisto(int id_biglietto, String email, String nominativo, String qr, double prezzoAcquisto) {
		
		PreparedStatement ps;
		String query = "UPDATE Biglietto SET email_cliente = ?, nominativo = ?, qr_code = ?, stato = 'acquistato', prezzo_acquisto = ? WHERE id_biglietto = ?";
		Connection c = null;
		try {
			c = DBConnection.getInstance().startConnection();
			ps = c.prepareStatement(query); 
			
			ps.setString(1, email);
	        ps.setString(2, nominativo);
	        ps.setString(3, qr);
	        ps.setDouble(4, prezzoAcquisto);
	        ps.setInt(5, id_biglietto);
	        
	        ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnection.getInstance().closeConnection(c);
		}
	}
	
	@Override
	public int countPostiLiberi(int idTappa, int idSettore, String tipo) { 
	    String query = "SELECT COUNT(*) AS rimasti " +
	                   "FROM Biglietto b " +
	                   "JOIN Tariffa t ON b.id_tariffa = t.id_tariffa " +
	                   "WHERE t.id_tappa = ? AND t.id_settore = ? AND t.tipologia_biglietto = ? AND b.stato = 'DISPONIBILE'";
	    int totale = 0;
	    Connection c = null;
	    try {
	        c = DBConnection.getInstance().startConnection();
	        PreparedStatement ps = c.prepareStatement(query);
	        
	        ps.setInt(1, idTappa);
	        ps.setInt(2, idSettore);
	        ps.setString(3, tipo);
	        
	        ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                totale = rs.getInt("rimasti");
	            }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBConnection.getInstance().closeConnection(c);
	    }
	    return totale;
	}

	@Override
	public void cambiaNominativo(int id_biglietto, String nominativo) {
		PreparedStatement ps;
		String query = "UPDATE Biglietto SET nominativo = ? WHERE id_biglietto = ?";
		Connection c = null;
		try {
			c = DBConnection.getInstance().startConnection();
			ps = c.prepareStatement(query); 
			
			ps.setString(1, nominativo);
	        ps.setInt(2, id_biglietto);
	        
	        ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnection.getInstance().closeConnection(c);
		}
		
	}
	
}
