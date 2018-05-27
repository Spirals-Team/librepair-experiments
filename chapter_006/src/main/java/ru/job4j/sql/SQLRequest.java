package ru.job4j.sql;

import java.sql.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SQLRequest {
    private final Connection conn;

    public SQLRequest(Connection conn) {
        this.conn = conn;
    }

    public void createTable() {
        try {
            Statement st = conn.createStatement();
            st.execute("DROP TABLE IF EXISTS TEST;");
            st.execute("CREATE TABLE TEST (FIELD int);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addData(int n) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO TEST VALUES (?)");
            for (int i = 0; i <= n; i++) {
                ps.setInt(1, i);
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MyEntries selectItems() {
        MyEntries myEntries = new MyEntries();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT FIELD FROM TEST"));
            while (rs.next()) {
                MyEntry entry = new MyEntry();
                entry.setField(rs.getInt(1));
                myEntries.addInList(entry);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myEntries;
    }
}
