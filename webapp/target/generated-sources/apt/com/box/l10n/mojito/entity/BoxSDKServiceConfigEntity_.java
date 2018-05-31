package com.box.l10n.mojito.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BoxSDKServiceConfigEntity.class)
public abstract class BoxSDKServiceConfigEntity_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<BoxSDKServiceConfigEntity, String> privateKey;
	public static volatile SingularAttribute<BoxSDKServiceConfigEntity, String> clientId;
	public static volatile SingularAttribute<BoxSDKServiceConfigEntity, String> privateKeyPassword;
	public static volatile SingularAttribute<BoxSDKServiceConfigEntity, Boolean> validated;
	public static volatile SingularAttribute<BoxSDKServiceConfigEntity, String> dropsFolderId;
	public static volatile SingularAttribute<BoxSDKServiceConfigEntity, String> clientSecret;
	public static volatile SingularAttribute<BoxSDKServiceConfigEntity, String> rootFolderId;
	public static volatile SingularAttribute<BoxSDKServiceConfigEntity, String> enterpriseId;
	public static volatile SingularAttribute<BoxSDKServiceConfigEntity, Boolean> bootstrap;
	public static volatile SingularAttribute<BoxSDKServiceConfigEntity, String> appUserId;
	public static volatile SingularAttribute<BoxSDKServiceConfigEntity, String> rootFolderUrl;
	public static volatile SingularAttribute<BoxSDKServiceConfigEntity, String> publicKeyId;

}

