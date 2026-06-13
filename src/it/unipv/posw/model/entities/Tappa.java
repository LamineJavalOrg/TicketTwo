package it.unipv.posw.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * @author gpelle
 */
public class Tappa {
    private int id_tappa;
    private int id_evento;
    private int id_sede;
    private String nomeSede;
    private LocalDateTime data_ora;
    private List<Tariffa> tariffe;
    

	public Tappa(int id_tappa, int id_evento, int id_sede, String nomeSede, LocalDateTime data_ora) {
		this.id_tappa = id_tappa;
		this.id_evento = id_evento;
		this.id_sede = id_sede;
		this.nomeSede = nomeSede;
		this.data_ora = data_ora;
		this.tariffe = new ArrayList<Tariffa>();
	}
	

	public int getId_sede() {
		return id_sede;
	}
	
	public List<Tariffa> getTariffe() {
		return tariffe;
	}

	public LocalDateTime getData_ora() {
		return data_ora;
	}
    
    
    

}
