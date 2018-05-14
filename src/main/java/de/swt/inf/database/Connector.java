package de.swt.inf.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/swt_karlender?user=foo&password=123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connector() {
        try {
            String resultSet = connection.nativeSQL("CREATE TABLE if not EXISTS `swt_karlender`.`Termin` (\n" +
                    "  `TERMIN_ID` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `Name` VARCHAR(100) NOT NULL,\n" +
                    "  `Start` DATETIME NOT NULL,\n" +
                    "  `Ende` DATETIME NOT NULL,\n" +
                    "  `AllDay` TINYINT NOT NULL,\n" +
                    "  `Location` BLOB NULL,\n" +
                    "  `Repeat` VARCHAR(10) NULL,\n" +
                    "  `Cancel` TINYINT NULL,\n" +
                    "  `Attachement` BLOB NULL,\n" +
                    "  `Note` VARCHAR(200) NULL,\n" +
                    "  `Priority` INT NULL,\n" +
                    "  `Reminder` TINYINT NULL,\n" +
                    "  `VCard` INT NULL,\n" +
                    "  `CancelMsg` VARCHAR(50) NULL,\n" +
                    "  `ReminderDate` DATETIME NULL,\n" +
                    "  `CALENDER_ID` INT NOT NULL,\n" +
                    "  `CATEGORY_ID` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`TERMIN_ID`));\n");
            System.out.println(resultSet);
            resultSet = connection.nativeSQL("");
            System.out.println(resultSet);
            resultSet = connection.nativeSQL("");
            System.out.println(resultSet);
            resultSet = connection.nativeSQL("");
            System.out.println(resultSet);
            resultSet = connection.nativeSQL("");
            System.out.println(resultSet);


            //TODO: 


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
