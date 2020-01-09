package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.or.hamtaickeaktuellaordinationer.v6;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import javax.jws.WebService;
import se.riv.se.apotekensservice.or.hamtaickeaktuellaordinationer.v6.ApplicationException;
import se.riv.se.apotekensservice.or.hamtaickeaktuellaordinationer.v6.HamtaIckeAktuellaOrdinationerResponderInterface;
import se.riv.se.apotekensservice.or.hamtaickeaktuellaordinationer.v6.SystemException;
import se.riv.se.apotekensservice.or.hamtaickeaktuellaordinationerresponder.v6.HamtaIckeAktuellaOrdinationerResponseType;
import se.riv.se.apotekensservice.or.v7.ApoteksinformationResponse;
import se.riv.se.apotekensservice.or.v7.ArbetsplatsInfoResponse;
import se.riv.se.apotekensservice.or.v7.ArtikelResponse;
import se.riv.se.apotekensservice.or.v7.DosunderlagResponse;
import se.riv.se.apotekensservice.or.v7.ForskrivarinfoResponse;
import se.riv.se.apotekensservice.or.v7.OrdinationslistaKortInfoResponse;

@WebService(serviceName = "HamtaIckeAktuellaOrdinationerResponderService",
      endpointInterface = "se.riv.se.apotekensservice.or.hamtaickeaktuellaordinationer.v6.HamtaIckeAktuellaOrdinationerResponderInterface",
      portName = "HamtaIckeAktuellaOrdinationerResponderPort", targetNamespace = "urn:riv:se.apotekensservice:or:HamtaIckeAktuellaOrdinationer:6",
      wsdlLocation = "TD_APSE_OR_7_0/interactions/HamtaIckeAktuellaOrdinationerInteraction/HamtaIckeAktuellaOrdinationerInteraction_6.2_rivtabp21.wsdl")

public class HamtaIckeAktuellaOrdinationerTestProducer implements HamtaIckeAktuellaOrdinationerResponderInterface {

  @Override
  @WebResult(
      name = "HamtaIckeAktuellaOrdinationerResponse",
      targetNamespace = "urn:riv:se.apotekensservice:or:HamtaIckeAktuellaOrdinationerResponder:6",
      partName = "parameters")
  @WebMethod(
      operationName = "HamtaIckeAktuellaOrdinationer",
      action =
          "urn:riv:se.apotekensservice:or:HamtaIckeAktuellaOrdinationerResponder:6:HamtaIckeAktuellaOrdinationer")
  public HamtaIckeAktuellaOrdinationerResponseType hamtaIckeAktuellaOrdinationer(
      @WebParam(
              partName = "parameters",
              name = "HamtaIckeAktuellaOrdinationer",
              targetNamespace =
                  "urn:riv:se.apotekensservice:or:HamtaIckeAktuellaOrdinationerResponder:6")
          se.riv.se.apotekensservice.or.hamtaickeaktuellaordinationerresponder.v6.HamtaIckeAktuellaOrdinationerRequestType
              parameters,
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
          se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType argosHeader)
      throws ApplicationException, SystemException {
      String personnummer = parameters.getPersonnummer();

      if (personnummer == null || "".equals(personnummer)) {
        throw new RuntimeException("Personnummer is mandatory!");
      }

      if ("APPLICATIONEXCEPTION".equals(personnummer)) {
        throw new ApplicationException(
            "APPLICATIONEXCEPTION");
      }

      if ("SYSTEMEXCEPTION".equals(personnummer)) {
        throw new SystemException(
            "SYSTEMEXCEPTION");
      }

      HamtaIckeAktuellaOrdinationerResponseType response = new HamtaIckeAktuellaOrdinationerResponseType();
      response.getApoteksInformationLista().add(createApoteksInformation());
      response.getArbetsplatsInfoLista().add(createArbetsplatsInfo());
      response.getArtikelLista().add(createArtikelResponse());
      response.getForskrivarInfoLista().add(createForskrivarinfo());
      response.setOrdinationKortInfolista(createOrdinationsLista(personnummer));
      return response;
    }

