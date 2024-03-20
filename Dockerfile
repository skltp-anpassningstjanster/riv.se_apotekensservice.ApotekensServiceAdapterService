FROM eclipse-temurin:11-jre-alpine

ENV BASE_DIR=/opt/apse \
    APPJAR=/opt/apse/apse-adapter.jar \
    APPUSER=ind-app \
    LOG_DIR=/var/log/camel \
    RESET_CACHE_SCRIPT=/usr/local/bin/resetCaches.sh \
    APSE_LOG_APPENDER=EcsLayout \
    JAVA_CACERTS=${JAVA_HOME}/lib/security/cacerts

RUN mkdir -p ${BASE_DIR} ${LOG_DIR} \
 && adduser -HD -u 1000 -h ${BASE_DIR} ${APPUSER} \
 && touch ${JAVA_CACERTS} \
 && mkdir /certificates \
 && cp /usr/share/ca-certificates/mozilla/DigiCert_Global_Root_G2.crt /usr/local/share/ca-certificates/ \
 && rm /usr/share/ca-certificates/mozilla/*.crt  \
 && echo -n > /etc/ca-certificates.conf \
 && update-ca-certificates \
 && trust extract --overwrite --format=java-cacerts --filter=ca-anchors --purpose=server-auth ${JAVA_CACERTS} \
 && chown ${APPUSER}:${APPUSER} -R ${BASE_DIR} ${LOG_DIR} ${JAVA_CACERTS}


WORKDIR ${BASE_DIR}
USER ${APPUSER}

ADD target/apse-adapter-*.jar ${APPJAR}

COPY <<EOF /run.sh
chmod +w $JAVA_CACERTS
for cacert in $(find /cacerts/ -type f 2>/dev/null)
do
  keytool -import -trustcacerts -alias $(basename \${cacert}) -file \$cacert -keystore $JAVA_CACERTS -storepass changeit -noprompt
done
chmod -w $JAVA_CACERTS
exec java -XX:MaxRAMPercentage=75 ${JAVA_OPTS} -jar ${APPJAR} ${CONFIG_FILE_PARAM}
EOF

CMD ["sh", "/run.sh"]
