package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.axs.hamtakundinfo.v6;

import javax.jws.WebMethod;
import javax.jws.WebResult;

import riv.se_apotekensservice.axs._4.ApoteksinformationResponse;
import riv.se_apotekensservice.axs._5.KundinformationResponse;
import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.se.apotekensservice.axs.hamtakundinfo.v6.rivtabp21.ApplicationException;
import se.riv.se.apotekensservice.axs.hamtakundinfo.v6.rivtabp21.HamtaKundInfoResponderInterface;
import se.riv.se.apotekensservice.axs.hamtakundinfo.v6.rivtabp21.SystemException;
import se.riv.se.apotekensservice.axs.hamtakundinforesponder.v6.HamtaKundInfoRequestType;
import se.riv.se.apotekensservice.axs.hamtakundinforesponder.v6.HamtaKundInfoResponseType;


public class HamtaKundInfoTestproducer implements HamtaKundInfoResponderInterface {
	
	public static final String SSN_OK = "121212121212";

	@Override
	@WebResult(name = "HamtaKundInfoResponse", targetNamespace = "urn:riv:se.apotekensservice:axs:HamtaKundInfoResponder:5", partName = "parameters")
	@WebMethod(operationName = "HamtaKundInfo", action = "urn:riv:se.apotekensservice:axs:HamtaKundInfoResponder:5:HamtaKundInfo")
	public HamtaKundInfoResponseType hamtaKundInfo(HamtaKundInfoRequestType parameters, String logicalAddress,
			ArgosHeaderType argosHeader) throws SystemException, ApplicationException {

		HamtaKundInfoResponseType respone = new HamtaKundInfoResponseType();
		respone.setKundinformation(createKundInformation(parameters));
		respone.setApotek(createApoteksInformation(parameters));
		return respone;
	}
	private ApoteksinformationResponse createApoteksInformation(HamtaKundInfoRequestType parameters) {
		ApoteksinformationResponse response = new ApoteksinformationResponse();
		return response;
	}

	private KundinformationResponse createKundInformation(HamtaKundInfoRequestType parameters) {
		KundinformationResponse response = new KundinformationResponse();
		response.setStatusKod("OK");
		return response;
	}

}
