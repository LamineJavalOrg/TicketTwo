package it.unipv.posw.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import it.unipv.posw.model.Sede;
import it.unipv.posw.model.Settore;
import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.exception.SedeEsistenteException;
import it.unipv.posw.model.exception.SedeNonEliminabileException;
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
	public Sede configuraSede(Sede sede, List<Settore> settori) throws SedeEsistenteException {
		if (settori == null || settori.isEmpty()) {
			throw new IllegalArgumentException("La configurazione deve includere almeno un settore.");
		}
		
		ISedeDAO sedeDAO = MYSQLDAOFactory.getInstance().getSedeDAO();
		ISettoreDAO settoreDAO = MYSQLDAOFactory.getInstance().getSettoreDAO();
		IPostoDAO postoDAO = MYSQLDAOFactory.getInstance().getPostoDAO();
		
		
		
        if (sedeDAO.isSedeEsistente(sede.getNome(), sede.getIndirizzo())) {
        	throw new SedeEsistenteException();
        }
        
        // scritture atomiche: connessione condivisa + transazione
        try (Connection c = DBConnection.getInstance().startConnection()){
        	try {
        		c.setAutoCommit(false);
        		
        		Sede sedeSalvata = sedeDAO.salvaSede(sede, c);
        		
        		for (Settore s : settori) {
                    s.setId_sede(sedeSalvata.getId_sede()); 
                    settoreDAO.salvaSettore(s, c);
                    
                    if (s.getTipo() == TipologiaPosto.NUMERATO) {
                    	postoDAO.salvaPostiPerSettore(s, c);
                    }
                }
        		c.commit();
                return sedeSalvata;
                
        	} catch (SQLException e) {
				c.rollback();
				e.printStackTrace();
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}  
        
    
	
    public List<Sede> getTutteLeSedi() {
        return MYSQLDAOFactory.getInstance().getSedeDAO().getTutteLeSedi();
    }
    
    public List<Settore> getSettoriPerSede(int idSede) {
		return MYSQLDAOFactory.getInstance().getSettoreDAO().getSettoriDaSede(idSede);
	}
    
	public boolean eliminaSede(Sede sede) throws SedeNonEliminabileException {
		int idSede = sede.getId_sede();
		
		ITappaDAO tappaDAO = MYSQLDAOFactory.getInstance().getTappaDAO();
		ISedeDAO sedeDAO = MYSQLDAOFactory.getInstance().getSedeDAO();
		
		if (tappaDAO.contaTappePerSede(idSede) > 0) {
			throw new SedeNonEliminabileException();
		}
        return sedeDAO.eliminaSede(idSede);
		}
	}
