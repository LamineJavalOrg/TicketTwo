package it.unipv.posw.model.entities;

import java.time.LocalDateTime;
/**
 * @author gpelle
 */
public class Tappa {
    private int id_tappa;
    private int id_evento;
    private int id_sede;
    private String nomeSede;
    private LocalDateTime data_ora;
    
	public Tappa(int id_tappa, int id_evento, int id_sede, String nomeSede, LocalDateTime data_ora) {
		super();
		this.id_tappa = id_tappa;
		this.id_evento = id_evento;
		this.id_sede = id_sede;
		this.nomeSede = nomeSede;
		this.data_ora = data_ora;
	}
    
    
    

}
