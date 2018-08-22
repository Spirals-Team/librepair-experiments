#!/bin/bash -eu

cd /tls


export DOCKER_IP=$(cat /docker_ip)
export MY_HOSTNAME=$(hostname)
export MY_IP=$(hostname -i)

cat <<EOT > csr.conf
[req]
default_bits = 1024
prompt = no
default_md = sha256
req_extensions = req_ext
distinguished_name = dn

[ dn ]
C=DR
ST=DR
L=Druid City
O=Druid
OU=IntegrationTests
emailAddress=integration-test@druid.io
CN = ${MY_IP}

[ req_ext ]
subjectAltName = @alt_names

[ alt_names ]
IP.1 = ${DOCKER_IP}
IP.2 = ${MY_IP}
IP.3 = 127.0.0.1
DNS.1 = ${MY_HOSTNAME}
DNS.2 = localhost

EOT

# Generate a server certificate for this machine
openssl genrsa -out server.key 1024 -sha256
openssl req -new -out server.csr -key server.key -reqexts req_ext -config csr.conf
openssl x509 -req -days 3650 -in server.csr -CA root.pem -CAkey root.key -set_serial 0x1234 -out server.pem -sha256 -extfile csr.conf -extensions req_ext

# Create a Java keystore containing the generated certificate
openssl pkcs12 -export -in server.pem -inkey server.key -out server.p12 -name druid -CAfile root.pem -caname druid-it-root -password pass:druid123
keytool -importkeystore -srckeystore server.p12 -srcstoretype PKCS12 -destkeystore server.jks -deststoretype JKS -srcstorepass druid123 -deststorepass druid123

# Create a Java truststore with the imply test cluster root CA
keytool -import -alias druid-it-root -keystore truststore.jks -file root.pem -storepass druid123 -noprompt
