package org.hesperides.batch.legacy.entities;

import org.hesperides.domain.technos.entities.Techno;
import org.hesperides.domain.templatecontainers.entities.TemplateContainer;

public class LegacyTechno {

    String name;
    String version;
    Boolean working_copy;

    public Techno toDomainInstance() {
        return new Techno(new Techno.Key(name, version, working_copy ? TemplateContainer.VersionType.workingcopy : TemplateContainer.VersionType.release), null);
    }
}
