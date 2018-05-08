package edu.unq.desapp.grupo_a.backend.dao;

import org.springframework.stereotype.Repository;

import edu.unq.desapp.grupo_a.backend.model.User;

@Repository
public class UserDao extends HibernateGenericDao<User> implements GenericDao<User> {

	private static final long serialVersionUID = -4425722631916607857L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Class<User> getDomainClass() {
		return User.class;
	}
	
}
