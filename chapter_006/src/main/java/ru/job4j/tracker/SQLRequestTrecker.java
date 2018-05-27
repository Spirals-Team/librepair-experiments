package ru.job4j.tracker;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SQLRequestTrecker {
    private final Connection conn;

    public SQLRequestTrecker(Connection conn) {
        this.conn = conn;
    }

    public void executeSQL(String text) {
        try {
            Statement st = conn.createStatement();
            st.execute(text);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeSQL(List<String> list) {
        try {
            Statement st = conn.createStatement();
            for (String text: list) {
                st.execute(text);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Item> getListItems(String type, String value) {
        List<Item> list = new LinkedList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet resultSet;
            if (type == null || value == null) {
                resultSet = st.executeQuery("SELECT * FROM ITEMS");
            } else {
                resultSet = st.executeQuery(String.format("SELECT * FROM ITEMS WHERE %s = '%s'", type, value));
            }
            while (resultSet.next()) {
                PreparedStatement ps = conn.prepareStatement("SELECT COMMENT FROM COMMENTS WHERE ITEM_ID = ?;");
                ps.setInt(1, Integer.parseInt(resultSet.getString(1)));
                ResultSet rs = ps.executeQuery();
                List<String> com = new LinkedList<>();
                while (rs.next()) {
                    com.add(rs.getString(1));
                }
                list.add(new Item(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        com));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addItem(Item item) {
        executeSQL(String.format("INSERT INTO ITEMS (NAME, DES, CREATED) VALUES ('%s', '%s', '%s');",
                item.getName(),
                item.getDesc(),
                new Timestamp(System.currentTimeMillis())));
    }

    public void removeItem(String id) {
        executeSQL(String.format("DELETE FROM ITEMS WHERE ID = %s;", id));
        executeSQL(String.format("DELETE FROM COMMENTS WHERE ITEM_ID = %s;", id));
    }

    public void addComment(String id, String text) {
        executeSQL(String.format("INSERT INTO COMMENTS (ITEM_ID, COMMENT) VALUES ('%s', '%s');", id, text));
    }

    public void replaceItem(String id, Item item) {
        List<Item> list = getListItems("ID", id);
        addComment(id, String.format("REPLACE (%s), old name = %s, old description = %s",
                new Timestamp(System.currentTimeMillis()),
                list.get(0).getName(),
                list.get(0).getDesc()));
        executeSQL(String.format("UPDATE ITEMS SET (NAME, DES) = ('%s', '%s') WHERE ID = '%s';",
                item.getName(),
                item.getDesc(),
                id));
    }
}
