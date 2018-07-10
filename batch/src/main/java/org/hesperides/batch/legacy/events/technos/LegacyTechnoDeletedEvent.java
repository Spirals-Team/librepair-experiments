package org.hesperides.batch.legacy.events.technos;

import org.hesperides.batch.legacy.events.LegacyInterface;
import org.hesperides.domain.security.User;
import org.hesperides.domain.technos.TechnoDeletedEvent;
import org.hesperides.domain.technos.entities.Techno;
import org.hesperides.domain.templatecontainers.entities.TemplateContainer;

public class LegacyTechnoDeletedEvent implements LegacyInterface {

    String packageName;
    String packageVersion;
    Boolean workingCopy;

    @Override
    public TemplateContainer.Key getKey() {

        return new Techno.Key(
                packageName,
                packageVersion,
                workingCopy ? TemplateContainer.VersionType.workingcopy : TemplateContainer.VersionType.release);

    }

    @Override
    public Object toDomainEvent(User user) {
        return new TechnoDeletedEvent(getKey(),user);
    }

//    @Override
//    private void updateModel(UserEvent userEvent) {
//        List<AbstractProperty> abstractProperties = AbstractProperty.extractPropertiesFromTemplates(templates.values());
//        AbstractProperty.validateProperties(abstractProperties);
//        apply(new TechnoPropertiesUpdatedEvent(key, abstractProperties, userEvent.getUser()));
//    }

}
