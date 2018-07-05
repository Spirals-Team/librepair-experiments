FROM websphere-liberty:latest

RUN installUtility install --acceptLicense \
    jwt-1.0
ADD server.xml /opt/ibm/wlp/usr/servers/defaultServer/server.xml
ADD phonebook.war /opt/ibm/wlp/usr/servers/defaultServer/apps/phonebook.war