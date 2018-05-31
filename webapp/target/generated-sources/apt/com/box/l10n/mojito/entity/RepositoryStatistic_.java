package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RepositoryStatistic.class)
public abstract class RepositoryStatistic_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<RepositoryStatistic, Long> uncommentedTextUnitCount;
	public static volatile SingularAttribute<RepositoryStatistic, Long> unusedTextUnitCount;
	public static volatile SingularAttribute<RepositoryStatistic, Long> pluralTextUnitWordCount;
	public static volatile SingularAttribute<RepositoryStatistic, User> createdByUser;
	public static volatile SingularAttribute<RepositoryStatistic, Long> usedTextUnitWordCount;
	public static volatile SetAttribute<RepositoryStatistic, RepositoryLocaleStatistic> repositoryLocaleStatistics;
	public static volatile SingularAttribute<RepositoryStatistic, Long> usedTextUnitCount;
	public static volatile SingularAttribute<RepositoryStatistic, Long> pluralTextUnitCount;
	public static volatile SingularAttribute<RepositoryStatistic, Long> unusedTextUnitWordCount;

}

