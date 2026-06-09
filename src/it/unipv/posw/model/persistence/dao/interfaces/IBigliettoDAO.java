package it.unipv.posw.model.persistence.dao.interfaces;

import java.util.List;

import it.unipv.posw.model.Biglietto;

public interface IBigliettoDAO {
	
	List<Biglietto> getBigliettiDisponibili(int idTappa, int idSettore, String tipo, int quantita);
}
