#!/bin/bash
set -e # fail fast

if [[ ! "$1" =~ ^v[0-9]+\.[0-9]+\.[0-9]+$ ]]; then 
  echo "missing tag" >&2 
  exit 1
fi

if [[ ! -e "$2" ]]; then 
  echo "'$2' is not a file" >&2 
  exit 1
fi

MANIFEST_PREFIX="target/taskana-rest-spring-example"

sed -i "s|$MANIFEST_PREFIX.*\.jar|$MANIFEST_PREFIX-${1##v}.jar|" "$2"
