/**
 * EntityMetaData.java
 * Created on 01 December 2004, 15:46
 **/

package coaching.jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * The Class EntityMetaData.
 */
public class EntityMetaData extends JdbcBase {

    /** The separator. */
    private final char separator = ',';

    /** The header line. */
    private final String headerLine = null;

    /** The field names. */
    private final String fieldNames = null;

    /** The data line. */
    private final String dataLine = null;

    /** The output file. */
    private final File outputFile = null;

    /** The buffered writer. */
    private final BufferedWriter bufferedWriter = null;

    /**
     * The Constructor.
     *
     * @throws Exception the exception
     */
    public EntityMetaData() throws Exception {
        super();
    }

    /**
     * Process.
     *
     * @throws Exception the exception
     */
    public void process() throws Exception {
        super.query();
        toCsvFile(resultSetMetaData);
    }

    /**
     * To csv file.
     *
     * @param resultSetMetaData the result set meta data
     * @throws SQLException the SQL exception
     */
    private void toCsvFile(final ResultSetMetaData resultSetMetaData) throws SQLException {
        final int colCount = resultSetMetaData.getColumnCount();

        // * CSV header line
        final StringBuffer columnLabelLine = new StringBuffer("#");
        for (int column = 1; column < colCount; column++) {
            columnLabelLine.append(resultSetMetaData.getColumnLabel(column) + separator);
        }
        log.info("{}", columnLabelLine);

        // * type line
        final StringBuffer columnTypeLine = new StringBuffer("#");
        for (int column = 1; column < colCount; column++) {
            columnTypeLine.append(resultSetMetaData.getColumnTypeName(column) + separator);
        }
        log.info("{}", columnTypeLine);

        // * CSV header line
        final StringBuffer classTypeLine = new StringBuffer("#");
        for (int column = 1; column < colCount; column++) {
            classTypeLine.append(resultSetMetaData.getColumnClassName(column) + separator);
        }
        log.info("{}", classTypeLine);
    }
}
