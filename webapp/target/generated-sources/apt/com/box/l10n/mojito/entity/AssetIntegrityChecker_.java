package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.service.assetintegritychecker.integritychecker.IntegrityCheckerType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AssetIntegrityChecker.class)
public abstract class AssetIntegrityChecker_ extends com.box.l10n.mojito.entity.BaseEntity_ {

	public static volatile SingularAttribute<AssetIntegrityChecker, IntegrityCheckerType> integrityCheckerType;
	public static volatile SingularAttribute<AssetIntegrityChecker, Repository> repository;
	public static volatile SingularAttribute<AssetIntegrityChecker, String> assetExtension;

}

