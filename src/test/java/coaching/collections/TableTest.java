
package coaching.collections;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * TableTest class.
 */
public class TableTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(TableTest.class);

    /**
     * Unit Test to typical usage.
     */
    @Test
    public void testTypicalUsage() {
        // Given
        final Table table = new Table();
        assertNotNull(table);

        // When we add columns and rows.
        table.addCols("ColOne", "ColTwo");
        table.addRow("ValueOne", "ValueTwo");

        // Then we can produce a table as csv.
        final String csvString = table.toCsvString();
        LOG.trace(csvString);
    }

    /**
     * Unit Test to table.
     */
    @Test
    public void testTable() {
        final Table table = new Table();
        assertNotNull(table);
        LOG.trace(table.toString());
    }

    /**
     * Unit Test to table string.
     */
    @Test
    public void testTableString() {
        final Table table = new Table("TableName");
        assertNotNull(table);
        LOG.trace(table.toString());
    }

    /**
     * Unit Test to table rows.
     */
    @Test
    public void testTableRows() {
        final Table table = new Table();
        assertNotNull(table);
        final TableRow tableRow = new TableRow();
        assertNotNull(tableRow);
        table.add(tableRow);
        LOG.trace(table.toString());
    }

}
