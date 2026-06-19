package it.unipv.posw.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.SedeEsistenteException;
import it.unipv.posw.model.exception.SedeException;
import it.unipv.posw.model.exception.SedeNonEliminabileException;
import it.unipv.posw.model.exception.SedeSalvataggioException;
import it.unipv.posw.model.exception.SedeSenzaSettoriException;
import it.unipv.posw.model.exception.SettoreNonValidoException;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;
import it.unipv.posw.model.persistence.dao.interfaces.IPostoDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ISedeDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ISettoreDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ITappaDAO;

/**
 * @author gpelle
 */

public class SedeService {
	public Settore creaSettore(TipologiaSettore nome, String prefisso, TipologiaPosto tipoPosto,
			int numFile, int numColonne, int capienza) throws SettoreNonValidoException {
 
		if (tipoPosto == TipologiaPosto.NUMERATO) {
			return Settore.creaNumerato(nome, prefisso, numFile, numColonne);
		}
		return Settore.creaNonNumerato(nome, prefisso, capienza);
	}
	
	
	public Sede configuraSede(Sede sede) throws EmptyFieldException, SedeException {
		if (sede.getNome() == null || sede.getNome().trim().isEmpty()
				|| sede.getIndirizzo() == null || sede.getIndirizzo().trim().isEmpty()) {
			throw new EmptyFieldException();
		}
		if (!sede.possiedeSettori()) {
			throw new SedeSenzaSettoriException();
		}
		
		ISedeDAO sedeDAO = MYSQLDAOFactory.getInstance().getSedeDAO();
		ISettoreDAO settoreDAO = MYSQLDAOFactory.getInstance().getSettoreDAO();
		IPostoDAO postoDAO = MYSQLDAOFactory.getInstance().getPostoDAO();
		
        if (sedeDAO.isSedeEsistente(sede.getNome(), sede.getIndirizzo())) {
        	throw new SedeEsistenteException();
        }
        
        
        // connessione condivisa + transazione
        Connection c = null;
        try {
        	c = DBConnection.getInstance().startConnection();
        	c.setAutoCommit(false);
        	     		
        	Sede sedeSalvata = sedeDAO.salvaSede(sede, c);
        		
        	for (Settore s : sede.getSettori()) {
        		s.setId_sede(sedeSalvata.getId_sede()); 
                settoreDAO.salvaSettore(s, c);
                    
                if (s.getTipo() == TipologiaPosto.NUMERATO) {
                  	postoDAO.salvaPostiPerSettore(s, c);
                }
            }
        	c.commit();
            return sedeSalvata;
            
        } catch (Exception e) {
        	DBConnection.getInstance().rollback(c);
			e.printStackTrace();
			throw new SedeSalvataggioException("Errore imprevisto del database durante il salvataggio della sede.", e);
		} finally {
			DBConnection.getInstance().setAutoCommit(c, true);
			DBConnection.getInstance().closeConnection(c);
		}
	}
    
	
    public List<Sede> getTutteLeSedi() {
        return MYSQLDAOFactory.getInstance().getSedeDAO().getTutteLeSedi();
    }
    
    public List<Settore> getSettoriPerSede(Tappa tappa) {
		return MYSQLDAOFactory.getInstance().getSettoreDAO().getSettoriDaSede(tappa.getId_sede());
	}	
}
