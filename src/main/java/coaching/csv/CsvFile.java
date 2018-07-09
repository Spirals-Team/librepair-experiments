
package coaching.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represent a comma separated value file class.
 */
public class CsvFile {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(CsvFile.class);

    private final String csvFilename;
    private String headerLine;
    private String[] columnNames;
    private final List<CsvRecord> records = new ArrayList<>();

    private boolean loaded;

    /**
     * Instantiates a new csv file.
     */
    public CsvFile() {
        LOG.info("CsvFile({})");
        this.csvFilename = String.format("%s.csv", this.getClass().getSimpleName());
        initialise();
    }

    /**
     * Instantiates a new csv file from filename.
     *
     * @param csvFilename
     *            the csv filename
     */
    public CsvFile(final String csvFilename) {
        LOG.info("CsvFile({})", csvFilename);
        this.csvFilename = csvFilename;
        initialise();
    }

    /**
     * Initialise.
     */
    private void initialise() {
        try {
            read(this.csvFilename);
        } catch (final IOException e) {
            LOG.error(e.toString());
        }
    }

    /**
     * Gets the header line.
     *
     * @return the headerLine
     */
    public String getHeaderLine() {
        return this.headerLine;
    }

    /**
     * header.
     *
     * @return the header
     */
    public String getHeader() {
        final String colNames = Arrays.toString(this.columnNames);
        return String.format("#%s", colNames);
    }

    /**
     * column names.
     *
     * @return the column names
     */
    public String getColumnNames() {
        return Arrays.toString(this.columnNames);
    }

    /**
     * Read filename.
     *
     * @param filename
     *            the filename
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void read(final String filename) throws IOException {
        LOG.info("read({})", filename);
        if (filename != null) {
            final InputStream resourceAsStream = getClass().getResourceAsStream(filename);
            read(resourceAsStream);
        } else {
            LOG.error("unexpected that filename == null");
        }
    }

    /**
     * Read a resource as a stream.
     *
     * @param resourceAsStream
     *            the resource as stream
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void read(final InputStream resourceAsStream) throws IOException {
        if (resourceAsStream != null) {
            final InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
            read(inputStreamReader);
            resourceAsStream.close();
        } else {
            LOG.error("unexpected that resourceAsStream == null");
        }
    }

    /**
     * Read input stream reader.
     *
     * @param inputStreamReader
     *            the input stream reader
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void read(final InputStreamReader inputStreamReader) throws IOException {
        if (inputStreamReader != null) {
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            read(bufferedReader);
            inputStreamReader.close();
        }
    }

    /**
     * Read.
     *
     * @param bufferedReader
     *            the buffered reader
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void read(final BufferedReader bufferedReader) throws IOException {
        if (bufferedReader != null) {
            String line = bufferedReader.readLine().trim();
            while (line != null) {
                if (line.length() > 0) {
                    processLine(line);
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            this.loaded = true;
        } else {
            LOG.error("unexpected that bufferedReader == null");
        }
    }

    /**
     * Process line.
     *
     * @param line
     *            the line
     */
    protected void processLine(final String line) {
        if (line.charAt(0) == '#') {
            setHeaderLine(line);
        } else {
            final CsvRecord record = new CsvRecord(line);
            this.records.add(record);
            final String recordString = record.toString();
            LOG.debug("recordString = {}", recordString);
        }
    }

    /**
     * header line new header line.
     *
     * @param line
     *            the new header line
     */
    private void setHeaderLine(final String line) {
        this.headerLine = line.substring(1);
        this.columnNames = this.headerLine.split(",");
    }

    /**
     * Write.
     *
     * @param filename
     *            the filename
     */
    public void write(final String filename) {
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (final CsvRecord csvRecord : this.records) {
                LOG.trace("csvRecord = {}", csvRecord);
                writer.write(csvRecord.toString());
            }
            writer.close();
        } catch (final Exception exception) {
            LOG.error(exception.toString());
        }
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    /**
     * record.
     *
     * @param index
     *            the index
     * @return the record
     */
    public CsvRecord getRecord(final int index) {
        return this.records.get(index);
    }

    /**
     * Log pretty.
     */
    public void logPretty() {
        CsvFile.LOG.debug(toString());
    }

    /**
     * Size.
     *
     * @return the int
     */
    public int rowCount() {
        return this.records.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("CsvFile [columnNames=%s, records=%s]", Arrays.toString(this.columnNames), this.records);
    }

}
