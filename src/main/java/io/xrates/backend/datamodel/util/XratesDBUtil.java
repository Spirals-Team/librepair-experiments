package io.xrates.backend.datamodel.util;

import io.xrates.backend.Rates;
import io.xrates.backend.constants.RateProviderDetails;
import io.xrates.backend.datamodel.beans.Conversion;
import io.xrates.backend.datamodel.beans.Provider;
import io.xrates.backend.datamodel.beans.Service;
import io.xrates.backend.datamodel.dao.ConversionDao;
import io.xrates.backend.datamodel.dao.ProviderDao;
import io.xrates.backend.datamodel.dao.ServiceDao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XratesDBUtil {
	
	@Autowired
	private ServiceDao serviceDao;
	
	@Autowired
	private ConversionDao conversionDao;
	
	@Autowired
	private ProviderDao providerDao;
	
	@Transactional
	public void persistRates(Rates rates) {
		Provider provider = getOrCreateProvider(rates.getRateProvider());
		Date rateCaptureTime = rates.getRateCaptureTime();
		for (String fromCurrency : rates.getRates().keySet()) {
			Map<String, Double> conversionRateMap = rates.getRates().get(fromCurrency);
			for (String toCurrency : conversionRateMap.keySet()) {
				Conversion conversion = new Conversion();
				Service service = getService(fromCurrency, toCurrency, provider);
				Double conversionRate = conversionRateMap.get(toCurrency);
				
				conversion.setService(service);
				conversion.setConversionTimestamp(rateCaptureTime);
				conversion.setConversionRate(conversionRate);
				
				conversionDao.create(conversion);
			}
		}
	}
	
	public List<Provider> listOfServiceProviders(String fromCurrency, String toCurrency) {
		return serviceDao.findProviders(fromCurrency, toCurrency);
	}
	
	private Service getService(String fromCurrency, String toCurrency, Provider provider) {
		return serviceDao.findService(fromCurrency, toCurrency, provider);
	}
	
	private Provider getOrCreateProvider(RateProviderDetails rateProvider) {
		return providerDao.findProvider(rateProvider);
	}
}
