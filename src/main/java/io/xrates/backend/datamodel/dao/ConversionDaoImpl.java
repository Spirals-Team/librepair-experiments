package io.xrates.backend.datamodel.dao;

import io.xrates.backend.constants.Constants;
import io.xrates.backend.datamodel.beans.Conversion;
import io.xrates.backend.datamodel.beans.Service;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ConversionDaoImpl extends AbstractDao<Conversion> implements ConversionDao {
	
	private Logger log = LoggerFactory.getLogger(SubscriptionDaoImpl.class.getName());
	
	@Autowired
	ServiceDao serviceDao;
	
	private List<Conversion> findAllType(String statusType) {
		Query q = entityManager.createQuery("select c from Conversion c "
				+ "where c.status = :status");
		q.setParameter("status", statusType);
		List<Conversion> result = q.getResultList();
		if (result != null) {
			log.info("Found " + result.size() + " records of " + statusType + " type");
		}
		return result;
	}
	
	@Override
	public List<Conversion> findAllUnProcessed() {
		return findAllType(Constants.UNPROCESSED);
	}
	
	@Override
	public List<Conversion> findAllNotifyAlert() {
		return findAllType(Constants.NOTIFY_ALERT);
	}
	
	@Override
	public Conversion findLastClosingRate(Service service) {
		Query q = entityManager.createQuery("select con from Conversion con "
				+ "where con.conversionTimestamp in "
				+ "( select max(c.conversionTimestamp) from "
				+ "Conversion c where c.conversionTimestamp < :todayTimestamp "
				+ "and c.service = :service ) and con.service = :service ");
		q.setParameter("service", service);
		//TODO:Temporarily hard-coded time-zone. Need to extend data-model 
		//to accommodate it.
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		q.setParameter("todayTimestamp", cal.getTime());
		try {
			Conversion result = (Conversion) q.getSingleResult();
			return result;
		} catch (NoResultException e) {
			log.error("Seems there is no previous entry for this service-id => " 
					+ service.getServiceId() );
			return null;
		}
		
	}
	
	@Override
	public Conversion findLastClosingRate(Conversion currentRate) {
		return findLastClosingRate(currentRate.getService());
	}

	@Override
	public Conversion findNotifyAlert(Service service) {
		Query q = entityManager.createQuery("select c from Conversion c where "
				+ "service = :service and status = :status");
		q.setParameter("service", service);
		q.setParameter("status", Constants.NOTIFY_ALERT);
		try {
			Conversion result = (Conversion) q.getSingleResult();
			return result;
		} catch (NoResultException e) {
			log.error("Seems fishy !! Check the record for service-id => " 
					+ service.getServiceId() );
			return null;
		}
	}
	
	@Override
	public List<Conversion> todayNotified(Service service) {
		Query q = entityManager.createQuery("select c from Conversion c "
				+ "where service = :service and status = :status "
				+ "and DATE(conversion_timestamp) = CURDATE() ");
		q.setParameter("service", service);
		q.setParameter("status", Constants.NOTIFIED);
		List<Conversion> result = q.getResultList();
		return result;
	}
}
