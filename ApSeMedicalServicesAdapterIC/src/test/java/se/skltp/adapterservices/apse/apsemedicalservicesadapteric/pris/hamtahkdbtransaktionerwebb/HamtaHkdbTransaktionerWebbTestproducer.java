package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.pris.hamtahkdbtransaktionerwebb;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbtransaktionerwebb.v1.rivtabp21.ApplicationException;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbtransaktionerwebb.v1.rivtabp21.HamtaHkdbTransaktionerWebbResponderInterface;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbtransaktionerwebb.v1.rivtabp21.SystemException;
import se.riv.se.apotekensservice.pris.hamtahkdbtransaktionerwebbresponder.v1.HamtaHkdbTransaktionerWebbRequestType;
import se.riv.se.apotekensservice.pris.hamtahkdbtransaktionerwebbresponder.v1.HamtaHkdbTransaktionerWebbResponseType;



public class HamtaHkdbTransaktionerWebbTestproducer implements HamtaHkdbTransaktionerWebbResponderInterface {

	@Override
	public HamtaHkdbTransaktionerWebbResponseType hamtaHkdbTransaktionerWebb(HamtaHkdbTransaktionerWebbRequestType arg0,
			String arg1, ArgosHeaderType arg2) throws SystemException, ApplicationException {
		// TODO Auto-generated method stub
		HamtaHkdbTransaktionerWebbResponseType response = new HamtaHkdbTransaktionerWebbResponseType();
		return response;
	}


}
