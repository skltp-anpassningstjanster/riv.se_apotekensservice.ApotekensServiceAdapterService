package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.pris.hamtahkdbkonto;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbkonto.v1.rivtabp21.ApplicationException;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbkonto.v1.rivtabp21.HamtaHkdbKontoResponderInterface;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbkonto.v1.rivtabp21.SystemException;
import se.riv.se.apotekensservice.pris.hamtahkdbkontoresponder.v1.HamtaHkdbKontoRequestType;
import se.riv.se.apotekensservice.pris.hamtahkdbkontoresponder.v1.HamtaHkdbKontoResponseType;


public class HamtaHkdbKontoTestproducer implements HamtaHkdbKontoResponderInterface {

	@Override
	public HamtaHkdbKontoResponseType hamtaHkdbKonto(HamtaHkdbKontoRequestType arg0, String arg1, ArgosHeaderType arg2)
			throws ApplicationException, SystemException {
		// TODO Auto-generated method stub
		HamtaHkdbKontoResponseType response = new HamtaHkdbKontoResponseType();
		return response;
	}

}
