
package coaching.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A TableRow composed of TableCells class.
 */
public class TableRow {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The cols. */
    private final List<TableCell> cols = new ArrayList<>();

    /**
     * Instantiates a new table row.
     *
     * @param values
     *            the values
     */
    public TableRow(final String... values) {
        for (final String value : values) {
            final TableCell tableCell = new TableCell(value);
            this.cols.add(tableCell);
        }
    }

    /**
     * To row string.
     *
     * @return the string
     */
    public String toRowString() {
        final StringBuilder stringBuffer = new StringBuilder();

        final Iterator<TableCell> tableRow = this.cols.iterator();
        if (tableRow.hasNext()) {
            stringBuffer.append(tableRow.next());
            while (tableRow.hasNext()) {
                stringBuffer.append(',');
                stringBuffer.append(tableRow.next());
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
        return String.format("%s [cols=%s]", this.getClass().getSimpleName(), Collections.singletonList(this.cols));
    }

}
