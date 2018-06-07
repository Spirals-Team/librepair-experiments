/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.amq;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.atf4j.core.TestResultsReporting;

/**
 * An abstract class to walk folders.
 */
public abstract class AbstractFolderWalker
        extends TestResultsReporting
        implements FolderWalkerInterface {

    /** base path. */
    private String path = ".";

    /** filename filter. */
    private FilenameFilter filter = new Unfiltered();

    /** list of found files. */
    private final List<File> foundFiles = new ArrayList<File>();

    /**
     * Unfiltered filename class.
     */
    public class Unfiltered implements FilenameFilter {

        /*
         * (non-Javadoc)
         *
         * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
         */
        @Override
        public boolean accept(final File dir, final String filename) {
            AbstractFolderWalker.this.log.trace("accept({},{})", dir, filename);
            return true;
        }
    }

    /**
     * Instantiates a new abstract folder walker.
     */
    public AbstractFolderWalker() {
        super();
    }

    /**
     * Instantiates a new abstract folder walker.
     *
     * @param path the path
     */
    public AbstractFolderWalker(final String path) {
        super();
        setPath(path);
    }

    /**
     * Instantiates a new abstract folder walker.
     *
     * @param extensionFilter the extension filter
     */
    public AbstractFolderWalker(final FilenameFilter extensionFilter) {
        super();
        setExtensionFilter(extensionFilter);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.amq.FolderWalkerInterface#setPath(java.lang.String)
     */
    @Override
    public void setPath(final String path) {
        this.path = path;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.amq.FolderWalkerInterface#setExtensionFilter(java.io.
     * FilenameFilter)
     */
    @Override
    public void setExtensionFilter(final FilenameFilter extensionFilter) {
        filter = extensionFilter;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.amq.FolderWalkerInterface#walk()
     */
    @Override
    public List<File> walk() {
        return walk(path);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.amq.FolderWalkerInterface#walk(java.lang.String)
     */
    @Override
    public List<File> walk(final String path) {
        if (path != null) {
            final File dir = new File(path);
            log.debug("dir = {}", dir.getAbsolutePath());
            log.debug("file = {}", dir.getAbsoluteFile());
            final File[] files = dir.listFiles(filter);
            if (files != null) {
                for (final File file : files) {
                    if (file.isDirectory()) {
                        final String subDir = file.getAbsolutePath();
                        walk(subDir);
                    } else {
                        foundFiles.add(file);
                    }
                }
            }
        }
        return foundFiles;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.amq.FolderWalkerInterface#scan(java.lang.String)
     */
    @Override
    public List<File> scan(final String path) {
        final File root = new File(path);
        for (final File file : root.listFiles()) {
            if (file.isDirectory()) {
                final String subDir = file.getAbsolutePath();
                log.debug("{}", subDir);
                scan(subDir);
            } else {
                log.debug("{}", file);
                foundFiles.add(file);
            }
        }
        return foundFiles;
    }

    /**
     * File from resource URL.
     *
     * @param path the path
     * @return the file
     */
    protected File fileFromResourceURL(final String path) {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        final URL url = classLoader.getResource(path);
        File file = null;
        URI uri;
        try {
            uri = url.toURI();
            file = new File(uri);
        } catch (final URISyntaxException e) {
            log.error(e.toString());
        }
        return file;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.amq.FolderWalkerInterface#getPath()
     */
    @Override
    public String getPath() {
        return path;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.amq.FolderWalkerInterface#getFoundFiles()
     */
    @Override
    public List<File> getFoundFiles() {
        return foundFiles;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s [path=%s, filter=%s, foundFiles=%s]",
                this.getClass().getSimpleName(),
                path,
                filter,
                foundFiles);
    }

}
