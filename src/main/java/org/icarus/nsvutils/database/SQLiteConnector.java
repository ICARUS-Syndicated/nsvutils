package org.icarus.nsvutils.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sqlite.SQLiteDataSource;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class SQLiteConnector {
    public static HikariDataSource database;

    public void initialize(String path) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.sqlite.JDBC");
        config.setPoolName("NSV-SQLite");

        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:" + Paths.get(path));
        config.setDataSource(sqLiteDataSource);

        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setMaximumPoolSize(15);
        config.setKeepaliveTime(0);
        config.setMinimumIdle(5);
        config.setConnectionTestQuery("SELECT 1");
        config.setAutoCommit(true);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("journal_mode", "WAL");
        config.addDataSourceProperty("busy_timeout", "5000"); // 5秒

        database = new HikariDataSource(config);
    }

    public Connection connect() throws SQLException {
        return database.getConnection();
    }

    public void close() {
        database.close();
    }
}
