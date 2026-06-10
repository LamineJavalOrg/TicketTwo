package it.unipv.posw.model.service.ricerca;

import java.util.List;

import it.unipv.posw.model.entities.Artista;
import it.unipv.posw.model.enums.RicercaType;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;

/**
 * @author rkomi-dev
 */

public class RicercaPerArtistaStrategy implements IRicercaStrategy {

	public RicercaPerArtistaStrategy() {
	}
	
	
	@Override
	public List<Artista> ricerca(String query) {

		return MYSQLDAOFactory.getInstance().getArtistaDAO().trovaArtisti(query);
	}

	@Override
	public RicercaType getDestinazione() {
		return RicercaType.PER_ARTISTA;
		
	}

	@Override
	public String getEtichettaSuggerimento(Object o) {
		
		return ((Artista)o).getNome_darte();
	}


}
