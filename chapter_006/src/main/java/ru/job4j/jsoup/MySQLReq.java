package ru.job4j.jsoup;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Date;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class MySQLReq {
    private static Logger log = Logger.getLogger(MySQLReq.class);
    private final Connection conn;

    public MySQLReq(Connection conn) {
        this.conn = conn;
    }

    public void createTable() {
        try {
            PreparedStatement ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS JOB (id serial PRIMARY KEY, title CHARACTER VARYING(500) NOT NULL, text CHARACTER VARYING(10000) NOT NULL, url CHARACTER VARYING(1000) NOT NULL, created TIMESTAMP NOT NULL);");
            ps.executeUpdate();
            Statement st = conn.createStatement();
        } catch (SQLException e) {
            log.error(e);
        }
    }

    public void add(String url, String title, String text, Date time) {
        if (newURL(url)) {
//            log.info(String.format("%s %s %s", time, url, title));
            try {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO JOB (TITLE, TEXT, URL, CREATED) VALUES (?, ?, ?, ?);");
                ps.setString(1, title);
                ps.setString(2, text);
                ps.setString(3, url);
                ps.setTimestamp(4, new Timestamp(time.getTime()));
                ps.executeUpdate();
            } catch (SQLException e) {
                log.error(e);
            }
        }
    }

    private boolean newURL(String url) {
        boolean flagNew = true;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT URL FROM JOB WHERE URL = ?;");
            ps.setString(1, url);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                flagNew = false;
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return flagNew;
    }

    public boolean isEmptyBD() {
        boolean flagNew = true;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM JOB;");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                flagNew = false;
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return flagNew;
    }

    public Date lastJob() {
        Date date = null;
        try {
            PreparedStatement ps = conn.prepareStatement("select created from job order by created desc limit 1;");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getTimestamp(1));
                date = new Date(rs.getTimestamp(1).getTime() - 1);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return date;
    }
}
