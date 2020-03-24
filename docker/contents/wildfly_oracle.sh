#!/bin/bash

set -x #echo on

# Usage: execute.sh [WildFly mode] [configuration file]
#
# The default mode is 'standalone' and default configuration is based on the
# mode. It can be 'standalone.xml' or 'domain.xml'.

JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=${1:-"standalone"}
#JBOSS_CONFIG=${2:-"$JBOSS_MODE.xml"}

function wait_for_server() {
  until `$JBOSS_CLI -c "ls /deployment" &> /dev/null`; do
    sleep 1
  done
}

echo "=> Downloading jdbc driver"
curl https://maven.ceon.pl/artifactory/repo/com/oracle/ojdbc/ojdbc8/19.3.0.0/ojdbc8-19.3.0.0.jar --output $JBOSS_HOME/ojdbc8.jar

echo "=> Starting WildFly server"
#$JBOSS_HOME/bin/$JBOSS_MODE.sh -c $JBOSS_CONFIG > /dev/null &
$JBOSS_HOME/bin/$JBOSS_MODE.sh > /dev/null &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Executing the commands"
$JBOSS_CLI -c --file=`dirname "$0"`/oracle.cli

echo "=> Shutting down WildFly"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=*:shutdown"
fi

rm -rf /opt/jboss/wildfly/standalone/configuration/standalone_xml_history/current/*