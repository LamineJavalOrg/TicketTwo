package it.unipv.posw.model.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import it.unipv.posw.model.Settore;
import it.unipv.posw.model.persistence.dao.interfaces.IPostoDAO;

/**
 * @author gpelle
 */

public class PostoDAO implements IPostoDAO {
	
	// percorso transazionale: connessione iniettata
	@Override
	public void salvaPostiPerSettore(Settore settore, Connection c) throws SQLException {
		String query = "INSERT INTO Posto (id_settore, fila, colonna, prefisso) VALUES (?,?,?,?)";
		
		PreparedStatement ps = c.prepareStatement(query);
		
		for (int f = 1; f <= settore.getNum_file(); f++) {
			for (int col = 1; col <= settore.getPosti_per_fila(); col++) {
				ps.setInt(1, settore.getId_settore());
				ps.setInt(2, f);
				ps.setInt(3, col);
				ps.setString(4, settore.getPrefisso());
				ps.addBatch();
			}
		}
		ps.executeBatch();
	}
}

