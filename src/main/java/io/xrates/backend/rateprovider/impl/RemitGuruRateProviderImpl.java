package io.xrates.backend.rateprovider.impl;

import io.github.siddharthgoel88.useragents.impl.Feku;
import io.xrates.backend.constants.RateProviderDetails;
import io.xrates.backend.rateprovider.AbstractRateProvider;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component
public class RemitGuruRateProviderImpl extends AbstractRateProvider {
	private String resourceUrl = "http://www.remitguru.com/transfer/jsp/getQTStatistics.jsp";
	private Logger log = LoggerFactory.getLogger(RemitGuruRateProviderImpl.class.getName());
	private Currency toCurr = Currency.getInstance("INR");
	private List<String> currDetailList = new ArrayList<>();
	private int amount = 1000;
	
	public RemitGuruRateProviderImpl() {
		setRateProviderDetails(RateProviderDetails.REMIT);
		currDetailList.add("GB~GBP~IN~INR");
		currDetailList.add("SG~SGD~IN~INR");
		currDetailList.add("EU~EUR~IN~INR");
//		currDetailList.add("DE~EUR~IN~INR");
	}
	

	private void setRequestProperties(URLConnection uc){
		uc.setDoOutput(true);
        uc.addRequestProperty("Origin", "http://www.remitguru.com");
        uc.addRequestProperty("Accept-Encoding","gzip, deflate, value");
        uc.addRequestProperty("Accept-Language","en-US,en;q=0.8,hi;q=0.6");
        uc.addRequestProperty("User-Agent", Feku.getFirefox().getLatestUserAgent());
        uc.addRequestProperty("Content-type", "application/x-www-form-urlencoded");
        uc.addRequestProperty("Accept", "/");
        uc.addRequestProperty("Referer","http://www.remitguru.com/");
        uc.addRequestProperty("Cookie", "JSESSIONID=6D03E2BFB55E12FCD5522925EDF7A7D0; __utma=161803967.1062202356.1437661741.1437661741.1437661741.1; __utmc=161803967; __utmz=161803967.1437661741.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided)");
        uc.addRequestProperty("Connection","keep-alive");
	}
	
	
	private URLConnection getUrlConnection(String stringUrl) throws IOException{
        URL urlObj = new URL(stringUrl);
        URLConnection uc = urlObj.openConnection();
        setRequestProperties(uc);
        return uc;
	}
	
	@Override
	protected void updateRates(){
		try{
			log.info("REMIT: Fetching rates @ " + resourceUrl);
			for(String eachCurr:currDetailList){
				URLConnection uc = getUrlConnection(resourceUrl);
				extractRates(amount, eachCurr, uc);
			}
		}catch(IOException e){
			log.error("RemitGuru: fetching exchange rates caused error");
			e.printStackTrace();
		}
	}
	
	private String getCurrencyFromDetailString(String currencyDetail){
		return currencyDetail.substring(currencyDetail.indexOf('~')+1, currencyDetail.indexOf('~', currencyDetail.indexOf('~')+1));
	}
	
	
	private void extractRates(int amount, String currencyDetails, URLConnection uc) throws IOException{
		String query = String.format("amountTransfer=%s&corridor=%s&sendMode=%s", 
				  URLEncoder.encode(Integer.toString(amount),"UTF-8"),
				  URLEncoder.encode(currencyDetails,"UTF-8"),
				  URLEncoder.encode("CIP-NORMAL","UTF-8"));

		try (OutputStream output = uc.getOutputStream()) {
		output.write(query.getBytes("UTF-8"));
		}
		
		InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
		Scanner s = new Scanner(inputStreamReader).useDelimiter("\\A");
		String resp = s.hasNext() ? s.next():"";
		String xRate = resp.substring(resp.indexOf('|')+1, resp.indexOf('|', resp.indexOf('|')+1));
		double rate = Double.parseDouble(xRate);
		Currency base = Currency.getInstance(getCurrencyFromDetailString(currencyDetails));
		rates.addAvailableCurrency(toCurr);
		rates.setConversion(base, toCurr, rate);
		log.info(base.getDisplayName() + " : 1 units @ " + rate);
		inputStreamReader.close();
		s.close();
	}
	
}
