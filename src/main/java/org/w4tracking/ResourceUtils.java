package org.w4tracking;

import org.w4tracking.models.Model;
import org.w4tracking.representations.idm.AttributesRepresentation;
import org.w4tracking.representations.idm.DataRepresentation;
import org.w4tracking.representations.idm.LinksRepresentation;

public class ResourceUtils {

    private ResourceUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <T extends AttributesRepresentation> DataRepresentation buildData(Model model, T attributes, LinksRepresentation links) {
        return new DataRepresentation.Builder<>()
                .withId(model.getId())
                .withType(model.getType().getAlias())
                .withAttributes(attributes)
                .withLinks(links)
                .build();
    }
}
