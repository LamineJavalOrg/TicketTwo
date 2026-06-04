package it.unipv.posw.model.persistence.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.unipv.posw.model.Sede;
import it.unipv.posw.model.Settore;
import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.DAO.interfaces.ISedeDAO;

/**
 * @author gpelle
 */
public class SedeDAO implements ISedeDAO {
    
    
    @Override
    public boolean isSedeEsistente(String nome, String indirizzo) {
    	PreparedStatement ps;
    	ResultSet rs;
        String query = "SELECT COUNT(*) FROM Sede WHERE nome = ? AND indirizzo = ?";
        Connection c = null;
        
        try {
            c = DBConnection.getInstance().startConnection();            
            ps = c.prepareStatement(query);
            
            ps.setString(1, nome); 
            ps.setString(2, indirizzo);
            
            rs = ps.executeQuery();
            
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { 
        	e.printStackTrace(); 
        }
        finally { 
        	DBConnection.getInstance().closeConnection(c); 
        }
        return false;
    }

	@Override
    public Sede salvaSede(Sede sede) {
		PreparedStatement ps;
	    String query = "INSERT INTO Sede (nome, indirizzo) VALUES (?,?)";
	    Connection c = null;
	    
	    try {
	        c = DBConnection.getInstance().startConnection();
	        ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        
	        ps.setString(1, sede.getNome()); 
	        ps.setString(2, sede.getIndirizzo());
	        
	        int r = ps.executeUpdate();
	        if (r > 0) {
	        	try (ResultSet rs = ps.getGeneratedKeys()){
	        		if(rs.next()) {
	        			sede.setId_sede(rs.getInt(1));
	        			return sede;
	        		}
	        	}
	        }
	    } catch (SQLException e) { 
	        e.printStackTrace(); 
	    } finally { 
	        DBConnection.getInstance().closeConnection(c); 
	    }
	    return null;
}
	
	@Override
    public Settore salvaSettore(Settore settore) {
        String qSettore = "INSERT INTO Settore (id_sede, nome_settore, tipo_posti, capienza_max, "
        		+ "num_file, posti_per_fila, prefisso) VALUES (?,?,?,?,?,?,?)";
        String qPosto = "INSERT INTO Posto (id_settore, fila, colonna, prefisso) VALUES (?,?,?,?)";
        Connection c = null;
        
        int idSettore=-1;
        
        try {
            c = DBConnection.getInstance().startConnection();
            PreparedStatement ps = c.prepareStatement(qSettore, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, settore.getId_sede());
            ps.setString(2, settore.getNome_settore().name());
            ps.setString(3, settore.getTipo().name());
            ps.setInt(4, settore.getCapienza_max());
            ps.setInt(5, settore.getNum_file());
            ps.setInt(6, settore.getPosti_per_fila());
            ps.setString(7, settore.getPrefisso()); 
            
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()){
            	if(rs.next()) {
            		idSettore = rs.getInt(1);
            		settore.setId_settore(idSettore);
            	}
            }
            
            if (settore.getTipo() == TipologiaPosto.NUMERATO) {
                PreparedStatement psp = c.prepareStatement(qPosto);
                    
                    for (int f = 1; f <= settore.getNum_file(); f++) {
                        for (int col = 1; col <= settore.getPosti_per_fila(); col++) {
                            psp.setInt(1, idSettore);
                            psp.setInt(2, f);
                            psp.setInt(3, col);
                            psp.setString(4, settore.getPrefisso()); // Ogni posto eredita il prefisso del settore
                            
                            psp.addBatch();
                        }
                    }
                    psp.executeBatch();
                }
            }catch (SQLException e) {
                e.printStackTrace();
        }
        finally { 
        	DBConnection.getInstance().closeConnection(c); 
        }
        return null;
    }
}
