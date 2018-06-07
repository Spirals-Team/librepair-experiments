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
import java.util.List;

/**
 * The FolderWalkerInterface.
 */
public interface FolderWalkerInterface {

    /**
     * Sets the path.
     *
     * @param path the new path
     */
    public void setPath(String path);

    /**
     * Sets the extension filter.
     *
     * @param extensionFilter the new extension filter
     */
    public void setExtensionFilter(FilenameFilter extensionFilter);

    /**
     * Walk.
     *
     * @return the list
     */
    public List<File> walk();

    /**
     * Walk the path.
     *
     * @param path the path
     * @return the list
     */
    public List<File> walk(String path);

    /**
     * Scan the path.
     *
     * @param path the path
     * @return the list
     */
    public List<File> scan(String path);

    /**
     * Gets the path.
     *
     * @return the path
     */
    public String getPath();

    /**
     * Gets the found files.
     *
     * @return the found files
     */
    public List<File> getFoundFiles();

}
