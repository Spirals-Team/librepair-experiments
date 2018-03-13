package ru.job4j.start;

import java.sql.*;
import java.util.ArrayList;

public class TrackerSQL {

    private String url;

    private String username;
    private String password;

    private Connection conn;

    public TrackerSQL(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.getConn();
    }

    public TrackerSQL() {
    }

    public void initTrackerSQL() {
        this.url = "jdbc:postgresql://localhost:5432/Tracker";
        this.username = "postgres";
        this.password = "password";
        this.getConn();
    }

    ResultSet readFromBase(String query) {

        ResultSet rs = null;
        try (Statement st = this.conn.createStatement()) {
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void deleteAllItemsFromBase() {

        ResultSet rs = readFromBase("select * from items");

        if (rs == null) {

            try (Statement st = this.conn.createStatement()) {
                st.execute("create table items (\n"
                        + "idsql text,\n"
                        + "name text,\n"
                        + "description text,\n"
                        + "createsql timestamp\n"
                        + ");");
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        }
        this.executeDelete("delete  from items");
    }

    public void getConn() {

        try {
            this.conn = DriverManager.getConnection(this.url, this.username, this.password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQueryNew(String query) {
        ResultSet rs = null;
        try (Statement st = conn.createStatement()) {
            rs = st.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }


    public void executeInsert(String query) {
        try (Statement st = conn.createStatement()) {
            st.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeDelete(String query) {
        try (Statement st = conn.createStatement()) {
            st.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSql(Item item) {

        String query = "insert into items(name, description, createsql, idsql) values('" + item.getName() + "', '"
                + item.getDescription() + "', '" + item.getCreateSql() + "', '" + item.getId() + "');";

        this.executeInsert(query);

    }

    public void editSql(Item item) {

        String id = item.getId();

        String query = "update items set name = '" + item.getName() + "' where idsql='" + id + "';";

        String query2 = "update items set description = '" + item.getDescription() + "' where idsql='" + id + "';";

        this.executeInsert(query);

        this.executeInsert(query2);
    }

    public void updateSql(Item item) {
        editSql(item);
    }

    public void deleteSql(Item item) {

        String query = "delete from items where idsql = '" + item.getId() + "';";

        this.executeDelete(query);

    }

    public Item findByIdSql(String id) {

        Item item = null;
        String query = "select * from items where idsql = '" + id + "'";

        ResultSet rs = null;
        try (Statement st = conn.createStatement()) {
            rs = st.executeQuery(query);

            if (rs.next()) {
                item = getItemFromResultSet(rs);
                return item;
            } else {
                return item;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    private Item getItemFromResultSet(ResultSet resultSet) {
        Item item = new Item();

        try {
            String idsql = resultSet.getString("idsql");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            item.setId(idsql);
            item.setName(name);
            item.setDescription(description);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    public Item[] getAllSql() {

        int countOfItems = 0;
        ResultSet resultSet = this.executeQuery("select count(*) AS rowcount   from items;");
        try {
            if (resultSet.next()) {
                countOfItems = resultSet.getInt("rowcount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Item[] result = new Item[countOfItems];

        ResultSet resultSet2 = this.executeQuery("select * from items;");
        int i = 0;
        try {
            while (resultSet2.next()) {
                result[i] = this.getItemFromResultSet(resultSet2);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

    public Item[] findAllSql() {
        return getAllSql();
    }

    public ArrayList<Item> findByNameSql(String name) {
        ArrayList<Item> returnItems = new ArrayList<Item>(); //Item item = null; //new Item();
        String query = "select * from items where name = '" + name + "'";
        ResultSet resultSet = this.executeQuery(query);
        try {
            while (resultSet.next()) {
                returnItems.add(this.getItemFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnItems;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}