package it.unipv.posw.model.service;

/** 
 * @author rkomi-dev
 */

public class QRService {
	
	private String codice; 
	public final int QRDIGITS = 8;
	
	public QRService() {

	}

	public void Genera() {
		
		codice = "QR";
		for(int i = 0; i < QRDIGITS; i++) {
			codice += (int)(9 * Math.random());
		}
	}

	public String getCodice() {
		return codice;
	}
	
	

}
