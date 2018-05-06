package org.sjc.transparencia;

import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;

import java.util.UUID;

public class Dao {

    private static Sql2o connection;


    public static Sql2o connect() {
        connection = new Sql2o("jdbc:postgresql://localhost:5432/transparencia_development",
                "postgres", "", new PostgresQuirks() {
            {
                converters.put(UUID.class, new UUIDConverter());
            }
        });
        return connection;
    }

    public static Sql2o getConnection() {
        if (connection == null) {
            connection = connect();
        }
        return connection;
    }
}
