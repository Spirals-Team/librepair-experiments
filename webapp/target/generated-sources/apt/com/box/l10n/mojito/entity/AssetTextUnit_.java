package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AssetTextUnit.class)
public abstract class AssetTextUnit_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<AssetTextUnit, Boolean> doNotTranslate;
	public static volatile SingularAttribute<AssetTextUnit, String> contentMd5;
	public static volatile SingularAttribute<AssetTextUnit, PluralForm> pluralForm;
	public static volatile SingularAttribute<AssetTextUnit, AssetExtraction> assetExtraction;
	public static volatile SingularAttribute<AssetTextUnit, User> createdByUser;
	public static volatile SingularAttribute<AssetTextUnit, String> pluralFormOther;
	public static volatile SingularAttribute<AssetTextUnit, String> name;
	public static volatile SingularAttribute<AssetTextUnit, String> comment;
	public static volatile SetAttribute<AssetTextUnit, String> usages;
	public static volatile SingularAttribute<AssetTextUnit, String> content;
	public static volatile SingularAttribute<AssetTextUnit, String> md5;

}

