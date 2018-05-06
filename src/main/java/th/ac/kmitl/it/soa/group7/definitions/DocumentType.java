package th.ac.kmitl.it.soa.group7.definitions;


public class DocumentType { //Defines DocumentTypeCode
	//TODO Rewrite to enum
	
	DocumentType(String typeCode, String description){}
	
	public static final String[] DEBIT_NOTE =
								{"80", "ใบเพิ่มหนี้ (Debit note)"},
								CREDIT_NOTE =
								{"81", "ใบลดหนี้ (Credit note)"},
								INVOICE =
								{"380", "ใบแจ้งหนี้ (Invoice)"},
								TAX_INVOICE =
								{"388", "ใบกำกับภาษี (Tax Invoice)"},
								RECEIPT =
								{"T01", "ใบรับ (Receipt)"},
								RECEIPT_TAX_INVOICE =
								{"T03", "ใบเสร็จรับเงิน/ใบกำกับภาษี (Receipt/ Tax Invoice)"},
								DELIVERY_ORDER_TAX_INVOICE =
								{"T04", "ใบส่งของ/ใบกำกับภาษี (Delivery order/ Tax Invoice)"},
								Abbreviated_TAX_INVOICE =
								{"T05", "ใบกำกับภาษีอย่างย่อ (Abbreviated Tax Invoice)"},
								RECEIPT_ABBREVIATED_TAX_INVOICE = 
								{"T06", "ใบเสร็จรับเงิน/ใบกำกับภาษีอย่างย่อ (Receipt/Abbreviated Tax Invoice)"},
								CANCELLATION_NOTE = {"T07", "ใบแจ้งยกเลิก (Cancellation note)"};

	
}
