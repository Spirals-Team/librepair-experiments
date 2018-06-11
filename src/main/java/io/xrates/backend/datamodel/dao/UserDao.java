package io.xrates.backend.datamodel.dao;

import io.xrates.backend.datamodel.beans.Service;
import io.xrates.backend.datamodel.beans.User;

import java.util.Set;

public interface UserDao extends Dao<User> {
	
	public void createUser(String name, String email);
	public void createUser(String name, String email, int isdCode, long contactNumber);
	public User findUserByEmail(String email);
	public void verifyUserByEmail(String email);
	public void verifyUserByEmail(User user);
	public void verifyUserContact(String email);
	public void verifyUserContact(User user);
	public Set<User> findUserByService(Service service);
	public Set<User> findUserByService(Set<Service> services);
}
