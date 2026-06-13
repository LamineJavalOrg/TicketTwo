package it.unipv.posw.model.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.persistence.dao.interfaces.IPostoDAO;

/**
 * @author gpelle
 */

public class PostoDAO implements IPostoDAO {
	
	// percorso transazionale
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
	
	// percorso transazionale
	@Override
	public List<Integer> getIdPostiPerSettore(int idSettore, Connection c) throws SQLException {
		String query = "SELECT id_posto FROM Posto WHERE id_settore = ? ORDER BY fila, colonna"; // orberby per scorrere la lista idPosti in modo lineare
		List<Integer> idPosti = new ArrayList<>();

		PreparedStatement ps = c.prepareStatement(query);
		ps.setInt(1, idSettore);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			idPosti.add(rs.getInt("id_posto"));
		}
		return idPosti;
	}
}