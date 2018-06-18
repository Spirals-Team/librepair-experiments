package io.xrates.backend.rateprovider.util;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CurrencyAdapter {
	private static Map<String, Currency> map = null;
	
	public static Currency getInstance(String currencyName) {
		if (map == null) {
			map = new HashMap<String, Currency>();
			Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
			for (Currency cur : availableCurrencies) {
				map.put(cur.getDisplayName(), cur);
			}
			
			//Adding exceptions
			addExceptions();
		}
		
		if (map.containsKey(currencyName)) {
			return map.get(currencyName);
		} else {
			return null;
		}
	}

	private static void addExceptions() {
		map.put("Sterling Pound", Currency.getInstance("GBP"));
		map.put("Danish Kroner", Currency.getInstance("DKK"));
		map.put("Norwegian Kroner", Currency.getInstance("NOK"));
		map.put("Swedish Kroner", Currency.getInstance("SEK"));
		map.put("Chinese Renminbi(Offshore)", Currency.getInstance("CNY"));
		map.put("Chinese Renminbi", null); //No ISO code support for CNH and RMB
		map.put("Korean Won", Currency.getInstance("KRW"));
		map.put("Sri Lanka Rupee", Currency.getInstance("LKR"));
		map.put("Saudi Rial", Currency.getInstance("SAR"));
	}
}
