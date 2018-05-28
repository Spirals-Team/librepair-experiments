package com.lala.app;

import java.sql.DriverManager;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.lala.app.repository.MovieRepositoryImpl;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        MovieDBUnitTest.class
})
@Ignore
public class MovieDBUTest {

    @BeforeClass
    public static void before() throws Exception {
          String url = "jdbc:hsqldb:hsql://localhost/workdb";

        new MovieRepositoryImpl(DriverManager.getConnection(url));
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:hsqldb:hsql://localhost/workdb" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "" );

        JdbcDatabaseTester databaseTester = new PropertiesBasedJdbcDatabaseTester();

        FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().build(
            MovieDBUTest.class.getClassLoader().
                        getResource("ds-0.xml").openStream());

        //databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        //databaseTester.setDataSet(dataSet);
        //databaseTester.onSetup();
    }

    @AfterClass
    public static void after() {
    }

}