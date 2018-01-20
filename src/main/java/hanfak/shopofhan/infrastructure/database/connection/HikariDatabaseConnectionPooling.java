package hanfak.shopofhan.infrastructure.database.connection;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hanfak.shopofhan.infrastructure.properties.PropertiesReader;
import hanfak.shopofhan.infrastructure.properties.Settings;

import javax.sql.DataSource;


// TODO: working on local, need to fix so it works via docker
public class HikariDatabaseConnectionPooling {
    private static final String DATABASE_NAME = "shop_of_han_database";
//    private static final Settings settings = Wiring.settings();
    private static DataSource datasource;


    static DataSource getDataSource() {
        // TODO: How to avoid new up settings here? How to new up this object? Does method need to be static?
        Settings settings = new Settings(new PropertiesReader("localhost"));
        // TODO: Is using static for settings correct way
        if (datasource == null) {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(settings.databaseURL() + DATABASE_NAME);
            config.setUsername(settings.databaseUsername());
            config.setPassword(settings.databasePassword());

            config.setMaximumPoolSize(10);
            config.setAutoCommit(false);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("verifyServerCertificate", "false");
            config.addDataSourceProperty("useSSL", "true");
            config.addDataSourceProperty("connectTimeout", "3000");

            datasource = new HikariDataSource(config);
        }
        return datasource;
    }
}
