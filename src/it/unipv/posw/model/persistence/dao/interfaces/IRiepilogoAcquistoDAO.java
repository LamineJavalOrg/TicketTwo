package it.unipv.posw.model.persistence.dao.interfaces;

import java.util.List;

import it.unipv.posw.model.entities.RiepilogoAcquisto;

/** 
 * @author rkomi-dev
 */

public interface IRiepilogoAcquistoDAO {
	
	List<RiepilogoAcquisto> getBigliettiAcquistati(String email);

}
