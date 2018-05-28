package com.lala.app;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

// mvn exec:java -Dexec.mainClass="pl.tau.dbdemo.DatabaseExport"

public class DatabaseExport {
    public static void main(String[] args) throws Exception {
        Connection jdbcConnection = DriverManager.getConnection(
                "jdbc:hsqldb:hsql://localhost/workdb", "sa", "");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        FlatXmlDataSet.write(connection.createDataSet(),
                new FileOutputStream("src/test/resources/databaseDump.xml"));
        FlatDtdDataSet.write(connection.createDataSet(),
                new FileOutputStream("src/test/resources/databaseDump.dtd"));
    }

}