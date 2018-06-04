#!/bin/bash
set -e #fail fast

# loads image from cache (if existing) and starts it.
# If the image is not in the cache it will be downloaded.
#
# Arguments:
# $1: docker image id
# $2: image url
# $3: property file
#
function startDb2Image {
  docker run -d -p 50000:50000 "$1" -d
  docker ps
  echo 'jdbcDriver=com.ibm.db2.jcc.DB2Driver' > $2
  echo 'jdbcUrl=jdbc:db2://localhost:50000/tskdb' >> $2
  echo 'dbUserName=db2inst1' >> $2
  echo 'dbPassword=db2inst1-pwd' >> $2
}


if [[ -z "$1" ]]; then
  echo "database is missing" >&2
  exit 1
fi

case "$1" in 
  H2)
    ;;
  DB2_10_5)
    startDb2Image mustaphazorgati/db2:10.5 "$HOME/taskanaUnitTest.properties" 
    ;;
  DB2_11_1)
    startDb2Image mustaphazorgati/db2:11.1 "$HOME/taskanaUnitTest.properties" 
    ;;
  *)
    echo "unknown database '$1'" >&2
    exit 1
esac

