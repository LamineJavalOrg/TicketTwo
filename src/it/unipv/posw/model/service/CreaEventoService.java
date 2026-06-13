package it.unipv.posw.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.exception.DataPassataException;
import it.unipv.posw.model.exception.PostiInsufficientiException;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;
import it.unipv.posw.model.persistence.dao.interfaces.IEventoDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IPostoDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ITappaDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ITariffaDAO;

/**
 * @author gpelle
 */

public class CreaEventoService {
	
	public boolean creaEventoMultiTappa(Evento evento, List<Tappa> tappe) throws DataPassataException, PostiInsufficientiException {
		
		if (evento == null) {
			throw new IllegalArgumentException("L'evento non può essere null.");
		}
		if (tappe == null || tappe.isEmpty()) {
			throw new IllegalArgumentException("L'evento deve avere almeno una tappa.");
		}
		
		for (Tappa tappa : tappe) {
			if (tappa == null || tappa.getData_ora() == null) {
				throw new IllegalArgumentException("Ogni tappa deve avere una data/ora valida.");
			}
			if (tappa.getData_ora().isBefore(LocalDateTime.now())) {
				throw new DataPassataException();
			}
		}
		
		IEventoDAO eventoDAO = MYSQLDAOFactory.getInstance().getEventoDAO();
		ITappaDAO tappaDAO = MYSQLDAOFactory.getInstance().getTappaDAO();
        ITariffaDAO tariffaDAO = MYSQLDAOFactory.getInstance().getTariffaDAO();
        IPostoDAO postoDAO = MYSQLDAOFactory.getInstance().getPostoDAO();
        
        // connessione condivisa + transazione
        Connection c = null;
        try {
        	c = DBConnection.getInstance().startConnection();
        	c.setAutoCommit(false);
        	
        	int idEvento = eventoDAO.salvaEvento(evento, c);
        	
        	for (Tappa tappa : tappe) {
        		int idTappa = tappaDAO.salvaTappa(tappa, idEvento, c);
        		
        		List<Tariffa> tariffe = tappa.getTariffe();
				Map<Integer, List<Integer>> postiPerSettore = caricaPostiPerSettore(tappa.getTariffe(), postoDAO, c);
                tariffaDAO.inserisciTariffa(tariffe, idTappa, idEvento, c, postiPerSettore);
        	}

            c.commit();
            return true;
            
        } catch (PostiInsufficientiException e) {
        	eseguiRollback(c);
			throw e;
		} catch (Exception e) {
        	eseguiRollback(c);
            e.printStackTrace();
            return false;
        } finally {
        	attivaAutoCommit(c);
            DBConnection.getInstance().closeConnection(c);
        }
    }
	
	
	
	
	
    private Map<Integer, List<Integer>> caricaPostiPerSettore(List<Tariffa> tariffe, IPostoDAO postoDAO, Connection c) throws SQLException {
    	Map<Integer, List<Integer>> postiPerSettore = new HashMap<>();

        for (Tariffa t : tariffe) {
            int idSettore = t.getId_settore();

            if (!postiPerSettore.containsKey(idSettore)) {
                List<Integer> posti = postoDAO.getIdPostiPerSettore(idSettore, c);

                if (!posti.isEmpty()) {
                    postiPerSettore.put(idSettore, posti);
                }
            }
        }
        return postiPerSettore;
    }
	
	
	
	// metodi di aiuto per gestione transazione
	private void eseguiRollback(Connection c) {
		if (c != null) {
			try {
				c.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
 
	private void attivaAutoCommit(Connection c) {
		if (c != null) {
			try {
				c.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}