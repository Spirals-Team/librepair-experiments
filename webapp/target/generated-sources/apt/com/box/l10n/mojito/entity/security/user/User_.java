package com.box.l10n.mojito.entity.security.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<User, String> commonName;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, User> createdByUser;
	public static volatile SingularAttribute<User, String> surname;
	public static volatile SingularAttribute<User, String> givenName;
	public static volatile SingularAttribute<User, Boolean> enabled;
	public static volatile SetAttribute<User, Authority> authorities;
	public static volatile SingularAttribute<User, String> username;

}

