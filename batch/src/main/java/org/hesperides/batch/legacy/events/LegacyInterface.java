package org.hesperides.batch.legacy.events;

import org.hesperides.domain.security.User;
import org.hesperides.domain.templatecontainers.entities.TemplateContainer;

public interface LegacyInterface {
    TemplateContainer.Key getKey();
    Object toDomainEvent(User user);

//    void updateModel(UserEvent userEvent);
}
