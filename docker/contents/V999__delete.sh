#!/bin/bash

set -x #echo on

rm -rf /docker-entrypoint-initdb.d/*.sql
rm -rf /docker-entrypoint-initdb.d/*.sh