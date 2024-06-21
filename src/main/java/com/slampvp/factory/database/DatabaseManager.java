package com.slampvp.factory.database;

import com.slampvp.factory.FactoryServer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class DatabaseManager {
    private static DatabaseManager instance;
    private final HikariDataSource dataSource;

    private DatabaseManager() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");

        config.addDataSourceProperty("serverName", "localhost");
        config.addDataSourceProperty("portNumber", "5432");
        config.addDataSourceProperty("databaseName", "factory");
        config.addDataSourceProperty("user", "postgres");
        config.addDataSourceProperty("password", "password");

        config.setMaximumPoolSize(5);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);

        dataSource = new HikariDataSource(config);
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void init() {
        FactoryServer.LOGGER.info("Creating database tables...");
        executeUpdate(DatabaseTables.TABLES_QUERY);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    public void executeUpdate(String query) {
        executeUpdate(query, statement -> {
        });
    }

    public void executeUpdate(String query, Consumer<PreparedStatement> statementConsumer) {
        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            statementConsumer.accept(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeQuery(String query, Consumer<ResultSet> resultSetConsumer) {
        executeQuery(query, statement -> {
        }, resultSetConsumer);
    }

    public void executeQuery(String query, Consumer<PreparedStatement> statementConsumer, Consumer<ResultSet> resultSetConsumer) {
        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            statementConsumer.accept(pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                resultSetConsumer.accept(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
