package com.box.l10n.mojito.entity.security.group;

import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Group.class)
public abstract class Group_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<Group, String> groupName;
	public static volatile SingularAttribute<Group, User> createdByUser;

}

