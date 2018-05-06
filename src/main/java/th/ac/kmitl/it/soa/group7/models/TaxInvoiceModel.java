package th.ac.kmitl.it.soa.group7.models;

public class TaxInvoiceModel {// Collect Tax Invoice details
	public TaxInvoiceModel() {
	}

	private String documentTypeCode;

	public void setDocumentTypeCode(String documentTypeCode) {// Set typeCode
		this.documentTypeCode = documentTypeCode;
	}

	public String getDocumentTypeCode() {// Get typeCode
		return documentTypeCode;
	}
}
