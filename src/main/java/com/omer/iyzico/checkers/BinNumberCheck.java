package com.omer.iyzico.checkers;

import com.iyzipay.Options;
import com.iyzipay.model.BinNumber;
import com.iyzipay.request.RetrieveBinNumberRequest;

public class BinNumberCheck {
	
	Options options;
	
	public BinNumberCheck() {
		options = new Options();
        options.setApiKey("sandbox-WJQ1HcphuA9cesK1quRqOko6tBneuy46");
        options.setSecretKey("sandbox-O5HjgLsjdf9iUSOj9FGI03TLVzmni9sA");
        options.setBaseUrl("https://sandbox-api.iyzipay.com");
	}

	public BinNumber checkBinNumber(String binNumber) {

		RetrieveBinNumberRequest request = new RetrieveBinNumberRequest();
//		request.setLocale(Locale.TR.getValue());
//		request.setConversationId("123456789");
		request.setBinNumber(binNumber);

		return  BinNumber.retrieve(request, options);
	}
}
