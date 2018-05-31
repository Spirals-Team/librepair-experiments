package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Asset.class)
public abstract class Asset_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<Asset, String> path;
	public static volatile SingularAttribute<Asset, String> contentMd5;
	public static volatile SingularAttribute<Asset, Boolean> virtual;
	public static volatile SingularAttribute<Asset, Boolean> deleted;
	public static volatile SingularAttribute<Asset, User> createdByUser;
	public static volatile SingularAttribute<Asset, Repository> repository;
	public static volatile SingularAttribute<Asset, AssetExtraction> lastSuccessfulAssetExtraction;
	public static volatile SingularAttribute<Asset, String> content;

}

