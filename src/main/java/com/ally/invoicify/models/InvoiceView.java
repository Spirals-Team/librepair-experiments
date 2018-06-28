package com.ally.invoicify.models;

public class InvoiceView {
	
	private String invoiceDescription;
	private long[] recordIds;

	public InvoiceView(){
		
	}
	
	public InvoiceView(String invoiceDescription, long[] recordIds) {
		this.invoiceDescription = invoiceDescription;
		this.recordIds = recordIds;
	}

	public String getInvoiceDescription() {
		return invoiceDescription;
	}

	public long[] getRecordIds() {
		return recordIds;
	}
	
	
	
}
