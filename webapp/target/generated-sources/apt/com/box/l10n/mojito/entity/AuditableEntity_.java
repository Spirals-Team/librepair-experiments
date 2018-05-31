package com.box.l10n.mojito.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.DateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AuditableEntity.class)
public abstract class AuditableEntity_ extends com.box.l10n.mojito.entity.BaseEntity_ {

	public static volatile SingularAttribute<AuditableEntity, DateTime> createdDate;
	public static volatile SingularAttribute<AuditableEntity, DateTime> lastModifiedDate;

}

