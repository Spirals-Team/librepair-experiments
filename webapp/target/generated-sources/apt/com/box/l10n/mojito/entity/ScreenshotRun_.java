package com.box.l10n.mojito.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ScreenshotRun.class)
public abstract class ScreenshotRun_ extends com.box.l10n.mojito.entity.SettableAuditableEntity_ {

	public static volatile SingularAttribute<ScreenshotRun, String> name;
	public static volatile SingularAttribute<ScreenshotRun, Repository> repository;
	public static volatile SingularAttribute<ScreenshotRun, Boolean> lastSuccessfulRun;
	public static volatile SetAttribute<ScreenshotRun, Screenshot> screenshots;

}

