package io.xrates.backend;

import io.xrates.backend.datamodel.dao.UserDao;
import io.xrates.backend.exceptions.RateProviderException;
import io.xrates.backend.ratecheck.BusinessLogic;
import io.xrates.backend.rateprovider.IRateProvider;

import java.io.IOException;
import java.util.Currency;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MainScheduler {
	
	Logger log = LoggerFactory.getLogger(MainScheduler.class.getName());
	
	@Autowired
	private List<IRateProvider> rateProviders;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private BusinessLogic bl;
	
	@Scheduled(cron="0 */15 9-18 * * MON-FRI")
	@Transactional
	public void getRate() throws IOException {
		log.debug("Running scheduler");
		
		for (int i = 0;i<this.rateProviders.size();i++){
			try {
				rateProviders.get(i).update();
				log.info(rateProviders.get(i).getRateProviderDetails().getProviderName() + 
						" : For 1 "+Currency.getInstance("SGD").getDisplayName() +
						" you get " + String.valueOf( 
							this.rateProviders.get(i).convert(Currency.getInstance("SGD"), 
							Currency.getInstance("INR"))) 
							+ " " + Currency.getInstance("INR").getDisplayName());
			} catch (RateProviderException e) {
				log.error("Error in " + rateProviders.getClass() + " :" + e.getMessage());
			}
		}
		
		bl.process();
		bl.notifyAlerts();
	}
}
