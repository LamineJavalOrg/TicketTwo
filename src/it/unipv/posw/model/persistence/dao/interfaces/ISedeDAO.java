package it.unipv.posw.model.persistence.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import it.unipv.posw.model.entities.Sede;

/**
 * @author gpelle
 */

public interface ISedeDAO {

	boolean isSedeEsistente(String nome, String indirizzo);
	Sede salvaSede(Sede sede, Connection c) throws SQLException;
	boolean eliminaSede(int idSede);
	List<Sede> getTutteLeSedi();
	List<Sede> getSediPerOrganizzatore(String email);


	
	
	

}
