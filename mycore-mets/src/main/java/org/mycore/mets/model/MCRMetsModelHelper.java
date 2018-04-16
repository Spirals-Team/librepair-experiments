package org.mycore.mets.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mycore.common.config.MCRConfiguration2;
import org.mycore.common.config.MCRConfigurationException;

public class MCRMetsModelHelper {

    public static final String TRANSLATION_USE = "TEI.TRANSLATION";

    public static final String TRANSCRIPTION_USE = "TEI.TRANSCRIPTION";

    public static final String ALTO_USE = "ALTO";

    public static final String MASTER_USE = "MASTER";

    protected static final String ALLOWED_TRANSLATION_PROPERTY = "MCR.METS.ALLOWED_TRANSLATION_SUBFOLDER";

    private static final Set<String> ALLOWED_TRANSLATION = MCRConfiguration2.getString(ALLOWED_TRANSLATION_PROPERTY)
        .map(folders -> folders.split(","))
        .map(Arrays::asList)
        .map(HashSet::new)
        .map(Set.class::cast)
        .orElse(Collections.emptySet());

    private static final Logger LOGGER = LogManager.getLogger();

    public static Optional<String> getUseForHref(final String href) {
        final int lastFolderPosition = href.lastIndexOf("/");
        final String path = (lastFolderPosition == -1) ? "" : href.substring(0, lastFolderPosition);

        if (path.startsWith("tei/")) {

            final String[] pathParts = path.split("/");
            if (pathParts.length == 1) {
                LOGGER.warn("Could not detect the file group of " + href);
                return Optional.empty();
            }

            final String teiType = pathParts[1];
            if (teiType.equals("transcription")) {
                return Optional.of(TRANSCRIPTION_USE);
            } else if (teiType.startsWith("translation.")) {
                final String translation = teiType.split("[.]",2)[1];
                if (!ALLOWED_TRANSLATION.contains(translation)) {
                    LOGGER.warn(
                        "Can not detect file group because " + translation + " is not in list of allowed translations "
                            + ALLOWED_TRANSLATION_PROPERTY);
                    return Optional.empty();
                }

                return Optional.of(TRANSLATION_USE + "." + translation.toUpperCase(Locale.ROOT));
            } else {
                return Optional.empty();
            }

        } else if (href.startsWith("alto/")) {
            return Optional.of(ALTO_USE);
        }
        return Optional.of(MASTER_USE);
    }

}
