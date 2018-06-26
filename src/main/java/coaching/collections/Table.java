
package coaching.collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Table class.
 */
public class Table {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The table name. */
    private String tableName;

    /** The col names. */
    private final List<String> colNames = new ArrayList<>();

    /** The rows. */
    private final List<TableRow> rows = new ArrayList<>();

    /**
     * Instantiates a new table.
     */
    public Table() {
        super();
    }

    /**
     * Instantiates a new table.
     *
     * @param tableName
     *            the table name
     */
    public Table(final String tableName) {
        this.tableName = tableName;
    }

    /**
     * Sets the table name.
     *
     * @param tableName
     *            the tableName to set
     * @return the table
     */
    public Table setTableName(final String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * Gets the table name.
     *
     * @return the tableName
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * Adds an cols.
     *
     * @param colNames
     *            the col names
     */
    public void addCols(final String... colNames) {
        this.colNames.addAll(Arrays.asList(colNames));
    }

    /**
     * Adds an row.
     *
     * @param values
     *            the values
     */
    public void addRow(final String... values) {
        add(new TableRow(values));
    }

    /**
     * Adds a table row.
     *
     * @param e
     *            the e
     * @return the boolean
     * @see java.util.List#add(java.lang.Object)
     */
    public boolean add(final TableRow e) {
        return this.rows.add(e);
    }

    /**
     * Gets the row at index.
     *
     * @param index
     *            the index
     * @return the table row
     * @see java.util.List#get(int)
     */
    public TableRow get(final int index) {
        return this.rows.get(index);
    }

    /**
     * To csv string.
     *
     * @return the string
     */
    public String toCsvString() {
        return "\n" + tableHeader() + tableBody();
    }

    /**
     * Table header.
     *
     * @return the string
     */
    protected String tableHeader() {
        final StringBuilder stringBuffer = new StringBuilder();

        final Iterator<String> itemIterator = this.colNames.iterator();
        if (itemIterator.hasNext()) {
            stringBuffer.append(itemIterator.next());
            while (itemIterator.hasNext()) {
                stringBuffer.append(',');
                stringBuffer.append(itemIterator.next());
            }
        }

        stringBuffer.append('\n');
        return stringBuffer.toString();
    }

    /**
     * Table body.
     *
     * @return the string
     */
    protected String tableBody() {
        final StringBuilder stringBuffer = new StringBuilder();

        final Iterator<TableRow> tableRow = this.rows.iterator();
        if (tableRow.hasNext()) {
            stringBuffer.append(tableRow.next());
            while (tableRow.hasNext()) {
                stringBuffer.append(',');
                stringBuffer.append(tableRow.next().toRowString());
            }
        }

        stringBuffer.append('\n');
        return stringBuffer.toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Table [tableName=%s, colNames=%s, rows=%s]", this.tableName, this.colNames, this.rows);
    }

}
