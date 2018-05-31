package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TranslationKitTextUnit.class)
public abstract class TranslationKitTextUnit_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<TranslationKitTextUnit, Boolean> sourceEqualsTarget;
	public static volatile SingularAttribute<TranslationKitTextUnit, User> createdByUser;
	public static volatile SingularAttribute<TranslationKitTextUnit, String> detectedLanguageException;
	public static volatile SingularAttribute<TranslationKitTextUnit, TMTextUnitVariant> exportedTmTextUnitVariant;
	public static volatile SingularAttribute<TranslationKitTextUnit, String> detectedLanguageExpected;
	public static volatile SingularAttribute<TranslationKitTextUnit, String> detectedLanguage;
	public static volatile SingularAttribute<TranslationKitTextUnit, TMTextUnit> tmTextUnit;
	public static volatile SingularAttribute<TranslationKitTextUnit, TMTextUnitVariant> importedTmTextUnitVariant;
	public static volatile SingularAttribute<TranslationKitTextUnit, Double> detectedLanguageProbability;
	public static volatile SingularAttribute<TranslationKitTextUnit, TranslationKit> translationKit;

}

