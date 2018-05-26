package io.xrates.frontend.templates;

import java.util.Set;
import java.util.Arrays;
import java.util.Currency;

public class SubscriptionForm {
	
	private static String[] currencyList = null;
	private String selectedCurrency = "SGD";
	
	static{
		int index = 0;
		Set<Currency> currencySet = Currency.getAvailableCurrencies();
		currencyList = new String[currencySet.size()];
		for (Currency currency : currencySet) {
			currencyList[index++] = currency.getDisplayName();
		}
		Arrays.sort(currencyList);
	}
	private String emailAddress = "Your Email address: someone@somedomain.com";
	
	
	public String[] getCurrencyList() {
		return currencyList;
	}
	public void setCurrencyList(String[] currencyList) {
		SubscriptionForm.currencyList = currencyList;
	}
	public String getSelectedCurrency() {
		return selectedCurrency;
	}
	public void setSelectedCurrency(String selectedCurrency) {
		this.selectedCurrency = selectedCurrency;
	}
	
	public String getCurrency(){
		return selectedCurrency;
	}
	public void setCurrency(String curr){
		this.selectedCurrency = curr;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public static String[] getRelevantCurrencyList(String fromCurrency){
		// TODO: Implement actual currency list here
		String[] currencyList = {"USD", "AXI", "SDJ" ,"DSK", "HAF", "DSO", "DAD", "DDD", "SDD"};
		return currencyList;
	}
	
	public static String[] getRelevantServices(String fromCurrency, String toCurrency){
		// TODO: Implement actual service list
		String[] serviceList = {"DBS", "OCBC", "HDFC Bank" , "RemitGuru"};
		return serviceList;
	}
}
