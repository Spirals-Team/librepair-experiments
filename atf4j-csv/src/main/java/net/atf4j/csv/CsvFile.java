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

package net.atf4j.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class to represent a CSV data file.
 */
public class CsvFile {

    private HeaderLine header;
    private final List<CsvRow> data = new ArrayList<CsvRow>();
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * Default Constructor instantiates a new empty CsvFile object.
     */
    public CsvFile() {
        super();
        load();
    }

    /**
     * Instantiates a new CSV file.
     *
     * @param dataFilename the data filename
     * @throws FileNotFoundException the file not found exception
     */
    public CsvFile(final String dataFilename) throws FileNotFoundException {
        super();
        load(dataFilename);
    }

    /**
     * Factory Method returns file as CsvFile INSTANCE.
     *
     * @param dataFilename the data filename
     * @return the csv file
     * @throws FileNotFoundException the file not found exception
     */
    public static CsvFile read(final String dataFilename) throws FileNotFoundException {
        return new CsvFile(dataFilename);
    }

    /**
     * Load.
     */
    protected void load() {
        try {
            load(configFilename());
        } catch (final FileNotFoundException e) {
            log.warn(e.toString());
        }
    }

    /**
     * Configuration filename.
     *
     * @return the string
     */
    private String configFilename() {
        final String simpleName = this.getClass().getSimpleName();
        return String.format("%s.csv", simpleName);
    }

    /**
     * Load.
     *
     * @param csvFilename the data filename
     * @throws FileNotFoundException the file not found exception
     */
    public void load(final String csvFilename) throws FileNotFoundException {
        final InputStream resourceAsStream = resourceAsStream(csvFilename);
        if (resourceAsStream != null) {
            load(resourceAsStream);
        } else {
            final String message = String.format("Expected '%s'", csvFilename);
            throw new FileNotFoundException(message);
        }
    }

    /**
     * Load.
     *
     * @param inputStream the InputStream
     */
    public void load(final InputStream inputStream) {
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String line = bufferedReader.readLine().trim();
            if (line.charAt(0) == '#') {
                header = new HeaderLine(line.substring(1));
            } else {
                data.add(new CsvRow(line));
            }

            while ((line = bufferedReader.readLine()) != null) {
                data.add(new CsvRow(line));
            }
            bufferedReader.close();

        } catch (final IOException e) {
            log.error(e.toString());
        }
    }

    /**
     * Resource as stream.
     *
     * @param resourceFilename the resource filename
     * @return the input stream
     */
    private InputStream resourceAsStream(final String resourceFilename) {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        return classLoader.getResourceAsStream(resourceFilename);
    }

    /**
     * Gets the header.
     *
     * @return the header
     */
    public HeaderLine getHeaderLine() {
        return header;
    }

    /**
     * Column count.
     *
     * @return the int
     */
    public int columnCount() {
        return header.length();
    }

    /**
     * Gets the column names.
     *
     * @return the column names
     */
    public String[] getColumnNames() {
        return header.getFields();
    }

    /**
     * Gets the header for a column.
     *
     * @param columnNumber the column number
     * @return the header
     */
    public String getColumnName(final int columnNumber) {
        return header.getField(columnNumber);
    }

    /**
     * Length.
     *
     * @return the int
     */
    public int rowCount() {
        return data.size();
    }

    /**
     * Get a row by index.
     *
     * @param index line number
     * @return the csv row
     * @see java.util.List#get(int)
     */
    public CsvRow getRow(final int index) {
        return data.get(index - 1);
    }

    /**
     * return the CSV file data as an array of rows.
     *
     * @return the object[]
     * @see java.util.List#toArray()
     */
    public Object[] toArray() {
        return data.toArray();
    }

    /**
     * To string.
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String debugString() {
        final String simpleName = this.getClass().getSimpleName();
        return String.format("%s [header=%s, data=%s]", simpleName, header.toString(), data.toString());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final String simpleName = this.getClass().getSimpleName();
        return String.format("%s [header=%s, data=%s]", simpleName, header, data);
    }

}
