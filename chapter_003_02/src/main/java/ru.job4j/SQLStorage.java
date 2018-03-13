package ru.job4j;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import java.sql.*;
import java.util.Properties;

public class SQLStorage {
    private static final Logger Log = LoggerFactory.getLogger(SQLStorage.class);

    public static void main(String[] args) {


        Log.info("asdf");

        String url = "jdbc:postgresql://localhost:5432/java_a_from_z";
//        Properties props = new Properties();
//        props.setProperty("user","fred");
//        props.setProperty("password","secret");
//        props.setProperty("ssl","true");
        String username = "postgres";
        String password = "password";
        Connection conn = null;
        try {


            conn = DriverManager.getConnection(url, username, password);

//

            //+
            //+
//            PreparedStatement st = conn.prepareStatement("INSERT INTO users(login, password, create_date) VALUES(?, ?, ?)",
//                    Statement.RETURN_GENERATED_KEYS);
//            st.setString(1, "new java user test 2");
//            st.setString(2, "password");
//            st.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
//            st.executeUpdate();
//            ResultSet generatedKeys = st.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                System.out.println(generatedKeys.getInt(1));
//            }
//
//            //-

//            //+
//            PreparedStatement st = conn.prepareStatement("UPDATE users set login=? WHERE id=?");
//            st.setString(1, "new java user test 3");
//            st.setInt(2, 10);
//            st.executeUpdate();
//
//            //-

            //+
            PreparedStatement st = conn.prepareStatement("DELETE FROM users WHERE id=?");
            st.setInt(1, 10);
            st.executeUpdate();


            //-


            //-

            //+
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * FROM USERS");
            //-

            //+
//            PreparedStatement st  = conn.prepareStatement("SELECT * FROM USERS as u WHERE u.id in (?, ?, ?)");
//            st.setInt(1,3);
//            st.setInt(2, 7);
//            st.setInt(3, 8);
//            ResultSet rs = st.executeQuery();
//            while (rs.next())
//            {
////                System.out.print("Column 1 returned ");
////                System.out.println(rs.getString(1));
//                System.out.println(String.format("%s %s", rs.getString("login"), rs.getTimestamp("create_date")));
//
//            }
//            rs.close();
            st.close();
            //-




        } catch (Exception e) {
            Log.error(e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
//                    e.printStackTrace();
                    Log.error(e.getMessage(), e);
                }
            }
        }





    }

}
