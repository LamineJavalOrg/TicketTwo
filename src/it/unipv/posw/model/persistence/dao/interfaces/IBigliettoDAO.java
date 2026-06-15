package it.unipv.posw.model.persistence.dao.interfaces;

import java.util.List;

import it.unipv.posw.model.entities.Biglietto;
import it.unipv.posw.model.enums.TipologiaBiglietto;

/** 
 * @author rkomi-dev
 */

public interface IBigliettoDAO {
	
	List<Biglietto> getBigliettiDisponibili(int idTappa, int idSettore, TipologiaBiglietto tipo, int quantita);
	void updatePostAcquisto(int id_biglietto, String email, String nominativo, String qr);
	int countPostiLiberi(int idTappa, int idSettore, String tipo);
}
