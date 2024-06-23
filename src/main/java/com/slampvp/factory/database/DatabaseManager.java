package com.slampvp.factory.database;

import com.slampvp.factory.FactoryServer;
import com.slampvp.factory.database.queries.CreateTables;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public final class DatabaseManager {
    private static DatabaseManager instance;
    private final HikariDataSource dataSource;
    private final ExecutorService executor;

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
        executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void init() {
        FactoryServer.LOGGER.info("Creating database tables...");
        executeUpdate(CreateTables.TABLES_QUERY).join();
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
        executor.shutdown();
    }

    public CompletableFuture<Void> executeUpdate(String query) {
        return executeUpdate(query, statement -> {});
    }

    public CompletableFuture<Void> executeUpdate(String query, Consumer<PreparedStatement> statementConsumer) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
                try {
                    statementConsumer.accept(pstmt);
                } catch (Exception e) {
                    throw e;
                }
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<ResultSet> executeQuery(String query) {
        return executeQuery(query, statement -> {});
    }

    public CompletableFuture<ResultSet> executeQuery(String query, Consumer<PreparedStatement> statementConsumer) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                statementConsumer.accept(preparedStatement);
                return preparedStatement.executeQuery();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }
}
