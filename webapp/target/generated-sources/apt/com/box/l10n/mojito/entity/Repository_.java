package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.security.user.User;
import com.box.l10n.mojito.service.drop.exporter.DropExporterType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Repository.class)
public abstract class Repository_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SetAttribute<Repository, AssetIntegrityChecker> assetIntegrityCheckers;
	public static volatile SingularAttribute<Repository, Boolean> deleted;
	public static volatile SingularAttribute<Repository, User> createdByUser;
	public static volatile SetAttribute<Repository, RepositoryLocale> repositoryLocales;
	public static volatile SingularAttribute<Repository, String> name;
	public static volatile SingularAttribute<Repository, RepositoryStatistic> repositoryStatistic;
	public static volatile SingularAttribute<Repository, String> description;
	public static volatile SingularAttribute<Repository, TM> tm;
	public static volatile SingularAttribute<Repository, DropExporterType> dropExporterType;

}

