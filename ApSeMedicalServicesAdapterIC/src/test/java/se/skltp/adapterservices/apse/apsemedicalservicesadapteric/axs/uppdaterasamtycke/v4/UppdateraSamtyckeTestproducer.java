package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.axs.uppdaterasamtycke.v4;

import javax.jws.WebMethod;
import javax.jws.WebResult;

import se.riv.inera.se.apotekensservice.argos.v4.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.axs.uppdaterasamtycke.v4.rivtabp21.UppdateraSamtyckeResponderInterface;
import se.riv.se.apotekensservice.axs.uppdaterasamtyckeresponder.v4.UppdateraSamtyckeRequestType;
import se.riv.se.apotekensservice.axs.uppdaterasamtyckeresponder.v4.UppdateraSamtyckeResponseType;


public class UppdateraSamtyckeTestproducer implements UppdateraSamtyckeResponderInterface {

	@Override
	@WebResult(name = "UppdateraSamtyckeResponse", targetNamespace = "urn:riv:se.apotekensservice:axs:UppdateraSamtyckeResponder:4", partName = "parameters")
	@WebMethod(operationName = "UppdateraSamtycke", action = "urn:riv:se.apotekensservice:axs:UppdateraSamtyckeResponder:4:UppdateraSamtycke")
	public UppdateraSamtyckeResponseType uppdateraSamtycke(UppdateraSamtyckeRequestType parameters,
			String logicalAddress, ArgosHeaderType argosHeader)
					throws se.riv.inera.se.apotekensservice.axs.uppdaterasamtycke.v4.rivtabp21.SystemException,
					se.riv.inera.se.apotekensservice.axs.uppdaterasamtycke.v4.rivtabp21.ApplicationException {
		// TODO Auto-generated method stub
		UppdateraSamtyckeResponseType response = new UppdateraSamtyckeResponseType();
		return response;
	}



}
