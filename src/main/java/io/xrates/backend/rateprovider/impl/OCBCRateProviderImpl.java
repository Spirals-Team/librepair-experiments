package io.xrates.backend.rateprovider.impl;

import io.xrates.backend.constants.RateProviderDetails;
import io.xrates.backend.rateprovider.AbstractRateProvider;

import java.io.IOException;
import java.util.Currency;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OCBCRateProviderImpl extends AbstractRateProvider{
	private String resourceUrl = "https://www.ocbc.com/rates/daily_price_fx.html";
//	private Double toSGD = 0.0;
	private Logger log = LoggerFactory.getLogger(OCBCRateProviderImpl.class.getName());
	
	private String fromCurrency = "INR";
	
	public OCBCRateProviderImpl() {
		setRateProviderDetails(RateProviderDetails.OCBC);
	}
	
	@Override
	protected void updateRates() {
		updateCurrencyList();
		try {
			fetchRates();
		} catch (IOException e) {
//			TODO: Implement for specific Exceptions
			System.err.println("There was an error fetching the currency rates");
		}
	}
	
	private void fetchRates() throws IOException{
		log.info("OCBS: Fetching page @ " + resourceUrl);
		Document doc = Jsoup.connect(resourceUrl).get();
		Element ratesTable = doc.select(".MsoNormalTable").get(0);
		getRateForInputCurrency(fromCurrency, ratesTable);
	}
	
	
	private void getRateForInputCurrency(String fromCurrency, Element ratesTable){
		int tableLength = ratesTable.select("tr").size();
		
		for (int i = 5; i < tableLength; i++) {
			try{
				Element tableRow = ratesTable.select("tr").get(i);
				Elements tableData = tableRow.select("td");
				String unit = tableData.get(1).text();
				int unitValue = Integer.parseInt(unit.split(" ")[0]);
				String unitCurrency = unit.split(" ")[1];
				float sellingRate = Float.parseFloat(tableData.get(3).text());
				if(unitCurrency.equals(fromCurrency)){
					double toSgd = sellingRate/unitValue;
					double toInr = unitValue/sellingRate;				
					
					rates.setConversion(Currency.getInstance("SGD"), Currency.getInstance("INR"), toInr);
					rates.setConversion(Currency.getInstance("INR"), Currency.getInstance("SGD"), toSgd);
					
				}
			}catch(IndexOutOfBoundsException e){
				
			}catch(NumberFormatException e){
				
			}

		}
	}
	
	private void updateCurrencyList() {
		rates.addAvailableCurrency(Currency.getInstance("INR"));
		rates.addAvailableCurrency(Currency.getInstance("SGD"));
	}
	
}
