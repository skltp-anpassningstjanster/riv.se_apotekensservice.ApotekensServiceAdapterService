package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.or.hamtaickeaktuellaordinationer.v6;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.se.apotekensservice.or.hamtaickeaktuellaordinationer.v6.ApplicationException;
import se.riv.se.apotekensservice.or.hamtaickeaktuellaordinationer.v6.HamtaIckeAktuellaOrdinationerResponderInterface;
import se.riv.se.apotekensservice.or.hamtaickeaktuellaordinationer.v6.SystemException;
import se.riv.se.apotekensservice.or.hamtaickeaktuellaordinationerresponder.v6.HamtaIckeAktuellaOrdinationerRequestType;
import se.riv.se.apotekensservice.or.hamtaickeaktuellaordinationerresponder.v6.HamtaIckeAktuellaOrdinationerResponseType;

public class HamtaIckeAktuellaOrdinationerTestProducer implements
    HamtaIckeAktuellaOrdinationerResponderInterface {

  @Override
  @WebResult(name = "HamtaIckeAktuellaOrdinationerResponse", targetNamespace = "urn:riv:se.apotekensservice:or:HamtaIckeAktuellaOrdinationerResponder:4", partName = "parameters")
  @WebMethod(operationName = "HamtaIckeAktuellaOrdinationer", action = "urn:riv:se.apotekensservice:or:HamtaIckeAktuellaOrdinationerResponder:4:HamtaIckeAktuellaOrdinationer")
  public se.riv.se.apotekensservice.or.hamtaickeaktuellaordinationerresponder.v6.HamtaIckeAktuellaOrdinationerResponseType hamtaIckeAktuellaOrdinationer(
      @WebParam(partName = "parameters", name = "HamtaIckeAktuellaOrdinationer", targetNamespace = "urn:riv:se.apotekensservice:or:HamtaIckeAktuellaOrdinationerResponder:4")
          HamtaIckeAktuellaOrdinationerRequestType parameters,
      @WebParam(partName = "LogicalAddress", name = "LogicalAddress", targetNamespace = "urn:riv:itintegration:registry:1", header = true)
          String logicalAddress,
      @WebParam(partName = "ArgosHeader", name = "ArgosHeader", targetNamespace = "urn:riv:inera.se.apotekensservice:argos:1", header = true)
          ArgosHeaderType argosHeader
  ) throws SystemException, ApplicationException {
    HamtaIckeAktuellaOrdinationerResponseType response = new HamtaIckeAktuellaOrdinationerResponseType();
//		response.setOrdinationsId(parameters.getOrdinationsId());
//		response.setUnderlagsversion(parameters.getUnderlagsversion());
    return response;
  }
}
