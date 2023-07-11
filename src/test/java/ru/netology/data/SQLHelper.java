package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private static final String dbUrl = System.getProperty("datasource.url");

    private SQLHelper() {
    }


    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection(dbUrl, "app", "pass");
    }

    @SneakyThrows
    public static String getDebitPaymentStatus() {
        String SqlStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        return runner.query(getConnection(), SqlStatus, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getCreditPaymentStatus() {
        String SqlStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        return runner.query(getConnection(), SqlStatus, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void cleanBase() {
        runner.execute(getConnection(), "TRUNCATE credit_request_entity");
        runner.execute(getConnection(), "TRUNCATE order_entity");
        runner.execute(getConnection(), "TRUNCATE payment_entity");
    }
}
