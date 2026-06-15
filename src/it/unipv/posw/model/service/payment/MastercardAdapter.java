package it.unipv.posw.model.service.payment;

/** 
 * @author rkomi-dev
 */

public class MastercardAdapter implements IPagamento {
	
	IMastercard mast;

	public MastercardAdapter(IMastercard mast) {
		super();
		this.mast = mast;
	}


	@Override
	public boolean Paga(double importo) {
		mast.pagaMastercard(importo);
		return true;
	}

}
