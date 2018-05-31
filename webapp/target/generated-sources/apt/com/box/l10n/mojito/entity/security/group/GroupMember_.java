package com.box.l10n.mojito.entity.security.group;

import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GroupMember.class)
public abstract class GroupMember_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<GroupMember, User> createdByUser;
	public static volatile SingularAttribute<GroupMember, User> user;
	public static volatile SingularAttribute<GroupMember, Group> group;

}

