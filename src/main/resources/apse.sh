#!/bin/bash

# chkconfig: 345 90 60
# description: ApSe startup script

# Make sure we run as the correct user
RUN_AS=${RUN_AS:-ind-app}
CURRENT_USER=$(id -nu)
BASE_DIR="/opt/anp/apse"
LOG_DIR="/var/log/camel/apse"
LOG4J="${BASE_DIR}/log4j2.xml"
JAR="${BASE_DIR}/apse.jar"
PROPERTIES="${BASE_DIR}/application.properties"
SERVICE="ApSe"


#JMX_PORT="8084"

if [ ! $CURRENT_USER == $RUN_AS ]; then
    if [ $CURRENT_USER == "root" ]; then
        exec su - $RUN_AS -c "$0 $@"
        exit $?
    fi
    echo "Script must be run as $RUN_AS or root. You are '$CURRENT_USER'"
    exit 1
fi


# OK, now we act as $RUN_AS
PERMOREXS=false
for f in $BASE_DIR $LOG4J $PROPERTIES $JAR
do 
    if ! test -r $f
    then
        PERMOREXS=true
        echo "$(whoami) don't have read permission on: $f, or file/dir doesn't exists"
    fi
done
if ! test -w $LOG_DIR
then
    PERMOREXS=true
    echo "--- $(whoami) don't have write permission on: $LOG_DIR, or file/dir doesn't exists"
fi
if ${PERMOREXS}
then
    exit 2
fi


cd $BASE_DIR

export JAVA_HOME=${JAVA_HOME:-$(readlink -f $(which java)|sed 's,/bin/java$,,')}
JAVA_CMD="$JAVA_HOME/bin/java"
JAVA_OPTS="-Dfile.encoding=UTF-8 -Duser.country=SE -Duser.language=sv -Dspring.import.config=application.properties -Xms512m -Xmx1536m -Dlog4j.configurationFile=file://${LOG4J}"

JMX_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.rmi.port=$JMX_PORT -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
GC_LOG="-XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:$LOG_DIR/gc/gc-%t.log"
GC_OPTS="-XX:MaxMetaspaceSize=256m -XX:CompressedClassSpaceSize=256m"

START_COMMAND="$JAVA_CMD -jar $JAVA_OPTS $GC_OPTS $JAR"
#START_COMMAND="$JAVA_CMD -jar $JAVA_OPTS $GC_OPTS $GC_LOG $JMX_OPTS $JAR"
PID_FILE=${BASE_DIR}/${SERVICE}.pid
SHUTDOWN_WAIT=30; # before issuing kill -9 on process.

PID=$(test -f $PID_FILE && cat $PID_FILE)
test -z "$PID" && ps h --pid $PID 2>/dev/null && IS_RUNNING=true || IS_RUNNING=false

start() {
  # Run the prestart script if it exists
  test -x $BASE_DIR/prestart && $BASE_DIR/prestart
   
  echo "${SERVICE} starting up..."
  PID=$(${START_COMMAND} \
            > ${LOG_DIR}/init.log 
            2>${LOG_DIR}/init.error.log \
            & echo $!)
  
  # Run the poststart script if it exists
  test -x $BASE_DIR/poststart && $BASE_DIR/poststart
}

waitOnShutdown() {
    kwait=${SHUTDOWN_WAIT};
    pid=$1 
    count=0;
    echo -n "Waiting for ${SERVICE} process [${pid}] to shutdown."  
    while kill -0 ${pid} 2>/dev/null && [ ${count} -le ${kwait} ]
    do 
      printf ".";
      sleep 1;
      (( count++ ));
    done;
    echo
  
}
 
killHarder() {
    kwait=${SHUTDOWN_WAIT};
    pid=$1 
    count=0;
    
    printf "${SERVICE} process is still running after %d seconds, killing process" \
    ${SHUTDOWN_WAIT};
    kill ${pid};
    sleep 3;

    # if it's still running use kill -9
    if kill -0 ${pid} 2>/dev/null; then {
        echo "${SERVICE} process is still running, using kill -9";
        kill -9 ${pid}
        sleep 3;
    } fi;
    
    if kill -0 ${pid} 2>/dev/null; then {
        echo "${SERVICE} process is still running, I give up";
        exit 1;	
    } fi;

    echo "${SERVICE} process [$pid] killed hard"
  
}
 
stop() {
    # Run the prestop script if it exists
    test -x $BASE_DIR/prestop && $BASE_DIR/prestop

    PID=$1
    kill -term $PID
    echo "Stopping ${SERVICE} [$PID]"
    waitOnShutdown $PID
    
    if kill -0 $PID 2>/dev/null; then {
      killHarder $PID
    } 
    else {
      echo "Stopped ${SERVICE} [$PID] succesfully"
    } fi;
    
    rm -f $PID_FILE
}

trapped() {
    stop $(cat $PID_FILE)
    exit 0
}

run() {
 # Run the prestart script if it exists
  test -x $BASE_DIR/prestart && $BASE_DIR/prestart

  echo "${SERVICE} starting up..."
  ${START_COMMAND} &
  PID=$!
  echo $PID > $PID_FILE
  # Run the poststart script if it exists
  test -x $BASE_DIR/poststart && $BASE_DIR/poststart

  trap trapped EXIT
  wait $PID
  rm -f $PID_FILE
}


case "$1" in
start)
    if [ -f $PID_FILE ]; then
        PID=`cat $PID_FILE`
        if [ -z "`ps axf | grep ${PID} | grep -v grep`" ]; then
            start
        else
            echo "${SERVICE} already running [$PID]"
            exit 0
        fi
    else
        start
    fi
 
    if [ -z $PID ]; then
        echo "Failed starting"
        exit 1
    else
        echo $PID > $PID_FILE
        echo "${SERVICE} started [pid $PID]"
        exit 0
    fi
;;
run)
    if $IS_RUNNING
    then
        echo "${SERVICE} already running [$PID]"
        exit 0
    fi
    run
;;
status)
    if [ -f $PID_FILE ]; then
        PID=`cat $PID_FILE`
        if [ -z "`ps axf | grep ${PID} | grep -v grep`" ]; then
            echo "${SERVICE} not running (process dead but PID file exists)"
            exit 1
        else
            echo "${SERVICE} running [pid $PID]"
            exit 0
        fi
    else
        echo "${SERVICE} not running"
        exit 0
    fi
;;
stop)
    if [ -f $PID_FILE ]; then
        PID=`cat $PID_FILE`
        if [ -z "`ps axf | grep ${PID} | grep -v grep`" ]; then
            echo "${SERVICE} not running (process dead but PID file exists)"
            rm -f $PID_FILE
            exit 1
        else
            stop ${PID}
            exit 0
        fi
    else
        echo "${SERVICE} not running (PID not found)"
        exit 0
    fi
;;
restart)
    $0 stop
    sleep 1
    $0 start
;;
*)
    echo "Usage: $0 {status|start|stop|restart}"
    exit 0
esac

