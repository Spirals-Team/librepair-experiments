package io.xrates.backend.rateprovider;

import io.xrates.backend.Rates;
import io.xrates.backend.constants.RateProviderDetails;
import io.xrates.backend.datamodel.util.XratesDBUtil;
import io.xrates.backend.exceptions.RateProviderException;

import java.util.Currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.github.lalyos.jfiglet.FigletFont;

public abstract class AbstractRateProvider implements IRateProvider {
	protected Rates rates = null;
	private RateProviderDetails rateProviderDetails = null;
	private long lastUpdated = -1;
	
	@Value("${xrates.staleness.milliseconds}")
	private long stalenessTimeInMilliSeconds;
	
	private Logger log = LoggerFactory.getLogger(AbstractRateProvider.class.getName());
	
	@Autowired
	private XratesDBUtil xratesDBUtil;
		
	public void update() throws RateProviderException {
		if (rateProviderDetails == null) {
			throw new RateProviderException("RateProvider not set.");
		}
		
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastUpdated > stalenessTimeInMilliSeconds) {
			log.info("Instantiating following service : \n" + 
					FigletFont.convertOneLine(rateProviderDetails.getProviderName()));
			rates = new Rates();
			rates.setRateProvider(rateProviderDetails);
			updateRates();
			xratesDBUtil.persistRates(rates);
			lastUpdated = currentTime;
		} else {
			log.info("Not updating the rates as it is last updates < "
					+ stalenessTimeInMilliSeconds/1000 + " seconds ago."  );
		}
	}
	
	public double convert(Currency from, Currency to) throws RateProviderException {
		if (rates == null) {
			log.info("Found rates null so updating it.");
			update();
		}
		return round(rates.getConversion(from, to), 2);
	}

	public RateProviderDetails getRateProviderDetails() {
		return rateProviderDetails;
	}
	
	protected void setRateProviderDetails(RateProviderDetails rateProviderDetails) {
		this.rateProviderDetails = rateProviderDetails;
	}
	
	public Rates getRates() {
		return rates;
	}
	
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	protected abstract void updateRates();

}
