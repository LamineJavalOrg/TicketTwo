package it.unipv.posw.model.persistence.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

import it.unipv.posw.model.Settore;

/**
 * @author gpelle
 */
public interface IPostoDAO {

	void salvaPostiPerSettore(Settore settore, Connection c) throws SQLException;

}
