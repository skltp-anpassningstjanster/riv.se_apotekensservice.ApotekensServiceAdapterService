package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.pris.hamtahkdbtransaktionerwebb;

import javax.jws.WebMethod;
import javax.jws.WebResult;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbtransaktionerwebb.v1.rivtabp21.ApplicationException;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbtransaktionerwebb.v1.rivtabp21.HamtaHkdbTransaktionerWebbResponderInterface;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbtransaktionerwebb.v1.rivtabp21.SystemException;
import se.riv.se.apotekensservice.pris.hamtahkdbtransaktionerwebbresponder.v1.HamtaHkdbTransaktionerWebbRequestType;
import se.riv.se.apotekensservice.pris.hamtahkdbtransaktionerwebbresponder.v1.HamtaHkdbTransaktionerWebbResponseType;



public class HamtaHkdbTransaktionerWebbTestproducer implements HamtaHkdbTransaktionerWebbResponderInterface {

	@Override
	@WebResult(name = "HamtaHkdbTransaktionerWebbResponse", targetNamespace = "urn:riv:se.apotekensservice:pris:HamtaHkdbTransaktionerWebbResponder:1", partName = "parameters")
	@WebMethod(operationName = "HamtaHkdbTransaktionerWebb", action = "urn:riv:se.apotekensservice:pris:HamtaHkdbTransaktionerWebbResponder:1:HamtaHkdbTransaktionerWebb")
	public HamtaHkdbTransaktionerWebbResponseType hamtaHkdbTransaktionerWebb(HamtaHkdbTransaktionerWebbRequestType arg0,
			String arg1, ArgosHeaderType arg2) throws SystemException, ApplicationException {
		// TODO Auto-generated method stub
		HamtaHkdbTransaktionerWebbResponseType response = new HamtaHkdbTransaktionerWebbResponseType();
		return response;
	}


}
