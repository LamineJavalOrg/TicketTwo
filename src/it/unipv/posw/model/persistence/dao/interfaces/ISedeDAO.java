package it.unipv.posw.model.persistence.dao.interfaces;

import it.unipv.posw.model.Sede;
import it.unipv.posw.model.Settore; 

/**
 * @author gpelle
 */
public interface ISedeDAO {

	public Sede salvaSede(Sede sede);
	public boolean isSedeEsistente(String nome, String indirizzo);
	public Settore salvaSettore(Settore settore);
	

}
