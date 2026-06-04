package it.unipv.posw.model.service;

import java.util.List;

import it.unipv.posw.model.Sede;
import it.unipv.posw.model.Settore;
import it.unipv.posw.model.exception.SedeEsistenteException;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;


/**
 * @author gpelle
 */
public class SedeService {
	public Sede configuraSede(Sede sede, List<Settore> settori) throws SedeEsistenteException {
        if (MYSQLDAOFactory.getInstance().getSedeDAO()
                .isSedeEsistente(sede.getNome(), sede.getIndirizzo()))
            throw new SedeEsistenteException();

        Sede sedeSalvata = MYSQLDAOFactory.getInstance().getSedeDAO().salvaSede(sede);
        
        if (sedeSalvata == null) {
        	return null;
        }
        for (Settore s : settori) {
            s.setId_sede(sedeSalvata.getId_sede()); 
            
            Settore settoreSalvato = MYSQLDAOFactory.getInstance().getSedeDAO().salvaSettore(s);
            if (settoreSalvato != null) {
                s.setId_settore(settoreSalvato.getId_settore());
            }
        }
        return sedeSalvata;
    }
}
