/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 *
 * <p>This file is part of SKLTP.
 *
 * <p>This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 *
 * <p>This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * <p>You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301 USA
 */
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.axs.hamtapatientinfo.v6;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import riv.se_apotekensservice.axs._4.ApoteksinformationResponse;
import riv.se_apotekensservice.axs._5.PatientInformationResponse;
import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.se.apotekensservice.axs.hamtapatientinfo.v6.rivtabp21.ApplicationException;
import se.riv.se.apotekensservice.axs.hamtapatientinfo.v6.rivtabp21.HamtaPatientInfoResponderInterface;
import se.riv.se.apotekensservice.axs.hamtapatientinfo.v6.rivtabp21.SystemException;
import se.riv.se.apotekensservice.axs.hamtapatientinforesponder.v6.HamtaPatientInfoRequestType;
import se.riv.se.apotekensservice.axs.hamtapatientinforesponder.v6.HamtaPatientInfoResponseType;

public class HamtaPatientInfoTestProducer implements HamtaPatientInfoResponderInterface {

  @Override
  @WebResult(
      name = "HamtaPatientInfoResponse",
      targetNamespace = "urn:riv:se.apotekensservice:axs:HamtaPatientInfoResponder:6",
      partName = "parameters")
  @WebMethod(
      operationName = "HamtaPatientInfo",
      action = "urn:riv:se.apotekensservice:axs:HamtaPatientInfoResponder:6:HamtaPatientInfo")
  public HamtaPatientInfoResponseType hamtaPatientInfo(
      @WebParam(
              partName = "parameters",
              name = "HamtaPatientInfo",
              targetNamespace = "urn:riv:se.apotekensservice:axs:HamtaPatientInfoResponder:6")
          HamtaPatientInfoRequestType parameters,
      @WebParam(
              partName = "LogicalAddress",
              name = "LogicalAddress",
              targetNamespace = "urn:riv:itintegration:registry:1",
              header = true)
          String logicalAddress,
      @WebParam(
              partName = "ArgosHeader",
              name = "ArgosHeader",
              targetNamespace = "urn:riv:inera.se.apotekensservice:argos:1",
              header = true)
          ArgosHeaderType argosHeader)
      throws SystemException, ApplicationException {
    // TODO Auto-generated method stub
    HamtaPatientInfoResponseType response = new HamtaPatientInfoResponseType();
    String ssn = parameters.getPersonnummer();

    response.setApotek(createApoteksInformationResponse(ssn));
    response.setPatientInformation(createPatientInformationResponse());
    response.setFinnsOrdination(true);
    response.setDosproducent(ssn);
    return response;
  }

  private ApoteksinformationResponse createApoteksInformationResponse(String ssn) {
    ApoteksinformationResponse apoteksinformationResponse = new ApoteksinformationResponse();
    apoteksinformationResponse.setAktorsnamn("Aktörsnamn");
    return null;
  }

  private PatientInformationResponse createPatientInformationResponse() {
    PatientInformationResponse informationResponse = new PatientInformationResponse();
    informationResponse.setStatusKod("OK");
    return null;
  }
}
