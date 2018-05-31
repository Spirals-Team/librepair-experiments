package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.TranslationKit.Type;
import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TranslationKit.class)
public abstract class TranslationKit_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<TranslationKit, Drop> drop;
	public static volatile SingularAttribute<TranslationKit, Integer> numTranslatedTranslationKitUnits;
	public static volatile SetAttribute<TranslationKit, String> notFoundTextUnitIds;
	public static volatile SingularAttribute<TranslationKit, Integer> numBadLanguageDetections;
	public static volatile SingularAttribute<TranslationKit, Long> wordCount;
	public static volatile SingularAttribute<TranslationKit, User> createdByUser;
	public static volatile SingularAttribute<TranslationKit, Integer> numTranslationKitUnits;
	public static volatile SingularAttribute<TranslationKit, Integer> numSourceEqualsTarget;
	public static volatile SingularAttribute<TranslationKit, Locale> locale;
	public static volatile SingularAttribute<TranslationKit, Type> type;

}

