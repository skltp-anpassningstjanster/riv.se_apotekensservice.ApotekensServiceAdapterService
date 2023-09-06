package se.skltp.adapterservices.apse.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import se.skltp.adapterservices.apse.config.SecurityProperties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import java.security.cert.X509Certificate;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
public class CustomHostnameVerifier implements HostnameVerifier {
    private final SecurityProperties securityProperties;
    private final HostnameVerifier defaultHostnameVerifier;

    public CustomHostnameVerifier(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
        this.defaultHostnameVerifier = new DefaultHostnameVerifier();
    }
    public CustomHostnameVerifier(SecurityProperties securityProperties, HostnameVerifier defaultHostnameVerifier) {
        this.securityProperties = securityProperties;
        this.defaultHostnameVerifier = defaultHostnameVerifier;
    }

    @Override
    public boolean verify(String hostname, SSLSession session) {
            AtomicBoolean returnValue = new AtomicBoolean(false);
            boolean defaultVerifyResponse = defaultHostnameVerifier.verify(hostname, session);
            if (defaultVerifyResponse) {
                returnValue.set(true);
            } else {
                X509Certificate pc;
                String subj = "----";
                try {
                    pc = (X509Certificate) session.getPeerCertificates()[0];
                    subj = pc.getSubjectDN().getName();
                } catch (SSLPeerUnverifiedException e) {
                    log.error("SSLPeerUnverifiedException");
                }
                log.warn(String.format("Hostname verification failed, the host \"%s\" presented a certificate with subject: \"%s\"", hostname, subj));
                if (securityProperties.getStore().getConsumer().getHostNameVerifySkipList() != null) {
                    securityProperties.getStore().getConsumer().getHostNameVerifySkipList().forEach(
                            skipMe -> {
                                if (skipMe.equals(hostname)) {
                                    log.error("Despite a failed host name verification the host was accepted. this should not be allowed outside a development environment");
                                    returnValue.set(true);
                                }
                            }
                    );
                }
            }
            return returnValue.get();
        }
    }