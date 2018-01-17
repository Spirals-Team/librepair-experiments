package io.xrates.backend.ratecheck;

import io.xrates.backend.communication.XratesEmail;
import io.xrates.backend.constants.Constants;
import io.xrates.backend.datamodel.beans.Conversion;
import io.xrates.backend.datamodel.beans.Service;
import io.xrates.backend.datamodel.beans.Subscription;
import io.xrates.backend.datamodel.beans.User;
import io.xrates.backend.datamodel.dao.ConversionDao;
import io.xrates.backend.datamodel.dao.UserDao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessLogic {
	
	@Autowired
	private ConversionDao conversionDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private XratesEmail xratesEmail;
	
	private final double  TRIGGER = 1.0;
	private Logger log = LoggerFactory.getLogger(BusinessLogic.class.getName());
	
	public void process() {
		List<Conversion> unprocessedRates = conversionDao.findAllUnProcessed();
		
		for (Conversion currentRate: unprocessedRates) {
			Conversion closingRate = conversionDao.findLastClosingRate(currentRate);
			double change = checkChange(closingRate, currentRate);
			currentRate.setPercentChange(change);
			if (Math.abs(change) >= TRIGGER && !isNotified(currentRate.getService())) {
				currentRate.setStatus(Constants.NOTIFY_ALERT);
			} else {
				currentRate.setStatus(Constants.PROCESSED);
			}
			conversionDao.update(currentRate);
		}
	}
	
	private boolean isNotified(Service service) {
		List<Conversion> result = conversionDao.
				todayNotified(service);
		if (result == null) {
			return false;
		}
		return result.size() > 0;
	}

	public void notifyAlerts() {
		List<Conversion> notifyAlerts = conversionDao.findAllNotifyAlert();
		Set<Service> notifyServices = new HashSet<Service>();
		Set<Subscription> notifySubscriptions = new HashSet<Subscription>();
		Set<User> usersNotifyAlert = new HashSet<User>();
		for (Conversion alert:notifyAlerts) {
			Service service = alert.getService();
			notifyServices.add(service);
			for (Subscription subscription: service.getSubscriptions()) {
				notifySubscriptions.add(subscription);
				usersNotifyAlert.add(subscription.getUser());
			}
		}
		
		log.info("Sending out notifications to " + usersNotifyAlert.size()
				+ " subscribers");
		for (User userNotifyAlert:usersNotifyAlert) {
			Set<Subscription> subscriptionIntersection = new 
					HashSet<Subscription>(userNotifyAlert.getSubscriptions());
			subscriptionIntersection.retainAll(notifySubscriptions);
			notifyByEmail(userNotifyAlert, subscriptionIntersection);
		}
		
		log.debug("Marking " + notifyAlerts.size() + " records "
				+ "as NOTIFIED");
		for (Conversion notifiedAlert:notifyAlerts) {
			//mark the alert as PROCESSED
			notifiedAlert.setStatus(Constants.NOTIFIED);
			conversionDao.update(notifiedAlert);
		}
	}

	private void notifyByEmail(User user, Set<Subscription> subscriptions) {
		if (subscriptions.size() == 0) {
			return;
		}
		
		String subject = "";
		String body = "";
		
		if (subscriptions.size() == 1) {
			Subscription subscription = subscriptions.iterator().next();
			Service service = subscription.getService();
			Conversion rate = conversionDao.findNotifyAlert(service);
			subject = "[Xrates Alert] " + emailText(service, rate);
			body =  "1 " + rate.getService().getFromCurrency() + " = " +
					round(rate.getConversionRate(), 2) + " " + 
					rate.getService().getToCurrency() + "\n" +
					round(rate.getPercentChange(), 2) + "% change since last "
					+ "closing rate";
		} else {
			subject = "[Xrates Alert] There are rates alert in "
					+ subscriptions.size() + " of the services "
					+ "you subscribed";
			int i = 1;
			body = "Following is the list of alerts to which "
					+ "you subscribed :";
			for (Subscription subscription:subscriptions) {
				Service service = subscription.getService();
				Conversion rate = conversionDao.findNotifyAlert(service);
				body = "(" + i++ + ".) " + emailText(service, rate) + "\n";
			}
		}
		
		xratesEmail.addTo(user);
		xratesEmail.addTextBody(body);
		xratesEmail.addSubject(subject);
		xratesEmail.sendMail();
	}
	
	private String emailText(Service service, Conversion rate) {
		return "1 " + service.getFromCurrency() 
				+ " = " + round(rate.getConversionRate(), 2) + " "
				+ service.getToCurrency() + " as noticed at "
				+ rate.getConversionTimestamp() + " for "
				+ service.getProvider().getProviderName();
	}

	private double checkChange(Conversion closingRate, Conversion currentRate) {
		if (closingRate == null || closingRate.getConversionRate() == 0) {
			return 0;
		}
		
		double closing = closingRate.getConversionRate();
		double current = currentRate.getConversionRate();
		return round(((current - closing) * 100 ) / closing , 2);
	}
	
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
}
