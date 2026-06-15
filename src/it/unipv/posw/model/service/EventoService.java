package it.unipv.posw.model.service;

import java.util.List;

import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;

/**
 * @author gpelle
 */
public class EventoService {
	
	public Tariffa getTariffaSpecifica(int idTappa, int idSettore, String tipoString) {
        TipologiaBiglietto tipoEnum = TipologiaBiglietto.valueOf(tipoString);
        return MYSQLDAOFactory.getInstance()
                              .getTariffaDAO()
                              .getTariffaCompleta(idTappa, idSettore, tipoEnum);
    }
	
	public int getPostiResidui(int idTappa, int idSettore, String tipo) {
        return MYSQLDAOFactory.getInstance()
                              .getBigliettoDAO()
                              .countPostiLiberi(idTappa, idSettore, tipo);
    }
	
	public List<TipologiaBiglietto> getTipologiePerSettore(int idTappa, int idSettore) {
        return MYSQLDAOFactory.getInstance()
                              .getTariffaDAO()
                              .trovaTipologieTappaSettore(idTappa, idSettore);
    }

}
