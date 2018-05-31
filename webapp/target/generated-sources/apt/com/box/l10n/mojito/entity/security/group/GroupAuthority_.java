package com.box.l10n.mojito.entity.security.group;

import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GroupAuthority.class)
public abstract class GroupAuthority_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<GroupAuthority, User> createdByUser;
	public static volatile SingularAttribute<GroupAuthority, String> authority;
	public static volatile SingularAttribute<GroupAuthority, Group> group;

}

