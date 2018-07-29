package io.vertx.tp.hikari.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import io.vertx.core.json.JsonObject;
import io.vertx.zero.exception.DataSourceException;

/**
 * JsonObject -> HikariConfig
 */
public class HikariCpConfig {

    private transient final HikariPool pool;


    private HikariCpConfig(final JsonObject config) {

        final HikariConfig hikariConfig = new HikariConfig();
        // Init hikariCp, refer to offical site
        // https://github.com/brettwooldridge/HikariCP
        hikariConfig.setDriverClassName(config.getString("driverClassName"));
        hikariConfig.setJdbcUrl(config.getString("jdbcUrl"));
        hikariConfig.setUsername(config.getString("username"));
        hikariConfig.setPassword(config.getString("password"));
        hikariConfig.setCatalog(config.getString("catalog"));
        // Init data source
        try {
            final HikariDataSource dataSource = new HikariDataSource(hikariConfig);
            this.pool = new HikariPool(dataSource);
        } catch (final HikariPool.PoolInitializationException ex) {
            throw new DataSourceException(this.getClass(), ex, hikariConfig.getJdbcUrl());
        }
    }

    public static HikariCpConfig create(final JsonObject config) {
        return new HikariCpConfig(config);
    }

    public HikariPool getPool() {
        return this.pool;
    }
}
