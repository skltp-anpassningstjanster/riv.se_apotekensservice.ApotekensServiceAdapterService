package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.expo.sokdosmottagare;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import org.w3c.addressing.v1.AttributedURIType;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.expo.sokdosmottagare.v1.rivtabp21.ApplicationException;
import se.riv.inera.se.apotekensservice.expo.sokdosmottagare.v1.rivtabp21.SokDosmottagareResponderInterface;
import se.riv.inera.se.apotekensservice.expo.sokdosmottagare.v1.rivtabp21.SystemException;
import se.riv.se.apotekensservice.expo.sokdosmottagareresponder.v1.SokDosmottagareRequestType;
import se.riv.se.apotekensservice.expo.sokdosmottagareresponder.v1.SokDosmottagareResponseType;

public class SokDosmottagareTestproducer implements SokDosmottagareResponderInterface {


	@Override
	@WebResult(name = "SokDosmottagareResponse", targetNamespace = "urn:riv:se.apotekensservice:expo:SokDosmottagareResponder:1", partName = "parameters")
	@WebMethod(operationName = "SokDosmottagare", action = "urn:riv:se.apotekensservice:expo:SokDosmottagareResponder:1:SokDosmottagare")
	public SokDosmottagareResponseType sokDosmottagare(SokDosmottagareRequestType parameters, String logicalAddress,
			ArgosHeaderType argosHeader) throws ApplicationException, SystemException {

		SokDosmottagareResponseType response = new SokDosmottagareResponseType();
		return response;
	}

}
