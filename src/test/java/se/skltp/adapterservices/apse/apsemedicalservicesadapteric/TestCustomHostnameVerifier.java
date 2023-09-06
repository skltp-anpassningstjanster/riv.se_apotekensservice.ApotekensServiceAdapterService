package se.skltp.adapterservices.apse.apsemedicalservicesadapteric;

import lombok.extern.log4j.Log4j2;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.skltp.adapterservices.apse.config.SecurityProperties;
import se.skltp.adapterservices.apse.utils.CustomHostnameVerifier;

import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.lenient;
@Log4j2
@ExtendWith(MockitoExtension.class)
public class TestCustomHostnameVerifier {

    @Test
    public void testVerify(@Mock SSLSession sslSession, @Mock HostnameVerifier defaultHostnameVerifier) throws Exception {
        lenient().when(defaultHostnameVerifier.verify("phonyHost", sslSession)).thenReturn(true);
        lenient().when(sslSession.getPeerCertificates()).thenThrow(SSLPeerUnverifiedException.class);

        SecurityProperties secProp = new SecurityProperties();
        secProp.setStore(new SecurityProperties.Store());
        secProp.getStore().setConsumer(new SecurityProperties.Store.Consumer());
        secProp.getStore().getConsumer().setHostNameVerifySkipList(List.of(new String[]{"localhost"}));

        CustomHostnameVerifier customHostnameVerifier = new CustomHostnameVerifier(secProp, defaultHostnameVerifier);
        assertThat(customHostnameVerifier.verify("localhost", sslSession)).isTrue();
        assertThat(customHostnameVerifier.verify("phonyHost", sslSession)).isTrue();
        assertThat(customHostnameVerifier.verify("fakeHost", sslSession)).isFalse();
    }
}
