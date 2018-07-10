package ru.job4j.search;

/**
 * FilenameUtils, to work with file names.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class FilenameUtils {

    private static final char EXTENSION_SEPARATOR = '.';
    private static final int NOT_FOUND = -1;

    /**
     * Gets the extension of a filename.
     *
     * @param filename the filename to retrieve the extension of
     * @return the extension of the file or an empty string
     * if none exists or {@code null}
     */
    public static String getExtension(final String filename) {
        if (filename == null) {
            return null;
        }
        int index = filename.lastIndexOf(EXTENSION_SEPARATOR);
        return (index == NOT_FOUND) ? "" : filename.substring(index + 1);
    }
}
