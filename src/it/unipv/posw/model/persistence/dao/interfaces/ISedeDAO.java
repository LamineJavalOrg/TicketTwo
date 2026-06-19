package it.unipv.posw.model.persistence.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import it.unipv.posw.model.entities.Sede;

/**
 * @author gpelle
 */

public interface ISedeDAO {

	public boolean isSedeEsistente(String nome, String indirizzo);
	public Sede salvaSede(Sede sede, Connection c) throws SQLException;
	public boolean eliminaSede(int idSede);
	public List<Sede> getTutteLeSedi();
	List<Sede> getSediPerOrganizzatore(String email);


	
	
	

}
