package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.TMTextUnitVariant.Status;
import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TMTextUnitVariant.class)
public abstract class TMTextUnitVariant_ extends com.box.l10n.mojito.entity.SettableAuditableEntity_ {

	public static volatile SingularAttribute<TMTextUnitVariant, String> contentMD5;
	public static volatile SingularAttribute<TMTextUnitVariant, Boolean> includedInLocalizedFile;
	public static volatile SingularAttribute<TMTextUnitVariant, User> createdByUser;
	public static volatile SingularAttribute<TMTextUnitVariant, TMTextUnit> tmTextUnit;
	public static volatile SingularAttribute<TMTextUnitVariant, String> comment;
	public static volatile SingularAttribute<TMTextUnitVariant, Locale> locale;
	public static volatile SetAttribute<TMTextUnitVariant, TMTextUnitVariantComment> tmTextUnitVariantComments;
	public static volatile SingularAttribute<TMTextUnitVariant, String> content;
	public static volatile SingularAttribute<TMTextUnitVariant, Status> status;

}

