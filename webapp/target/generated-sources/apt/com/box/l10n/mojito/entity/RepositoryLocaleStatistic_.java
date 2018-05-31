package com.box.l10n.mojito.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RepositoryLocaleStatistic.class)
public abstract class RepositoryLocaleStatistic_ extends com.box.l10n.mojito.entity.BaseEntity_ {

	public static volatile SingularAttribute<RepositoryLocaleStatistic, Long> translationNeededWordCount;
	public static volatile SingularAttribute<RepositoryLocaleStatistic, Long> includeInFileCount;
	public static volatile SingularAttribute<RepositoryLocaleStatistic, Long> includeInFileWordCount;
	public static volatile SingularAttribute<RepositoryLocaleStatistic, Long> diffToSourcePluralCount;
	public static volatile SingularAttribute<RepositoryLocaleStatistic, Locale> locale;
	public static volatile SingularAttribute<RepositoryLocaleStatistic, Long> translatedCount;
	public static volatile SingularAttribute<RepositoryLocaleStatistic, Long> reviewNeededCount;
	public static volatile SingularAttribute<RepositoryLocaleStatistic, Long> reviewNeededWordCount;
	public static volatile SingularAttribute<RepositoryLocaleStatistic, Long> translationNeededCount;
	public static volatile SingularAttribute<RepositoryLocaleStatistic, Long> translatedWordCount;
	public static volatile SingularAttribute<RepositoryLocaleStatistic, RepositoryStatistic> repositoryStatistic;
	public static volatile SingularAttribute<RepositoryLocaleStatistic, Long> forTranslationCount;
	public static volatile SingularAttribute<RepositoryLocaleStatistic, Long> forTranslationWordCount;

}

