package io.xrates.backend;

import io.xrates.backend.rateprovider.AbstractRateProvider;
import io.xrates.backend.rateprovider.impl.DBSRateProviderImpl;
import io.xrates.backend.rateprovider.impl.OCBCRateProviderImpl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatesRESTController {
	
	Logger log = LoggerFactory.getLogger(RatesRESTController.class.getName());
	
	private static final String CONTEXT_PATH  = "/xrates_rest/v1/";
	
	private static final String GET_RATES = "/get_rates";
	
	@Autowired
	private DBSRateProviderImpl dbs;
	
	@Autowired
	private OCBCRateProviderImpl ocbc;
	
	@RequestMapping(value = CONTEXT_PATH + "dbs" + GET_RATES, 
			method = RequestMethod.GET)
	public Rates getDBSRates(HttpServletRequest request) {
		log.info("Received GET request for DBS from IP=" + request.getRemoteAddr());
		return getRates(dbs);
	}
	
	@RequestMapping(value = CONTEXT_PATH + "ocbc" + GET_RATES, 
			method = RequestMethod.GET)
	public Rates getOCBCRates(HttpServletRequest request) {
		log.info("Received GET request for OCBC from IP=" + request.getRemoteAddr());
		return getRates(ocbc);
	}

	private Rates getRates(AbstractRateProvider rateProvider) {
		try {
			long startTime = System.currentTimeMillis();
			Rates rates = rateProvider.getRates();
			if (rates == null) {
				rateProvider.update();
				rates = rateProvider.getRates();
			}
			long endTime = System.currentTimeMillis();
			log.info("REST request at " + rates.getRateCaptureTime());
			log.info("Took " + (endTime - startTime) + "ms for getting the rates");
		} catch (Exception e) {
			String providerName = (rateProvider == null) ?
					null : rateProvider.getRateProviderDetails().getProviderName();
			log.error("Error in REST request of Rate Provider " 
					+ providerName);
			return null;
		}
		return rateProvider.getRates();
	}
	
	
}
