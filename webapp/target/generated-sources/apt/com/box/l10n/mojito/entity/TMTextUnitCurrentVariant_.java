package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TMTextUnitCurrentVariant.class)
public abstract class TMTextUnitCurrentVariant_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<TMTextUnitCurrentVariant, TMTextUnitVariant> tmTextUnitVariant;
	public static volatile SingularAttribute<TMTextUnitCurrentVariant, User> createdByUser;
	public static volatile SingularAttribute<TMTextUnitCurrentVariant, TM> tm;
	public static volatile SingularAttribute<TMTextUnitCurrentVariant, TMTextUnit> tmTextUnit;
	public static volatile SingularAttribute<TMTextUnitCurrentVariant, Locale> locale;

}

