package io.xrates.backend.datamodel.dao;

import io.xrates.backend.datamodel.beans.Service;
import io.xrates.backend.datamodel.beans.Subscription;
import io.xrates.backend.datamodel.beans.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscriptionDaoImpl extends AbstractDao<Subscription> implements SubscriptionDao {

	@Autowired
	private UserDao userDao;
	
	private Logger log = LoggerFactory.getLogger(SubscriptionDaoImpl.class.getName());
	
	@Override
	public void subscribeUser(String email, Service service) {
		List<Service> services = new ArrayList<Service>();
		services.add(service);
		subscribeUser(email, services);
	}

	@Override
	public void subscribeUser(User user, Service service) {
		List<Service> services = new ArrayList<Service>();
		services.add(service);
		subscribeUser(user, services);
	}
	
	@Override
	public void subscribeUser(String email, List<Service> services) {
		subscribeUser(userDao.findUserByEmail(email) , services);
	}

	@Override
	public void subscribeUser(User user, List<Service> services) {
		for (Service service: services) {
			Subscription subscription = new Subscription();
			subscription.setService(service);
			subscription.setUser(user);
			subscription.setStartDate(new Date());
			subscription.setActive(true);
			create(subscription);
		}
	}

	@Override
	public void unsubscribeUser(String email, Service service) {
		List<Service> services = new ArrayList<Service>();
		services.add(service);
		unsubscribeUser(email, services);
	}

	@Override
	public void unsubscribeUser(User user, Service service) {
		List<Service> services = new ArrayList<Service>();
		services.add(service);
		unsubscribeUser(user, services);
	}

	@Override
	public void unsubscribeUser(String email, List<Service> services) {
		unsubscribeUser(userDao.findUserByEmail(email), services);
	}

	@Override
	public void unsubscribeUser(User user, List<Service> services) {
		Query q = entityManager.createQuery("update Subscription set "
				+ "isActive = :isActive and endDate = :endDate "
				+ "where user = :user and service in :services");
		q.setParameter("isActive", false);
		q.setParameter("endDate", new Date());
		q.setParameter("user", user);
		q.setParameter("services", services);
		int numOfUpdates = q.executeUpdate();
		log.info(numOfUpdates + " services unsubscribed for " + user.getName());
	}
}
