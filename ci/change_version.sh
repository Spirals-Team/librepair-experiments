#!/bin/bash

# changing version in pom and all its children
# Arguments:
#   $1: directory of pom
#   $2: new version
function change_version {
  mvn versions:set -f "$1" -DnewVersion="$2"   -DartifactId=*  -DgroupId=* versions:commit
}

if [[ -z "$1" ]]; then
  echo "missing directory" >&2
  exit 1;
fi

[[ "$2" =~ ^v[0-9]\.[0-9]+\.[0-9]+$ ]] && change_version "$1" ${2##v}