    private ApoteksinformationResponse createApoteksInformation() {
      ApoteksinformationResponse apoteksInformation = new ApoteksinformationResponse();
      apoteksInformation.setAktorsnamn("Aktornamn");
      apoteksInformation.setAktorsorgnr(1L);
      apoteksInformation.setAllmantelefon("Allmantelefon");
      apoteksInformation.setBesoksadress("Besoksadress");
      apoteksInformation.setEReceptDjur(true);
      apoteksInformation.setFax("Fax");
      apoteksInformation.setGlnKod("GlnKod");
      apoteksInformation.setHuvudtypkod("Huvudtypkod");
      apoteksInformation.setNamn("Namne");
      apoteksInformation.setOrt("Ort");
      apoteksInformation.setRegistrerad(true);
      // apoteksInformation.setSlutdatum();
      // apoteksInformation.setStartdatum(value);
      return apoteksInformation;
    }

    private ArbetsplatsInfoResponse createArbetsplatsInfo() {
      ArbetsplatsInfoResponse arbetsplatsInfo = new ArbetsplatsInfoResponse();
      arbetsplatsInfo.setArbetsplatskod("Arbetsplatskod");
      arbetsplatsInfo.setArbetsplatsNamn("Arbetsplatsnamn");
      arbetsplatsInfo.setArbetsplatsOrt("Arbetsplatsort");
      arbetsplatsInfo.setFaxnummer("Faxnummer");
      // arbetsplatsInfo.setGiltigSlut(value);
      // arbetsplatsInfo.setGiltigStart(value);
      arbetsplatsInfo.setPostadress("Potadress");
      arbetsplatsInfo.setPostnummer("Postnummer");
      arbetsplatsInfo.setRegistrerad(true);
      arbetsplatsInfo.setTelefonnummer1("Telefonnummer1");
      arbetsplatsInfo.setTelefonnummer2("Telefonnummer2");
      return arbetsplatsInfo;
    }

    private ArtikelResponse createArtikelResponse() {
      ArtikelResponse artikel = new ArtikelResponse();
      artikel.setAlternativtAntalKlartext("AlternativtAntalKlartext");
      artikel.setAntalKlartext("AntalKlartext");
      artikel.setArtikelbenamning("Artikelbenamning");
      artikel.setAtcKlartext("AtcKlartext");
      artikel.setAtcKod("AtcKod");
      // artikel.setAvregistreringsdatum(value);
      artikel.setBestallningsstoppad(true);
      artikel.setForegaendePrisperiodensvara(true);
      artikel.setFormansberattigad(true);
      artikel.setForpackningsenhet("Forpackningsenhet");
      artikel.setForpackningsinnehall("Forpackningsinnehall");
      artikel.setForpackningsmangd(10D);
      artikel.setForpackningstyp("Forpackningstyp");
      artikel.setForsaljningsstoppad(true);
      // TODO: Fler attribut
      return artikel;
    }

    private ForskrivarinfoResponse createForskrivarinfo() {
      ForskrivarinfoResponse forskrivarInfo = new ForskrivarinfoResponse();
      forskrivarInfo.setEfternamn("Efternamn");
      forskrivarInfo.setFornamn("Fornamn");
      forskrivarInfo.setForskrivarkod("Forskrivarkod");
      forskrivarInfo.setGiltig(true);
      forskrivarInfo.setGruppforskrivarkodKlartext("Gruppforskrivarkod");
      //forskrivarInfo.setInskrankt(true);
      return forskrivarInfo;
    }

    private OrdinationslistaKortInfoResponse createOrdinationsLista(String personnummer) {
      DosunderlagResponse dosunderlag = new DosunderlagResponse();
      dosunderlag.setDosproducent("Dosproducent");
      dosunderlag.setDosunderlagsversion(1);

      OrdinationslistaKortInfoResponse ordinationslista = new OrdinationslistaKortInfoResponse();
      ordinationslista.setPersonnummer(personnummer);
      ordinationslista.setUnderlagsversion(1);
      ordinationslista.setDosunderlag(dosunderlag);
      return ordinationslista;
    }
}
