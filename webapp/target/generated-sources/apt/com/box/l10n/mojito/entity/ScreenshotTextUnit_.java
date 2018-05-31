package com.box.l10n.mojito.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ScreenshotTextUnit.class)
public abstract class ScreenshotTextUnit_ extends com.box.l10n.mojito.entity.BaseEntity_ {

	public static volatile SingularAttribute<ScreenshotTextUnit, TMTextUnitVariant> tmTextUnitVariant;
	public static volatile SingularAttribute<ScreenshotTextUnit, Integer> numberOfMatch;
	public static volatile SingularAttribute<ScreenshotTextUnit, String> name;
	public static volatile SingularAttribute<ScreenshotTextUnit, TMTextUnit> tmTextUnit;
	public static volatile SingularAttribute<ScreenshotTextUnit, String> renderedTarget;
	public static volatile SingularAttribute<ScreenshotTextUnit, Screenshot> screenshot;
	public static volatile SingularAttribute<ScreenshotTextUnit, String> source;
	public static volatile SingularAttribute<ScreenshotTextUnit, String> target;

}

