#!/usr/bin/env bash

set -e

if [[ "$1" -eq "clean" ]]; then
  mvn clean
fi

mvn package -DskipTests
docker build . --rm -t phonebook:1.0.0
docker-compose up -d --remove-orphans
