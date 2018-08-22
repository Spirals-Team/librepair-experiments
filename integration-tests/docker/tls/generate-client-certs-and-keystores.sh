#!/bin/bash -eu

mkdir -p client_tls
rm -f client_tls/*
cp docker/tls/root.key client_tls/root.key
cp docker/tls/root.pem client_tls/root.pem

cd client_tls


export DOCKER_HOST_IP=$(resolveip -s $HOSTNAME)

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
CN = localhost

[ req_ext ]
subjectAltName = @alt_names

[ alt_names ]
IP.1 = ${DOCKER_HOST_IP}
IP.2 = 127.0.0.1
DNS.1 = ${HOSTNAME}
DNS.2 = localhost

EOT

# Generate a client certificate for this machine
openssl genrsa -out client.key 1024 -sha256
openssl req -new -out client.csr -key client.key -reqexts req_ext -config csr.conf
openssl x509 -req -days 3650 -in client.csr -CA root.pem -CAkey root.key -set_serial 0x1234 -out client.pem -sha256 -extfile csr.conf -extensions req_ext

# Create a Java keystore containing the generated certificate
openssl pkcs12 -export -in client.pem -inkey client.key -out client.p12 -name druid -CAfile root.pem -caname druid-it-root -password pass:druid123
keytool -importkeystore -srckeystore client.p12 -srcstoretype PKCS12 -destkeystore client.jks -deststoretype JKS -srcstorepass druid123 -deststorepass druid123

# Create a Java truststore with the imply test cluster root CA
keytool -import -alias druid-it-root -keystore truststore.jks -file root.pem -storepass druid123 -noprompt



# Generate a client cert with an incorrect hostname for testing
cat <<EOT > invalid_hostname_csr.conf
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
CN = thisisprobablynottherighthostname

[ req_ext ]
subjectAltName = @alt_names

[ alt_names ]
DNS.1 = thisisprobablywrongtoo

EOT

openssl genrsa -out invalid_hostname_client.key 1024 -sha256
openssl req -new -out invalid_hostname_client.csr -key invalid_hostname_client.key -reqexts req_ext -config invalid_hostname_csr.conf
openssl x509 -req -days 3650 -in invalid_hostname_client.csr -CA root.pem -CAkey root.key -set_serial 0x1234 -out invalid_hostname_client.pem -sha256 -extfile invalid_hostname_csr.conf -extensions req_ext

# Create a Java keystore containing the generated certificate
openssl pkcs12 -export -in invalid_hostname_client.pem -inkey invalid_hostname_client.key -out invalid_hostname_client.p12 -name druid -CAfile root.pem -caname druid-it-root -password pass:druid123
keytool -importkeystore -srckeystore invalid_hostname_client.p12 -srcstoretype PKCS12 -destkeystore invalid_hostname_client.jks -deststoretype JKS -srcstorepass druid123 -deststorepass druid123
