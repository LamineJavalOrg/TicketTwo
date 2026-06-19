package it.unipv.posw.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unipv.posw.model.entities.Artista;
import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.enums.TipologiaEvento;
import it.unipv.posw.model.exception.DataPassataException;
import it.unipv.posw.model.exception.DataTappaDuplicataException;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.EventoException;
import it.unipv.posw.model.exception.EventoSalvataggioException;
import it.unipv.posw.model.exception.EventoSenzaTappeException;
import it.unipv.posw.model.exception.TariffaNonValidaException;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;
import it.unipv.posw.model.persistence.dao.interfaces.IArtistaDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IEventoDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IPostoDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ITappaDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ITariffaDAO;

/**
 * @author gpelle
 */

public class CreaEventoService {
	
	public Evento creaEvento(String nome, TipologiaEvento tipo, String emailOrganizzatore,
			String nomeArtista, List<Tappa> tappe)
			throws EmptyFieldException, EventoException,
			TariffaNonValidaException, DataPassataException {

		if (nome == null || nome.trim().isEmpty() || nomeArtista == null || nomeArtista.trim().isEmpty()
				|| emailOrganizzatore == null || emailOrganizzatore.trim().isEmpty() || tipo == null) {
			throw new EmptyFieldException();
		}

		if (tappe == null || tappe.isEmpty()) {
			throw new EventoSenzaTappeException();
		}

		for (Tappa tappa : tappe) {
			validaTappa(tappa);
		}

		int idArtista = ritornaIdArtista(nomeArtista);
		Evento evento = new Evento(0, nome, tipo, emailOrganizzatore, idArtista);

		return salvaInTransazione(evento, tappe);
	}


	public void validaTappa(Tappa tappa) throws EmptyFieldException, TariffaNonValidaException, DataPassataException {
		if (tappa == null || tappa.getId_sede() <= 0 || tappa.getData_ora() == null) {
			throw new EmptyFieldException();
		}
		if (tappa.getData_ora().isBefore(LocalDateTime.now())) {
	        throw new DataPassataException();
	    }
		
		validaTariffe(tappa.getTariffe());
	}

	private void validaTariffe(List<Tariffa> tariffe) throws TariffaNonValidaException {
		if (tariffe == null || tariffe.isEmpty()) {
			throw new TariffaNonValidaException("Configura almeno una tariffa per la tappa.");
		}
		for (Tariffa t : tariffe) {
			if (t.getTipob() == null) {
				throw new TariffaNonValidaException("Seleziona la tipologia di biglietto per ogni settore.");
			}
			if (t.getPrezzo() <= 0) {
				throw new TariffaNonValidaException("Il prezzo deve essere maggiore di 0.");
			}
			if (t.getQuantita_massima() <= 0) {
				throw new TariffaNonValidaException("La quantità deve essere maggiore di 0.");
			}
		}
	}

	public void validaDataNonDuplicata(Tappa nuova, List<Tappa> esistenti) throws DataTappaDuplicataException {
		if (nuova == null || esistenti == null) {
			return;
		}
		for (Tappa t : esistenti) {
			if (stessaData(t, nuova)) {
				throw new DataTappaDuplicataException();
			}
		}
	}

	
	private boolean stessaData(Tappa a, Tappa b) {
		if (a.getData_ora() == null || b.getData_ora() == null) {
			return false;
		}
		return a.getData_ora().toLocalDate().equals(b.getData_ora().toLocalDate());
	}
	
	private int ritornaIdArtista(String nomeArtista) {
		String nome = nomeArtista.trim();
		IArtistaDAO artistaDAO = MYSQLDAOFactory.getInstance().getArtistaDAO();
 
		List<Artista> trovati = artistaDAO.trovaArtisti(nome);
		for (Artista a : trovati) {
			if (a.getNome_darte().equalsIgnoreCase(nome)) {
				return a.getId_artista();
			}
		}
		return artistaDAO.salvaArtista(new Artista(0, nome));
	}

	// connessione condivisa + transazione
	private Evento salvaInTransazione(Evento evento, List<Tappa> tappe) throws EventoException {
		IEventoDAO eventoDAO = MYSQLDAOFactory.getInstance().getEventoDAO();
		ITappaDAO tappaDAO = MYSQLDAOFactory.getInstance().getTappaDAO();
		ITariffaDAO tariffaDAO = MYSQLDAOFactory.getInstance().getTariffaDAO();
		IPostoDAO postoDAO = MYSQLDAOFactory.getInstance().getPostoDAO();

		Connection c = null;
		try {
			c = DBConnection.getInstance().startConnection();
			c.setAutoCommit(false);

			int idEvento = eventoDAO.salvaEvento(evento, c);

			for (Tappa tappa : tappe) {
				int idTappa = tappaDAO.salvaTappa(tappa, idEvento, c);

				List<Tariffa> tariffe = tappa.getTariffe();
				Map<Integer, List<Integer>> postiPerSettore = caricaPostiPerSettore(tariffe, postoDAO, c);
				tariffaDAO.inserisciTariffa(tariffe, idTappa, idEvento, c, postiPerSettore);
			}

			c.commit();

			Evento salvato = new Evento(idEvento, evento.getNome(), evento.getTipo(),
					evento.getEmail_organizzatore(), evento.getId_artista());
			for (Tappa tappa : tappe) {
				salvato.aggiungiTappa(tappa);
			}
			return salvato;

		} catch (Exception e) {
			DBConnection.getInstance().rollback(c);
			e.printStackTrace();
			throw new EventoSalvataggioException("Errore imprevisto del database durante la creazione dell'evento", e);
		} finally {
			DBConnection.getInstance().setAutoCommit(c, true);
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
}