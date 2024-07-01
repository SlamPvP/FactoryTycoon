package com.slampvp.factory.database;

import com.slampvp.factory.FactoryServer;
import com.slampvp.factory.database.queries.CreateTables;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class DatabaseManager {
    private static DatabaseManager instance;
    private final HikariDataSource dataSource;
    private final ExecutorService executor;

    private DatabaseManager() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");

        config.addDataSourceProperty("serverName", "130.61.235.170");
        config.addDataSourceProperty("portNumber", "25576");
        config.addDataSourceProperty("databaseName", "factory");
        config.addDataSourceProperty("user", "pterodactyl");
        config.addDataSourceProperty("password", "Pl3453Ch4n63M3!");

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

    public CompletableFuture<Long> executeUpdate(String query) {
        return executeUpdate(query, statement -> {
        });
    }

    public CompletableFuture<Long> executeUpdate(String query, SQLConsumer<PreparedStatement> statementConsumer) {
        return CompletableFuture.supplyAsync(() -> {
            try (
                    Connection connection = getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
            ) {
                statementConsumer.accept(preparedStatement);
                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }

                return Long.valueOf(-1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<Void> executeQuery(String query) {
        return executeQuery(query, statement -> {
        }, resultSet -> {
        });
    }

    public CompletableFuture<Void> executeQuery(String query,
                                                SQLConsumer<PreparedStatement> statementConsumer,
                                                SQLConsumer<ResultSet> resultSetConsumer) {
        return CompletableFuture.runAsync(() -> {
            try (
                    Connection connection = getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query)
            ) {

                statementConsumer.accept(preparedStatement);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSetConsumer.accept(resultSet);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error executing query", e);
            }
        }, executor);
    }

    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }
}
