#!/bin/bash

#set -x #echo on

COMMAND=${1:-"help"}
HELP=0
API_PARAM=0
ORACLE_PARAM=0
FORCE_PARAM=

function help() {
    if [[ "$1" = "start" ]]; then
        echo "API Template script help: Start"
        echo
        echo "Syntax: ./deploy.sh start [service] [options]"
        echo
        echo "service:"
        echo "api       Rebuild the API's image"
        echo "oracle    Rebuild the Oracle's image"
        echo
        echo "options:"
        echo "--force   Force building and recreating the containers"
    elif [[ "$1" = "update" ]]; then
        echo "API Template script help: Update"
        echo
        echo "Syntax: ./deploy.sh update [service]"
        echo
        echo "service:"
        echo "api        Update container with a new API package"
        echo
    elif [[ "$1" = "build" ]]; then
        echo "API Template script help: Build"
        echo
        echo "Syntax: ./deploy.sh build [service]"
        echo
        echo "service:"
        echo "api        Rebuild the API's image"
        echo "oracle     Rebuild the Oracle's image"
        echo
    else
        echo "API Template script help"
        echo
        echo "Syntax: ./deploy.sh [command] [options]"
        echo
        echo
        echo "commands:"
        echo "build      Rebuild a service's image"
        echo "delete     Deletes the template containers"
        echo "help       Show help information"
        echo "stop       Stops the template containes"
        echo "start      Starts the template containers"
        echo "update     Update running container without re-building"
        echo
    fi
}

function start() {
    if [[ $HELP = 1 ]]; then
        help "start"
    elif [[ $API_PARAM = 1 ]]; then 
        docker-compose --file docker-compose.yml --project-name api-template up --remove-orphans --detach $FORCE_PARAM api
    elif [[ $ORACLE_PARAM = 1 ]]; then 
        docker-compose --file docker-compose.yml --project-name api-template up --remove-orphans --detach $FORCE_PARAM oracle
    else
        docker-compose --file docker-compose.yml --project-name api-template up --remove-orphans --detach $FORCE_PARAM
    fi
}

function stop() {
    if [[ $HELP = 1 ]]; then
        help "stop"
    else
        docker-compose --project-name api-template stop
    fi
}

function delete() {
    if [[ $HELP = 1 ]]; then
        help "delete"
    else
        docker-compose --project-name api-template down
    fi
}

function update() {
    if [[ $API_PARAM = 1 ]]; then
        docker cp ../build/libs/*.war $(docker-compose --project-name api-template ps -q api):/opt/jboss/wildfly/standalone/deployments/api.war
    else
        help "update"
    fi
}

function build() {
    if [[ $API_PARAM = 1 ]]; then 
        docker-compose --file docker-compose.yml --project-name api-template build api
    elif [[ $ORACLE_PARAM = 1 ]]; then 
        docker-compose --file docker-compose.yml --project-name api-template build oracle
    else
        help "build"
    fi
}

while (( "$#" )); do
    case $1 in
        --help)
        HELP=1
        ;;
        --force)
        FORCE_PARAM="--force-recreate --build"
        ;;
        api)
        API_PARAM=1
        ;;
        oracle)
        ORACLE_PARAM=1
        ;;
    esac
    shift
done

if [ $COMMAND = "start" ]; then
	start
elif [ $COMMAND = "stop" ]; then
	stop
elif [ $COMMAND = "delete" ]; then
	delete
elif [ $COMMAND = "update" ]; then
	update
elif [ $COMMAND = "build" ]; then
	build
else
   help
fi
