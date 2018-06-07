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

package net.atf4j.data;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;

import net.atf4j.core.TestResultsReporting;

/**
 * AbstractWalker Class.
 */
public abstract class AbstractFolderWalker extends TestResultsReporting {

    /** The base path. */
    private String basePath = ".";

    /**
     * Instantiates a new abstract walker.
     */
    public AbstractFolderWalker() {
        super();
        this.log.debug("AbstractWalker()");
    }

    /**
     * Instantiates a new abstract walker.
     *
     * @param basePath the path
     */
    public AbstractFolderWalker(final String basePath) {
        super();
        this.log.debug("AbstractWalker({})", basePath);
        setBasePath(basePath);
    }

    /**
     * Sets the base path.
     *
     * @param basePath the base path
     * @return the abstract walker
     */
    public AbstractFolderWalker setBasePath(final String basePath) {
        this.basePath = basePath;
        return this;
    }

    /**
     * Walk the folders under the base path.
     *
     * @return the abstract walker
     */
    protected AbstractFolderWalker walk() {
        return walk(this.basePath);
    }

    /**
     * Walk the folders under the path.
     *
     * @param path the path
     * @return the abstract walker
     */
    protected AbstractFolderWalker walk(final String path) {
        this.log.trace("walk({})", path);
        final URL resource = this.getClass().getResource(path);
        walk(resource);
        return this;
    }

    /**
     * Walk.
     *
     * @param url the url
     */
    protected void walk(final URL url) {
        this.log.trace("walk({})", url);
        if (url != null) {
            URI uri;
            try {
                uri = url.toURI();
                walk(uri);
            } catch (final URISyntaxException e) {
                this.log.error(e.toString());
            }
        } else {
            this.log.error("url is null");
        }
    }

    /**
     * Walk.
     *
     * @param uri the uri
     */
    protected void walk(final URI uri) {
        this.log.trace("walk({})", uri);
        if (uri != null) {
            final File root = Paths.get(uri).toFile();
            walk(root);
        } else {
            this.log.error("uri is null");
        }
    }

    /**
     * Walk.
     *
     * @param root the root
     */
    protected void walk(final File root) {
        this.log.trace("walk({})", root);
        if (root != null) {
            final File[] list = root.listFiles();
            walk(list);
        } else {
            this.log.error("root is null");
        }
    }

    /**
     * Walk.
     *
     * @param list the list
     */
    protected void walk(final File[] list) {
        this.log.trace("walk({})", Arrays.toString(list));
        if (list != null) {
            this.log.trace("length:{}", list.length);
            for (final File file : list) {
                if (file.isDirectory()) {
                    walk(file.getAbsolutePath());
                } else {
                    processFile(file);
                }
            }
        } else {
            this.log.error("listFiles is null");
        }
    }

    /**
     * Process file.
     *
     * @param file the file
     */
    protected void processFile(final File file) {
        this.log.debug("processFile({})", file.getName());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("AbstractWalker [basePath=%s]", this.basePath);
    }

}
