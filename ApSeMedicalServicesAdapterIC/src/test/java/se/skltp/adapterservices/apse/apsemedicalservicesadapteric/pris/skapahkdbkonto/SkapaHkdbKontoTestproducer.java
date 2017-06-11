package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.pris.skapahkdbkonto;

import se.riv.inera.se.apotekensservice.pris.skapahkdbkonto.v4.rivtabp21.SkapaHkdbKontoResponderInterface;
import se.riv.se.apotekensservice.pris.skapahkdbkontoresponder.v4.SkapaHkdbKontoRequestType;
import se.riv.se.apotekensservice.pris.skapahkdbkontoresponder.v4.SkapaHkdbKontoResponseType;

public class SkapaHkdbKontoTestproducer implements SkapaHkdbKontoResponderInterface {

	@Override
	public SkapaHkdbKontoResponseType skapaHkdbKonto(SkapaHkdbKontoRequestType arg0, String arg1,
			riv.inera_se_apotekensservice.argos._1.ArgosHeaderType arg2)
					throws se.riv.inera.se.apotekensservice.pris.skapahkdbkonto.v4.rivtabp21.SystemException,
					se.riv.inera.se.apotekensservice.pris.skapahkdbkonto.v4.rivtabp21.ApplicationException {
		// TODO Auto-generated method stub
		SkapaHkdbKontoResponseType response = new SkapaHkdbKontoResponseType();
		return response;
	}

}
