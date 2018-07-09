
package coaching.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * Provides a example of a crude XML DAO reader.
 *
 * @author martin.spamer
 * @version 0.1 - 12:33:20
 */
public final class XmlDao extends AbstractDao {

    /**
     * The Constructor.
     */
    public XmlDao() {
        super();
    }

    /**
     * The Constructor.
     *
     * @param driverClassName the driver class name
     */
    public XmlDao(final String driverClassName) {
        super(driverClassName);
    }

    /**
     * To xml document.
     *
     * @return the document
     */
    public Document toXmlDocument() {
        try {
            final Document xmlDocument = toXmlDocument(resultSet);
            resultSet.close();
            return xmlDocument;
        } catch (final SQLException e) {
            log.error(e.toString());
        }
        return null;
    }

    /**
     * data as XML.
     *
     * @param resultSet
     *            the result set
     * @return the document
     */
    protected Document toXmlDocument(final ResultSet resultSet) {
        try {
            final Document document = createDocument();

            createTable(document, resultSet);

            return document;
        } catch (final Exception e) {
            log.error(e.toString());
        }
        return null;
    }

    /**
     * Creates the document.
     *
     * @return the document
     */
    private Document createDocument() {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.newDocument();
        } catch (final ParserConfigurationException e) {
            log.error(e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates the table.
     *
     * @param document the document
     * @param resultSet the result set
     */
    private void createTable(final Document document, final ResultSet resultSet) {
        ResultSetMetaData metaData;
        try {
            metaData = resultSet.getMetaData();

            final Element tableElement = document.createElement("TABLE");
            document.appendChild(tableElement);

            while (resultSet.next()) {
                createRow(document, tableElement, resultSet, metaData);
            }

        } catch (final SQLException e) {
            log.error(e.toString());

        }

    }

    /**
     * Creates the row.
     *
     * @param document the document
     * @param results the results
     * @param resultSet the result set
     * @param metaData the meta data
     */
    private void createRow(
            final Document document,
            final Element results,
            final ResultSet resultSet,
            final ResultSetMetaData metaData) {
        final Element row = document.createElement("ROW");
        results.appendChild(row);

        try {
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                final String columnName = metaData.getColumnName(i);
                final Object value = resultSet.getObject(i);

                createCol(document, row, columnName, value);
            }
        } catch (final SQLException e) {
            log.error(e.toString());
        }
    }

    /**
     * Creates the col.
     *
     * @param document the document
     * @param row the row
     * @param columnName the column name
     * @param value the value
     */
    private void createCol(final Document document, final Element row, final String columnName, final Object value) {
        if (value != null) {
            final Element node = document.createElement(columnName);
            final String string = String.format("%s", value);
            final Text createTextNode = document.createTextNode(string);
            node.appendChild(createTextNode);
            row.appendChild(node);
        }
    }

    /**
     * To xml string.
     *
     * @return the string
     */
    public String toXmlString() {
        try {
            final String xmlString = toXmlString(resultSet);
            resultSet.close();
            return xmlString;
        } catch (final SQLException e) {
            log.error(e.toString());
        }
        return null;
    }

    /**
     * data as an XML encoded string.
     *
     * @param resultSet
     *            the result set
     * @return the string
     */
    protected String toXmlString(final ResultSet resultSet) {
        final StringBuilder xml = new StringBuilder();

        try {
            final ResultSetMetaData metaData = resultSet.getMetaData();
            final int colCount = metaData.getColumnCount();

            while (resultSet.next()) {
                xml.append("<TABLE>\n");

                for (int i = 1; i <= colCount; i++) {
                    xml.append("<ROW>\n");
                    final String columnLabel = metaData.getColumnName(i);
                    final Object value = resultSet.getObject(i);
                    final String columnAsXml = columnValue(columnLabel, value);
                    xml.append(columnAsXml);
                }
                xml.append("</ROW>\n");
            }
            xml.append("</TABLE>\n");
            resultSet.close();
        } catch (final Exception e) {
            log.error(e.toString());
        }
        return xml.toString();
    }

    /**
     * Column value.
     *
     * @param columnName
     *            the column name
     * @param value
     *            the value
     * @return the string
     */
    protected String columnValue(final String columnName, final Object value) {
        return String.format("<%s>%s</%s>", columnName, value, columnName);
    }

}
