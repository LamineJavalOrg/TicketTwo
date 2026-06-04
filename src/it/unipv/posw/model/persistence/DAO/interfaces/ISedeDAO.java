package it.unipv.posw.model.persistence.DAO.interfaces;

import it.unipv.posw.model.Sede;
import it.unipv.posw.model.Settore; 

public interface ISedeDAO {

	public Sede salvaSede(Sede sede);
	public boolean isSedeEsistente(String nome, String indirizzo);
	public Settore salvaSettore(Settore settore);
	

}
