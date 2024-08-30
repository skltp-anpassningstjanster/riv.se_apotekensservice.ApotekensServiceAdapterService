package se.skltp.adapterservices.apse.debug;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.pascal.ticket.ArgosTicket;

@Component
@Log4j2
public class PropertyPrinter implements SmartInitializingSingleton {
  @Value("${locatorLabelTicketMachine:unset}")
  private String locatorLabelTicketMachine;

  @Value("${locatorLabelApseAdapt:unset}")
  private String locatorLabelApseAdapt;

  @Value("${locatorLabelGeneral:unset}")
  private String locatorLabelGeneral;

  @Override
  public void afterSingletonsInstantiated() {
    log.info("LocatorLabel Properties present at launch: " +
        "locatorLabelTicketMachine={}; " +
        "locatorLabelApseAdapt={}; " +
        "locatorLabelGeneral={};",
        locatorLabelTicketMachine,
        locatorLabelApseAdapt,
        locatorLabelGeneral);

    ArgosTicket argosSamlTicketMachine = new ArgosTicket();
    log.info("Creating dummy ticket to test and preload TicketMachine beans");
    String ticket = argosSamlTicketMachine.getTicketForOrganization("?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?" ,"?");
    log.info("Dummy ticket created OK!");
  }
}
