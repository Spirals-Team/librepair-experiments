
package coaching.jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * The Class JdbcToXml.
 */
class JdbcToXml extends JdbcBase {

    /**
     * The Constructor.
     *
     * @throws Exception the exception
     */
    public JdbcToXml() throws Exception {
        super();
    }

    /**
     * Process.
     *
     * @throws Exception the exception
     */
    public void process() throws Exception {
        super.query();
        toXmlFile();
    }

    /**
     * To xml file.
     */
    public void toXmlFile() {
        try {
            final String tableName = resultSetMetaData.getTableName(1);
            final String filename = String.format("%s.xml", tableName);
            toXmlFile(filename);
        } catch (final Exception e) {
            log.error(e.toString());
        }
    }

    /**
     * To xml file.
     *
     * buffered writer
     *
     * @param bufferedWriter the buffered writer
     */
    public void toXmlFile(final BufferedWriter bufferedWriter) {
        try {
            bufferedWriter.write(toXmlString());
            bufferedWriter.flush();
        } catch (final Exception e) {
            log.error(e.toString());
        }
    }

    /**
     * To xml file.
     *
     * filename
     *
     * @param filename the filename
     */
    public void toXmlFile(String filename) {
        try {
            if (filename.length() == 0) {
                filename = resultSetMetaData.getTableName(1) + ".xml";
            }

            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(filename)));

            toXmlFile(bufferedWriter);

            bufferedWriter.close();
        } catch (final Exception e) {
            log.error(e.toString());
        }
    }

    /**
     * To xml string.
     *
     * string
     *
     * @return the string
     */
    public String toXmlString() {
        StringBuffer xml = null;

        if (resultSet != null && resultSetMetaData != null) {
            try {
                final int colCount = resultSetMetaData.getColumnCount();
                xml = new StringBuffer();
                xml.append("<TABLE>\n");

                while (resultSet.next()) {
                    xml.append("\t<ROW>\n\t\t");

                    for (int i = 1; i <= colCount; i++) {
                        final String columnName = resultSetMetaData.getColumnName(i);
                        final Object value = resultSet.getObject(i);

                        if (value != null) {
                            xml.append("<FIELD NAME=\"" + columnName + "\">");
                            xml.append(value.toString().trim());
                            xml.append("</FIELD>");
                        }
                    }
                    xml.append("\n\t\t</ROW>\n");
                }

                xml.append("</TABLE>\n");
                return xml.toString();
            } catch (final Exception e) {
                log.error(e.toString());
            }
        }
        return null;
    }
}
