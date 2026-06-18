package it.unipv.posw.model.service;

import it.unipv.posw.model.entities.Biglietto;
import it.unipv.posw.model.entities.Carrello;
import it.unipv.posw.model.entities.SessioneCliente;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;
import it.unipv.posw.model.service.payment.IPagamento;

/** 
 * @author rkomi-dev
 */

public class AcquistoService {
	
	private QRService qrservice;
	
	public AcquistoService() {
	}
	
	public void Acquista(IPagamento metodo) {
		
		metodo.Paga(Carrello.getInstance().getTotale());
		
		qrservice = new QRService();
		
		qrservice.Genera();
		SessioneCliente.getInstance().setQr_attuale(qrservice.getCodice());
		
		for(Biglietto b : Carrello.getInstance().getItems()) {
			
			String email = SessioneCliente.getInstance().getClienteLoggato().getEmail();
			String nome = SessioneCliente.getInstance().getClienteLoggato().getNome();
			String cognome = SessioneCliente.getInstance().getClienteLoggato().getCognome();
			String qr = SessioneCliente.getInstance().getQr_attuale();
			
			MYSQLDAOFactory.getInstance().getBigliettoDAO().updatePostAcquisto(b.getId_biglietto(), email, nome + " " + cognome, qr, b.getTariffa().getPrezzo());
		}
		
		
	}
}
