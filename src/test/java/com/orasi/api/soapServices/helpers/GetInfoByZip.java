package com.orasi.api.soapServices.helpers;

import java.io.File;

import javax.xml.soap.SOAPConstants;

import com.orasi.utils.XMLTools;

public class GetInfoByZip extends USZipService {
    public GetInfoByZip() {
        File xml = new File(this.getClass().getResource("/xmls/getInfoByZip.xml").getPath());
        setRequestDocument(XMLTools.makeXMLDocument(xml));

        // Generate a request from a project xml file
        setOperationName("GetInfoByZIP");
        removeComments();
        removeWhiteSpace();
    }

    public GetInfoByZip(String scenario, String fileType) {
        File xml = new File(this.getClass().getResource("/xmls/getInfoByZip.xml").getPath());
        setRequestDocument(XMLTools.makeXMLDocument(xml));
        switch (fileType.toLowerCase()) {
            case "csv":
                setRequestNodeValueByXPath(getTestScenario("/excelsheets/GetInfoByZipRequest_csv.csv", scenario));
                break;

            case "xls":
                setRequestNodeValueByXPath(getTestScenario("/excelsheets/GetInfoByZipRequest_xls.xls", scenario));
                break;

            case "xlsx":
                setRequestNodeValueByXPath(getTestScenario("/excelsheets/GetInfoByZipRequest_xlsx.xlsx", scenario));
                break;

            default:
                break;
        }

        // Generate a request from a project xml file
        setOperationName("GetInfoByZIP");
        removeComments();
        removeWhiteSpace();
    }

    public void setZip(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", value);
    }

    public int getNumberOfResults() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/GetInfoByZIPResponse/GetInfoByZIPResult/NewDataSet/Table");
    }

    public String getRequestZip() {
        return getRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip");
    }

    public void setSoapVersion() {
        setSoapVersion(SOAPConstants.SOAP_1_2_PROTOCOL);
    }
}
