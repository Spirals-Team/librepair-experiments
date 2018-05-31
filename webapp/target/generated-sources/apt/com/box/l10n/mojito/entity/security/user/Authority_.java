package com.box.l10n.mojito.entity.security.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Authority.class)
public abstract class Authority_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<Authority, User> createdByUser;
	public static volatile SingularAttribute<Authority, String> authority;
	public static volatile SingularAttribute<Authority, User> user;

}

