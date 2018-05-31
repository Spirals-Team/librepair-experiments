package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.TMTextUnitVariantComment.Severity;
import com.box.l10n.mojito.entity.TMTextUnitVariantComment.Type;
import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TMTextUnitVariantComment.class)
public abstract class TMTextUnitVariantComment_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<TMTextUnitVariantComment, Severity> severity;
	public static volatile SingularAttribute<TMTextUnitVariantComment, TMTextUnitVariant> tmTextUnitVariant;
	public static volatile SingularAttribute<TMTextUnitVariantComment, User> createdByUser;
	public static volatile SingularAttribute<TMTextUnitVariantComment, Type> type;
	public static volatile SingularAttribute<TMTextUnitVariantComment, String> content;

}

