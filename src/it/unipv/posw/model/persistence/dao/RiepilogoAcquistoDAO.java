package it.unipv.posw.model.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unipv.posw.model.entities.RiepilogoAcquisto;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.dao.interfaces.IRiepilogoAcquistoDAO;

/** 
 * @author rkomi-dev
 */

public class RiepilogoAcquistoDAO implements IRiepilogoAcquistoDAO {

	@Override
	public List<RiepilogoAcquisto> getBigliettiAcquistati(String email) {
		
		List<RiepilogoAcquisto> risultati = new ArrayList<>();
		PreparedStatement ps;
		String query = "SELECT e.nome AS nome_evento, " +
                "ta.data_ora AS data_ora_evento, " +
                "b.nominativo, " +
                "b.prezzo_acquisto, " +
                "b.id_biglietto " +
                "FROM Biglietto b " +
                "JOIN Tariffa t ON b.id_tariffa = t.id_tariffa " +
                "JOIN Evento e ON t.id_evento = e.id_evento " +
                "JOIN Tappa ta ON ta.id_tappa = t.id_tappa " +
                "WHERE b.email_cliente = ? AND b.stato = 'acquistato'";
		Connection c = null;
		try {
			c = DBConnection.getInstance().startConnection();
			ps = c.prepareStatement(query);
			
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
	            risultati.add(new RiepilogoAcquisto(
	            	rs.getInt("id_biglietto"),
	                rs.getString("nome_evento"),
	                rs.getTimestamp("data_ora_evento").toLocalDateTime(),
	                rs.getString("nominativo"),
	                rs.getDouble("prezzo_acquisto")
	            ));
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return risultati;
	}

}
