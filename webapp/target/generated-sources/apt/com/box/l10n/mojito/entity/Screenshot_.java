package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.Screenshot.Status;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Screenshot.class)
public abstract class Screenshot_ extends com.box.l10n.mojito.entity.SettableAuditableEntity_ {

	public static volatile SingularAttribute<Screenshot, Long> sequence;
	public static volatile SingularAttribute<Screenshot, ScreenshotRun> screenshotRun;
	public static volatile SingularAttribute<Screenshot, String> src;
	public static volatile SingularAttribute<Screenshot, String> name;
	public static volatile SetAttribute<Screenshot, ScreenshotTextUnit> screenshotTextUnits;
	public static volatile SingularAttribute<Screenshot, String> comment;
	public static volatile SingularAttribute<Screenshot, Locale> locale;
	public static volatile SingularAttribute<Screenshot, Status> status;

}

