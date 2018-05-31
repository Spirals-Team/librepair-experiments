package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TMTextUnit.class)
public abstract class TMTextUnit_ extends com.box.l10n.mojito.entity.SettableAuditableEntity_ {

	public static volatile SingularAttribute<TMTextUnit, String> contentMd5;
	public static volatile SingularAttribute<TMTextUnit, PluralForm> pluralForm;
	public static volatile SingularAttribute<TMTextUnit, Integer> wordCount;
	public static volatile SingularAttribute<TMTextUnit, User> createdByUser;
	public static volatile SingularAttribute<TMTextUnit, String> pluralFormOther;
	public static volatile SingularAttribute<TMTextUnit, String> name;
	public static volatile SingularAttribute<TMTextUnit, TM> tm;
	public static volatile SingularAttribute<TMTextUnit, String> comment;
	public static volatile SingularAttribute<TMTextUnit, Asset> asset;
	public static volatile SingularAttribute<TMTextUnit, String> content;
	public static volatile SingularAttribute<TMTextUnit, String> md5;

}

