package io.xrates.backend.rateprovider;

import java.util.Currency;

import io.xrates.backend.constants.RateProviderDetails;
import io.xrates.backend.exceptions.RateProviderException;

/*
 * This interface provides skeleton for rate
 * providers of different exchange rate providers
 */
public interface IRateProvider {
	
	/**
	 * Will fetch the rates from the respective
	 * source update it in-memory and persists in
	 * DB. If no rate provider is set then it should
	 * throw RateProviderException.
	 * 
	 * @throws RateProviderException
	 */
	public void update() throws RateProviderException;
	
	/**
	 * Returns the conversion rate of 1 unit of base
	 * currency to target currency for a particular
	 * rate provider. If no rate provider is set then
	 * it should throw RateProviderException.
	 * 
	 * @param baseCurrency
	 * @param targetCurrency
	 * @return returns the conversion rate
	 * @throws RateProviderException
	 */
	public double convert(Currency baseCurrency, 
			Currency targetCurrency) throws RateProviderException;
	
	/**
	 * Returns the RateProvider
	 * 
	 * @return
	 */
	public RateProviderDetails getRateProviderDetails();
}
