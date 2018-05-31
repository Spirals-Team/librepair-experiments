package com.box.l10n.mojito.entity;

import com.box.l10n.mojito.entity.security.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.DateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PollableTask.class)
public abstract class PollableTask_ extends com.box.l10n.mojito.entity.AuditableEntity_ {

	public static volatile SingularAttribute<PollableTask, PollableTask> parentTask;
	public static volatile SingularAttribute<PollableTask, User> createdByUser;
	public static volatile SetAttribute<PollableTask, PollableTask> subTasks;
	public static volatile SingularAttribute<PollableTask, String> name;
	public static volatile SingularAttribute<PollableTask, String> errorMessage;
	public static volatile SingularAttribute<PollableTask, String> message;
	public static volatile SingularAttribute<PollableTask, Integer> expectedSubTaskNumber;
	public static volatile SingularAttribute<PollableTask, String> errorStack;
	public static volatile SingularAttribute<PollableTask, Long> timeout;
	public static volatile SingularAttribute<PollableTask, DateTime> finishedDate;

}

