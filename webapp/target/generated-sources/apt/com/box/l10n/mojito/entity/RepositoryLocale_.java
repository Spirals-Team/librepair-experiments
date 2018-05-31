package com.box.l10n.mojito.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RepositoryLocale.class)
public abstract class RepositoryLocale_ extends com.box.l10n.mojito.entity.BaseEntity_ {

	public static volatile SingularAttribute<RepositoryLocale, RepositoryLocale> parentLocale;
	public static volatile SingularAttribute<RepositoryLocale, Boolean> toBeFullyTranslated;
	public static volatile SingularAttribute<RepositoryLocale, Repository> repository;
	public static volatile SingularAttribute<RepositoryLocale, Locale> locale;

}

