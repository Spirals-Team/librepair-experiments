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
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * FileWalker Class.
 */
public class FileWalker extends AbstractFolderWalker {

    /**
     * Instantiates a new file walker.
     */
    public FileWalker() {
        super();
        this.log.debug("FileWalker()");
    }

    /**
     * Instantiates a new file walker.
     *
     * @param path the path
     * @throws Exception the exception
     */
    public FileWalker(final String path) throws Exception {
        super();
        this.log.debug("FileWalker({})", path);
        setBasePath(path);
    }

    /**
     * Process file.
     *
     * @param f the f
     */
    @Override
    protected void processFile(final File f) {
        try {
            final File absoluteFile = f.getAbsoluteFile();
            this.log.trace("FILE:{}", absoluteFile);
            final String string = readFile(absoluteFile.getPath());
            this.log.debug("path = {}", string);
        } catch (final IOException e) {
            this.log.error(e.toString());
        }
    }

    /**
     * Read file.
     *
     * @param filename the filename
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected String readFile(final String filename) throws IOException {
        final byte[] encoded = Files.readAllBytes(Paths.get(filename));
        return new String(encoded, Charset.defaultCharset());
    }
}
