package it.unipv.posw.model.persistence.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unipv.posw.model.Tariffa;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.Biglietto;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.DAO.interfaces.IBigliettoDAO;

public class BigliettoDAO implements IBigliettoDAO {

	public BigliettoDAO() {}

	@Override
	public List<Biglietto> getBigliettiDisponibili(int idTappa, int idSettore, String tipo, int quantita) {
	    List<Biglietto> risultati = new ArrayList<>();
	    
	    String query = "SELECT b.*, t.id_evento, t.id_tappa, t.id_settore, t.prezzo_base, t.tipologia_biglietto " +
	                   "FROM Biglietto b " +
	                   "JOIN Tariffa t ON b.id_tariffa = t.id_tariffa " +
	                   "WHERE t.id_tappa = ? AND t.id_settore = ? AND t.tipologia_biglietto = ? " +
	                   "AND b.stato IS NULL LIMIT ?";
	    Connection c= null;
	    try {
	    	c = DBConnection.getInstance().startConnection();
	        PreparedStatement ps = c.prepareStatement(query); 
	        
	        ps.setInt(1, idTappa);
	        ps.setInt(2, idSettore);
	        ps.setString(3, tipo);
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
	    }
	    return risultati;
	}
	
}
