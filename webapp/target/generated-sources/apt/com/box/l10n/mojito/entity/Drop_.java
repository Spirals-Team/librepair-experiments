package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.security.user.User;
import com.box.l10n.mojito.service.drop.exporter.DropExporterType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.DateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Drop.class)
public abstract class Drop_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<Drop, PollableTask> exportPollableTask;
	public static volatile SingularAttribute<Drop, Boolean> canceled;
	public static volatile SingularAttribute<Drop, User> createdByUser;
	public static volatile SingularAttribute<Drop, String> name;
	public static volatile SingularAttribute<Drop, String> dropExporterConfig;
	public static volatile SingularAttribute<Drop, Boolean> exportFailed;
	public static volatile SingularAttribute<Drop, DropExporterType> dropExporterType;
	public static volatile SetAttribute<Drop, TranslationKit> translationKits;
	public static volatile SingularAttribute<Drop, Repository> repository;
	public static volatile SingularAttribute<Drop, Boolean> importFailed;
	public static volatile SingularAttribute<Drop, PollableTask> importPollableTask;
	public static volatile SingularAttribute<Drop, DateTime> lastImportedDate;

}

