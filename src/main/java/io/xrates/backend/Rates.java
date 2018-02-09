package io.xrates.backend;

import io.xrates.backend.constants.RateProviderDetails;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rates {
	private RateProviderDetails rateProvider = null;
	private List<Currency> availableCurrencies = new ArrayList<Currency>();
	private Map<String, Map<String, Double>> rates = new HashMap<String, Map<String,Double>>();
	private Date rateCaptureTime;
	
	public Rates() {
		setRateCaptureTime(new Date());
	}
	
	public void addAvailableCurrency(Currency cur) {
		availableCurrencies.add(cur);
	}

	public List<Currency> getAvailableCurrencies() {
		return availableCurrencies;
	}

	public void setAvailableCurrencies(List<Currency> availableCurrencies) {
		this.availableCurrencies = availableCurrencies;
	}
	
	public boolean isCurrencySupported(Currency cur) {
		return availableCurrencies.contains(cur);
	}

	public Map<String, Map<String, Double>> getRates() {
		return rates;
	}
	
	public void setConversion(Currency from, Currency to, Double rate) {
		if (rates.containsKey(from.getCurrencyCode())) {
			Map<String, Double> map = rates.get(from.getCurrencyCode());
			map.put(to.getCurrencyCode(), rate);
		} else {
			Map<String, Double> map = new HashMap<String, Double>();
			map.put(to.getCurrencyCode(), rate);
			rates.put(from.getCurrencyCode(), map);
		}
	}
	
	public double getConversion(Currency from, Currency to) {
		if (rates.containsKey(from.getCurrencyCode())) {
			Map<String, Double> map = rates.get(from.getCurrencyCode());
			if (map.containsKey(to.getCurrencyCode())) {
				return map.get(to.getCurrencyCode());
			}
		}
		return -1;
	}

	public RateProviderDetails getRateProvider() {
		return rateProvider;
	}

	public void setRateProvider(RateProviderDetails rateProvider) {
		this.rateProvider = rateProvider;
	}

	public Date getRateCaptureTime() {
		return rateCaptureTime;
	}

	private void setRateCaptureTime(Date rateCaptureTime) {
		this.rateCaptureTime = rateCaptureTime;
	}
}
