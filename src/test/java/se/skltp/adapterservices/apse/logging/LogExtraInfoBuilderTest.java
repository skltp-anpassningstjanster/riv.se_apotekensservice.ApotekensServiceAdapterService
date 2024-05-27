package se.skltp.adapterservices.apse.logging;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class LogExtraInfoBuilderTest {

  @Test
  void filterHeaderValueTest() {
    Map<String, Object> headers = new HashMap<>();
    headers.put("x-vp-auth-cert", "This-should-be-filtered");
    headers.put("x-fk-auth-cert", "This-should-be-filtered");
    headers.put("X-Fk-Auth-Cert", "This-should-be-filtered");
    headers.put("X-Some-Header", "This-should-not-be-filtered");

    assertEquals(LogExtraInfoBuilder.FILTERED_TEXT, LogExtraInfoBuilder.filterHeaderValue("x-vp-auth-cert", headers));
    assertEquals(LogExtraInfoBuilder.FILTERED_TEXT, LogExtraInfoBuilder.filterHeaderValue("x-fk-auth-cert", headers));
    assertEquals(LogExtraInfoBuilder.FILTERED_TEXT, LogExtraInfoBuilder.filterHeaderValue("X-Fk-Auth-Cert", headers));
    assertEquals("This-should-not-be-filtered", LogExtraInfoBuilder.filterHeaderValue("X-Some-Header", headers));
  }
}