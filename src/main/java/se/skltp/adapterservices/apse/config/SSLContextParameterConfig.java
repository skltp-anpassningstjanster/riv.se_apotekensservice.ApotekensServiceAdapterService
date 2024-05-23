package se.skltp.adapterservices.apse.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.support.jsse.KeyManagersParameters;
import org.apache.camel.support.jsse.KeyStoreParameters;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.support.jsse.TrustManagersParameters;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Configuration
@ConfigurationProperties(prefix = "apse.client-certificate")
public class SSLContextParameterConfig {

    /**
     * Path to the file that contains the clients x509 PEM encoded certificate chain.
     * If set then outgoing request will use this certifiacte as client certificate when mTLS is requested.
     * If not set, then no client certificate will be used.
     */
    private String PEMCertChainFile;

    /**
     * Path to the file that contains the clients x509 PEM encoded (in PKCS8) private key
     * This has to be set if the PEMCertChainFile is set.
     */
    private String PEMKeyFile;

    /**
     * The password to decrypt an encrypted key (does not seem to be functioning use unencrypted private keys)
     */
    private String PEMKeyPassword;

    /**
     * Path to the file that contains the trusted x509 PEM encoded certificates.
     * If set this will be the only trusted issuers, else it will use the system default.
     */
    String PEMTrustStoreFile;

    public void setSSLContextParameters(HttpComponent httpComponent) {
        try {
            SSLContextParameters sslContextParameters = SSLContextParameterConfig.buildSSLContextParameters(
                    PEMCertChainFile,
                    PEMKeyFile,
                    PEMTrustStoreFile,
                    PEMKeyPassword,
                    "RSA"
            );
            httpComponent.setSslContextParameters(sslContextParameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey loadPrivateKey(String keyPemFilePath, String algorithm) throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalArgumentException, IOException {
        String pemContent = Files.readString(Paths.get(keyPemFilePath));
        Pattern pattern = Pattern.compile("-----BEGIN (.*)PRIVATE KEY-----([^-]*)-----END (.*)PRIVATE KEY-----");
        Matcher matcher = pattern.matcher(pemContent);

        if (matcher.find()) {
            String privateKeyPEM = matcher.group(2).replaceAll("\\s+", "");
            byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            return keyFactory.generatePrivate(keySpec);
        }
        throw new IllegalArgumentException("No private key found in file: " + keyPemFilePath);
    }

    public static List<Certificate> loadCertificates(String pemPath) throws IOException, CertificateException {
        String pemContent = Files.readString(Paths.get(pemPath));
        List<Certificate> certificates = new ArrayList<>();
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        Pattern pattern = Pattern.compile("-----BEGIN CERTIFICATE-----([^-]*)-----END CERTIFICATE-----");
        Matcher matcher = pattern.matcher(pemContent);

        while (matcher.find()) {
            String certPEM = matcher.group(1).replaceAll("\\s+", "");
            byte[] certBytes = Base64.getDecoder().decode(certPEM);
            Certificate certificate = certFactory.generateCertificate(new ByteArrayInputStream(certBytes));
            certificates.add(certificate);
        }
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("No certificates found in file: " + pemPath);
        }
        return certificates;
    }

    public static TrustManagersParameters loadTrustStore(String pemTrustPath) throws Exception {
        List<Certificate> certificates = loadCertificates(pemTrustPath);

        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(null, null);
        int i = 0;
        for (Certificate cert : certificates) {
            trustStore.setCertificateEntry("trust-" + i++, cert);
        }

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        TrustManagersParameters tmp = new TrustManagersParameters();
        for (TrustManager tm : trustManagerFactory.getTrustManagers()) {
            tmp.setTrustManager(tm);
        }
        KeyStoreParameters tsp = new KeyStoreParameters();
        tsp.setResource(pemTrustPath); // Set the trust store resource
        tmp.setKeyStore(tsp);

        return tmp;
    }

    public static SSLContextParameters buildSSLContextParameters(String certPemFilePath, String keyPemFilePath, String trustStorePemFilePath, String keyPassword, String algorithm) throws Exception {
        SSLContextParameters scp = new SSLContextParameters();

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);

        if (notNothing(certPemFilePath)) {

            List<Certificate> certificates = loadCertificates(certPemFilePath);

            // Ladda privata nyckeln och certifikaten
            PrivateKey privateKey = loadPrivateKey(keyPemFilePath, algorithm);

            char[] pwd = keyPassword==null?null:keyPassword.toCharArray();
            keyStore.setKeyEntry("key", privateKey, pwd, certificates.toArray(new Certificate[0]));

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, pwd);

            KeyManagersParameters kmp = new KeyManagersParameters();
            KeyStoreParameters ksp = new KeyStoreParameters();
            ksp.setKeyStore(keyStore);
            kmp.setKeyStore(ksp);
            kmp.setKeyPassword(keyPassword);
            scp.setKeyManagers(kmp);
        }
        if (notNothing(trustStorePemFilePath)) {
            TrustManagersParameters tmp = loadTrustStore(trustStorePemFilePath);
            scp.setTrustManagers(tmp);
        }
        return scp;
    }
    private static boolean notNothing(String nn) {
        return nn != null && !nn.isBlank();
    }
}