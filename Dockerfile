FROM eclipse-temurin:11

ENV BASE_DIR=/opt/apse \
    APPJAR=/opt/apse/apse-adapter.jar \
    APPUSER=ind-app \
    LOG_DIR=/var/log/camel \
    RESET_CACHE_SCRIPT=/usr/local/bin/resetCaches.sh \
    APSE_LOG_APPENDER=EcsLayout


RUN mkdir -p ${BASE_DIR} ${LOG_DIR} \
 && useradd -Ms /bin/bash -b ${BASE_DIR} -u 556559423 ${APPUSER} \
 && chown ${APPUSER}:${APPUSER} -R ${BASE_DIR} ${LOG_DIR} \
 && chown root:${APPUSER} -R /etc/pki/ca-trust/extracted/ \
 && chmod g+w -R /etc/pki/ca-trust/extracted/ \
 && cat /etc/pki/tls/certs/ca-bundle.crt | sed  '/DigiCert Global Root G2/,/----END/d' > /etc/pki/ca-trust/source/blacklist/all_but_digicert_gr2.pem

WORKDIR ${BASE_DIR}
#USER ${APPUSER}

ADD target/apse-adapter-*.jar ${APPJAR}
CMD update-ca-trust \
 && java -XX:MaxRAMPercentage=75 ${JAVA_OPTS} -jar ${APPJAR} ${CONFIG_FILE_PARAM}
