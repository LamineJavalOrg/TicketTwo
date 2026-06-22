package it.unipv.posw.model.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	

	public int getId_tappa() {
		return id_tappa;
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

	
	@Override
	public String toString() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	    return nomeSede + " - " + data_ora.format(formatter);
	}



	public int getId_evento() {
		return id_evento;
	}


	public String getNomeSede() {
		return nomeSede;
	}


	public void setId_tappa(int id_tappa) {
		this.id_tappa = id_tappa;
	}


	public void setId_evento(int id_evento) {
		this.id_evento = id_evento;
	}


	public void setId_sede(int id_sede) {
		this.id_sede = id_sede;
	}


	public void setNomeSede(String nomeSede) {
		this.nomeSede = nomeSede;
	}


	public void setData_ora(LocalDateTime data_ora) {
		this.data_ora = data_ora;
	}


	public void setTariffe(List<Tariffa> tariffe) {
		this.tariffe = tariffe;
	}
	
	

}
