package org.hesperides.domain.modules.exceptions;

import org.hesperides.domain.modules.entities.Template;

public class DuplicateTemplateCreationException extends DuplicateException {
    public DuplicateTemplateCreationException(Template template) {
        super("le template " + template.getName() + " existe déjà.");
    }
}
