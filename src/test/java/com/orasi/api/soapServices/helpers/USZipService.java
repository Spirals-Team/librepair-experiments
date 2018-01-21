package com.orasi.api.soapServices.helpers;

import com.orasi.api.soapServices.SoapService;

public class USZipService extends SoapService {

    public USZipService() {
        setServiceName("USZipSoap");
        setServiceURL("http://www.webservicex.net/uszip.asmx?wsdl");
    }
}
