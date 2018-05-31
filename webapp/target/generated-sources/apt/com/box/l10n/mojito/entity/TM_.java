package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TM.class)
public abstract class TM_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<TM, User> createdByUser;
	public static volatile SetAttribute<TM, Repository> repositories;

}

