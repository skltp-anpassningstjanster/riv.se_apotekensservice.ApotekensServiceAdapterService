package se.skltp.adapterservices.apse.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import se.skltp.adapterservices.apse.config.SecurityProperties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.cert.X509Certificate;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
public class CustomHostnameVerifier implements HostnameVerifier {
    private final SecurityProperties securityProperties;

    public CustomHostnameVerifier(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties; 
    }

    @Override
        public boolean verify(String hostname, SSLSession session) {
            AtomicBoolean returnValue = new AtomicBoolean(false);
            DefaultHostnameVerifier defaultHostnameVerifier = new DefaultHostnameVerifier();
            boolean defaultVerifyResponse = defaultHostnameVerifier.verify(hostname, session);
            if (defaultVerifyResponse) {
                returnValue.set(true);
            } else {
                X509Certificate[] pc;
                String subj = "----";
                try {
                    pc = session.getPeerCertificateChain();
                    subj = pc[0].getSubjectDN().getName();
                } catch (SSLPeerUnverifiedException e) {
                    e.printStackTrace();
                }
                log.warn(String.format("Hostname verification failed, the host \"%s\" presented a certificate with subject: \"%s\"", subj, hostname, subj));
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