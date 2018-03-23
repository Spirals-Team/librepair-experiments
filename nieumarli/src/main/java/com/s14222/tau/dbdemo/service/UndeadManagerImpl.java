package com.s14222.tau.dbdemo.service;

import com.s14222.tau.domain.Undead;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UndeadManagerImpl {


    private Connection connection;
    private PreparedStatement addUndeadStmt;
    private PreparedStatement deleteUndeadsStmt;
    private PreparedStatement updateUndeadsStmt;
    private PreparedStatement getAllUndeadsStmt;

    public UndeadManagerImpl(Connection connection) throws SQLException {
        this.connection = connection;
        if (!isDatabaseReady()) {
            createTables();
        }
        setConnection(connection);
    }


    public UndeadManagerImpl() throws SQLException {
        String url = "jdbc:mysql://localhost/aga";
        try {
            Class.forName("com.mysql.jdbc.Driver");  //STEP 2: Register JDBC driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.connection = DriverManager.getConnection(url,"root", "");
        if (!isDatabaseReady()) {
            createTables();
        }
        setConnection(connection);
    }

    public void createTables() throws SQLException {
        connection.createStatement().executeUpdate(
                "CREATE TABLE `aga`.`Undeads` ( " +
                        "`id` INT NOT NULL AUTO_INCREMENT , " +
                        "`nazwa` VARCHAR(250) NULL , " +
                        "`zycie` INT NULL , " +
                        "`sila` INT NULL , " +
                        "`level` INT NULL , " +
                        "PRIMARY KEY (`id`)) ENGINE = InnoDB;");
    }

    public boolean isDatabaseReady() {
        try {
            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);//pobranie wszystkich tbel z bazy danych
            boolean tableExists = false; //zakladamy ze tabela nie istnieje
            while (rs.next()) { //przegladamy wszystki tabele w kolejcji rs
                if ("undeads".equalsIgnoreCase(rs.getString("TABLE_NAME"))) { //if tabela nazywa sie undead to wejdz w ifa
                    tableExists = true; //nie musimy przegladac dalej
                    break;
                }
            }
            return tableExists;
        } catch (SQLException e) {
            return false;
        }
    }

    public int addUndead(Undead undead) { //dodanie do bazy
        int count = 0;
        try {
            addUndeadStmt.setString(1, undead.getNazwa());
            addUndeadStmt.setInt(2, undead.getZycie());
            addUndeadStmt.setInt(3, undead.getSila());
            addUndeadStmt.setInt(4, undead.getLevel());

            count = addUndeadStmt.executeUpdate(); //ile wierszy sie dodalo

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return count;
    }

    public void deleteUndead(Undead undead){

        if(undead==null) {

            throw new NullPointerException("");
        }

        try {
            deleteUndeadsStmt.setInt(1, undead.getId());
            deleteUndeadsStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Undead> getAllUndeads() {
        List<Undead> undeads = new LinkedList<>();
        try {
            ResultSet rs = getAllUndeadsStmt.executeQuery(); //pobieramy wszystkich

            while (rs.next()) { //zamiana na obiekty
                Undead u = new Undead();
                u.setId(rs.getInt("id"));
                u.setNazwa(rs.getString("nazwa"));
                u.setZycie(rs.getInt("zycie"));
                u.setSila(rs.getInt("sila"));
                u.setLevel(rs.getInt("level"));
                undeads.add(u); //dodanie do listy obiektu (u obiekt)
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return undeads;
    }

public void updateUndead(Undead undead){


    try {
        updateUndeadsStmt.setString(1, undead.getNazwa());
        updateUndeadsStmt.setInt(2,undead.getZycie());
        updateUndeadsStmt.setInt(3,undead.getSila());
        updateUndeadsStmt.setInt(4,undead.getLevel());
        updateUndeadsStmt.setInt(5,undead.getId());
        updateUndeadsStmt.executeUpdate();

    } catch (SQLException e) {

        e.printStackTrace();

    }


    }
    public Connection getConnection() {

        return connection;
    }


    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        addUndeadStmt = connection.
                prepareStatement
                        ("INSERT INTO `undeads` (`id`, `nazwa`, `zycie`, `sila`, `level`) VALUES (NULL, ?, ?, ?, ?);");
        getAllUndeadsStmt = connection.
                prepareStatement("SELECT id, nazwa, zycie, sila, level FROM undeads");
        deleteUndeadsStmt = connection.
                prepareStatement("DELETE FROM `undeads` WHERE `undeads`.`id` = ?");
        updateUndeadsStmt = connection.
                prepareStatement("UPDATE `undeads` SET `nazwa` = ?, `zycie` = ?, `sila` = ?, `level` = ? WHERE `undeads`.`id` = ?;");
    }
}
