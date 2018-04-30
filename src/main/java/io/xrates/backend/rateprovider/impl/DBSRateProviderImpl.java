/*
 * This is an implementation of RateProvider interface
 * implementing the currency conversion method for bank
 * DBS
 * */

package io.xrates.backend.rateprovider.impl;
import io.github.siddharthgoel88.useragents.impl.Feku;
import io.xrates.backend.constants.RateProviderDetails;
import io.xrates.backend.rateprovider.AbstractRateProvider;
import io.xrates.backend.rateprovider.util.CurrencyAdapter;

import java.io.IOException;
import java.util.Currency;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DBSRateProviderImpl extends AbstractRateProvider {
	private String resourceURL = "http://www.dbs.com.sg/personal/rates-online/foreign-currency-foreign-exchange.page";
	private Logger log = LoggerFactory.getLogger(DBSRateProviderImpl.class.getName());
	private Currency base = Currency.getInstance("SGD");
	
	public DBSRateProviderImpl() {
		setRateProviderDetails(RateProviderDetails.DBS);
	}
	
	@Override
	protected void updateRates() {
		log.debug("Inside updateRates of DBSRateProviderImpl");
		try {
			log.info("DBS: Fetching page @ " + resourceURL);
			Document doc = getDocument();
			extractRates(doc.select("div.filterHld").get(0).select("table tbody").get(0));
			extractRates(doc.select("div.filterHld").get(1).select("table tbody").get(0));
		} catch (IOException e) {
			log.error("DBS: fetching page caused some error !");
			e.printStackTrace();
		}	
	}
	
	private Document getDocument() throws IOException {
		Document doc = Jsoup.connect(resourceURL)
						.userAgent(Feku.getChrome().getLatestUserAgent())
						.referrer("https://www.google.com/")
						.get();
		return doc;
	}
	
	private void extractRates(Element table) {
		int length = table.select("tr").size();
		for (int i=0; i<length; i++) {
			Currency toCur = getCurrency(table, i);
			if (toCur == null) {
				continue;
			}
			int unit = getUnit(table, i);
			double rate = getRate(table, i);
			double perRate = (rate > 0) ? (unit/rate) : -1;
			
			rates.addAvailableCurrency(toCur);
			rates.setConversion(base, toCur, perRate);
			
			log.info(toCur.getCurrencyCode() + " : " + unit + " units @ " + rate);
		}
	}
	
	private double getRate(Element table, int index) {
		double rate = Double.parseDouble(table.select("tr").get(index).select("td").get(0).text());
		return rate;
	}

	private int getUnit(Element table, int index) {
		int unit = Integer.parseInt(table.select("tr").get(index).select("th").get(1).text());
		return unit;
	}

	private Currency getCurrency(Element table, int index) {
		String currencyName  = table.select("tr").get(index).select("th").get(0).text().replace("\u00a0", "");
		return CurrencyAdapter.getInstance(currencyName);
	}
}
