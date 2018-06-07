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

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * The FolderWalkerTest Class.
 */
public class FolderWalkerTest extends TestResultsReporting {

    /**
     * Unit Test the FolderWalker Class.
     */
    public class FolderWalker extends AbstractFolderWalker {

        /**
         * The ExtensionFilter Class.
         */
        public class ExtensionFilter implements FilenameFilter {

            /** The extension. */
            private final String extension;

            /**
             * Instantiates a new extension filter.
             *
             * @param extension the extension
             */
            public ExtensionFilter(final String extension) {
                this.extension = extension;
            }

            /*
             * (non-Javadoc)
             *
             * @see java.io.FilenameFilter#accept(java.io.File,
             * java.lang.String)
             */
            @Override
            public boolean accept(final File dir, final String filename) {
                return filename.endsWith(extension);
            }
        }

        /**
         * Instantiates a new folder walker.
         */
        public FolderWalker() {
            super();
        }

        /**
         * Instantiates a new folder walker.
         *
         * @param path the path
         */
        public FolderWalker(final String path) {
            super(path);
        }
    }

    /**
     * Unit test folder walker.
     */
    @Test
    public void testFolderWalker() {
        log.debug("testFolderWalker");
        final FolderWalkerInterface folderWalker = new FolderWalker();
        verifyNotNull(folderWalker);
        folderWalker.walk();
    }

    /**
     * Unit test folder walker null.
     */
    @Test
    public void testFolderWalkerNull() {
        log.debug("testFolderWalkerNull");
        final FolderWalkerInterface folderWalker = new FolderWalker(null);
        verifyNotNull(folderWalker);
        folderWalker.walk();
    }

    /**
     * Unit test folder walker empty.
     */
    @Test
    public void testFolderWalkerEmpty() {
        log.debug("testFolderWalkerEmpty");
        final FolderWalkerInterface folderWalker = new FolderWalker("");
        verifyNotNull(folderWalker);
        folderWalker.walk();
    }

    /**
     * Unit test folder walker dot.
     */
    @Test
    public void testFolderWalkerDot() {
        log.debug("testFolderWalkerDot");
        final FolderWalkerInterface folderWalker = new FolderWalker(".");
        verifyNotNull(folderWalker);
        folderWalker.walk();
    }

    /**
     * Unit test folder walker string.
     */
    @Test
    public void testFolderWalkerString() {
        log.debug("testFolderWalkerString");
        final FolderWalkerInterface folderWalker = new FolderWalker("messages");
        verifyNotNull(folderWalker);
        folderWalker.walk();
    }
}
