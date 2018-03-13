package ru.job4j.task.vacancesfromsqlru;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

class WorkWithDataBase {

    private static final Logger LOGGG = LoggerFactory.getLogger(Sqlru.class);

    Connection conn;

    public WorkWithDataBase(Connection conn) {
        this.conn = conn;
    }

    void addVacancyToDataBase(Vacancy vacancy) {
        if (!this.vacancyExist(vacancy)) {
            String query = "insert into vacancies (name, vacancyurl, pageurl) values ('" + vacancy.name + "', \n"
                    + "'" + vacancy.vacancyurl + "', '" + vacancy.pageurl + "')";
            try (Statement st = conn.createStatement()) {
                st.execute(query);
                String massage = "добавляем = " + vacancy.name + " url: " + vacancy.vacancyurl + " page url: " + vacancy.pageurl;
                System.out.println(massage);
                LOGGG.info("executeProgram()");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGG.error(e.getMessage(), e);
            }
        }
    }

    void addCurrDateToTable() {
        this.createTablesIfNotExist();
        String query = "insert into lastload (loaddate) values ('" + LocalDateTime.now() + "')"; //('2018-01-26T11:38:09.685')";
        try (Statement st = conn.createStatement()) {
            st.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGG.error(e.getMessage(), e);
        }
    }

    boolean vacancyExist(Vacancy vacancy) {
        boolean exist = false;
        String query;
        ResultSet rs = null;
        query = "select * from vacancies as v\n"
                + "where v.vacancyurl = '" + vacancy.vacancyurl + "';";
        try (Statement st = conn.createStatement();) {
            rs = st.executeQuery(query);
            if (rs.next()) {
                exist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGG.error(e.getMessage(), e);
        }
        return exist;
    }

    boolean itIsFirsLoad() {
        this.createTablesIfNotExist();
        boolean itIsFirsLoad = true;
        String query;
        ResultSet rs = null;
        query = "select * from lastload";
        try (Statement st = conn.createStatement();) {
            rs = st.executeQuery(query);
            if (rs.next()) {
                itIsFirsLoad = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGG.error(e.getMessage(), e);
        }
        return itIsFirsLoad;
    }

    void createTableIfNotExist() {
        ResultSet rs = readFromBase("select * from vacancies");
        if (rs == null) {
            try (Statement st = this.conn.createStatement()) {
                st.execute("create table vacancies (\n"
                        + "\n"
                        + "name varchar,\n"
                        + "\n"
                        + "vacancyurl varchar,\n"
                        + "\n"
                        + "pageurl varchar\n"
                        + ")");
            } catch (SQLException e1) {
                e1.printStackTrace();
                LOGGG.error(e1.getMessage(), e1);
            }
        }
    }

    void createTablelastloadIfNotExist() {
        ResultSet rs = readFromBase("select * from lastload");
        if (rs == null) {
            try (Statement st = this.conn.createStatement()) {
                st.execute("create table lastload(\n"
                        + "loaddate timestamp\n"
                        + ")");
            } catch (SQLException e1) {
                e1.printStackTrace();
                LOGGG.error(e1.getMessage(), e1);
            }
        }
    }

    private String getQuaryThisUrlExist(String url, String typeOfquary) {
        String query;
        if (typeOfquary == "old") {
            query = "select * from usedpagesurls as u where u.pageurl = '" + url + "'";
        } else {
            query = "select * from vacancies as v where v.pageurl = '" + url + "';";
        }
        return query;
    }

    private void createTablesIfNotExist() {
        this.createTablelastloadIfNotExist();
        this.createTableIfNotExist();
    }

    boolean thisUrlExist(String url, String newVacOrOld) {
        this.createTablesIfNotExist();
        boolean thisUrlExist = true;
        ResultSet rs = null;
        try (Statement st = this.conn.createStatement()) {
            rs = st.executeQuery(this.getQuaryThisUrlExist(url, newVacOrOld));
            if (!rs.next()) {
                thisUrlExist = false;
            } else if (rs.getString("pageurl") == null) {
                thisUrlExist = false;
            } else {
                thisUrlExist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGG.error(e.getMessage(), e);
        }
        return thisUrlExist;
    }

    ResultSet readFromBase(String query) {
        ResultSet rs = null;
        try (Statement st = this.conn.createStatement()) {
            rs = st.executeQuery(query);
        } catch (SQLException e) {
        }
        return rs;
    }
}