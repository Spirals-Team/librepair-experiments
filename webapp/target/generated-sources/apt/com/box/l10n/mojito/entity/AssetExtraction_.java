package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AssetExtraction.class)
public abstract class AssetExtraction_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<AssetExtraction, String> contentMd5;
	public static volatile SingularAttribute<AssetExtraction, User> createdByUser;
	public static volatile SingularAttribute<AssetExtraction, Asset> asset;
	public static volatile SetAttribute<AssetExtraction, AssetTextUnit> assetTextUnits;
	public static volatile SingularAttribute<AssetExtraction, PollableTask> pollableTask;

}

