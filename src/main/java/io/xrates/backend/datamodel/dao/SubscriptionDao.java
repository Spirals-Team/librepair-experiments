package io.xrates.backend.datamodel.dao;

import io.xrates.backend.datamodel.beans.Service;
import io.xrates.backend.datamodel.beans.Subscription;
import io.xrates.backend.datamodel.beans.User;

import java.util.List;

public interface SubscriptionDao extends Dao<Subscription> {
	public void subscribeUser(String email, Service service);
	public void subscribeUser(User user, Service service);
	public void subscribeUser(String email, List<Service> services);
	public void subscribeUser(User user, List<Service> services);
	public void unsubscribeUser(String email, Service service);
	public void unsubscribeUser(User user, Service service);
	public void unsubscribeUser(String email, List<Service> services);
	public void unsubscribeUser(User user, List<Service> services);
}
