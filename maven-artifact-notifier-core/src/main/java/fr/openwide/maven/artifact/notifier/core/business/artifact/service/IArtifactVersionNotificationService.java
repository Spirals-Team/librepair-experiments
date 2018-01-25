package fr.openwide.maven.artifact.notifier.core.business.artifact.service;

import java.util.List;
import java.util.Map;

import fr.openwide.core.jpa.business.generic.service.IGenericEntityService;
import fr.openwide.maven.artifact.notifier.core.business.artifact.model.Artifact;
import fr.openwide.maven.artifact.notifier.core.business.artifact.model.ArtifactVersionNotification;
import fr.openwide.maven.artifact.notifier.core.business.user.model.User;

public interface IArtifactVersionNotificationService extends IGenericEntityService<Long, ArtifactVersionNotification> {

	List<ArtifactVersionNotification> listByArtifact(Artifact artifact);

	Map<User, List<ArtifactVersionNotification>> listNotificationsToSend();
}
