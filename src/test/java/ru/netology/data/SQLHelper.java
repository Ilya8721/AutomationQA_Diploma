package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static final String dbUrl = "jdbc:mysql://localhost:3306/app";

    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection(dbUrl, "app", "pass");
    }

    @SneakyThrows
    public static String getDebitPaymentStatus() {
        String SqlStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (var connection = getConnection()) {
            return runner.query(connection, SqlStatus, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static String getCreditPaymentStatus() {
        QueryRunner runner = new QueryRunner();
        String SqlStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (var connection = getConnection()) {
            return runner.query(connection, SqlStatus, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static void cleanBase() {
        var connection = getConnection();
        runner.execute(connection, "TRUNCATE credit_request_entity");
        runner.execute(connection, "TRUNCATE order_entity");
        runner.execute(connection, "TRUNCATE payment_entity");
    }
}
