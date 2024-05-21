FROM maven:3.9-eclipse-temurin-17 AS maven

RUN mkdir -p /opt/build
WORKDIR  /opt/build
ADD pom.xml /opt/build/
ADD src/ /opt/build/src

RUN --mount=type=secret,id=maven,target=/root/.m2/settings.xml \
    --mount=type=cache,target=/root/.m2/repository mvn clean install

FROM eclipse-temurin:17-jre-alpine

ENV BASE_DIR=/opt/apse \
    APPJAR=/opt/apse/apse-adapter.jar \
    APPUSER=ind-app \
    RESET_CACHE_SCRIPT=/usr/local/bin/resetCaches.sh \
    APSE_LOG_APPENDER=EcsLayout \
    JAVA_CACERTS=${JAVA_HOME}/lib/security/cacerts

RUN mkdir -p ${BASE_DIR} \
 && adduser -HD -u 1000 -h ${BASE_DIR} ${APPUSER} \
 && cp /usr/share/ca-certificates/mozilla/DigiCert_Global_Root_G2.crt /usr/local/share/ca-certificates/ \
 && rm /usr/share/ca-certificates/mozilla/*.crt  \
 && echo -n > /etc/ca-certificates.conf \
 && update-ca-certificates \
 && trust extract --overwrite --format=java-cacerts --filter=ca-anchors --purpose=server-auth ${JAVA_CACERTS} \
 && chown ${APPUSER}:${APPUSER} -R ${BASE_DIR} ${JAVA_CACERTS}


WORKDIR ${BASE_DIR}
USER ${APPUSER}

COPY --from=maven /opt/build/target/apse-adapter-*.jar ${APPJAR}

COPY <<EOF /run.sh
chmod +w $JAVA_CACERTS
for cacert in $(find /cacerts/ -type f 2>/dev/null)
do
  echo "adding \$cacert to java truststore"
  keytool -import -trustcacerts -alias $(basename \${cacert}) -file \$cacert -cacerts -storepass changeit -noprompt
done
chmod -w $JAVA_CACERTS
exec java \${JAVA_OPTS} -jar \${APPJAR} \${CONFIG_FILE_PARAM}
EOF

CMD ["sh", "/run.sh"]
