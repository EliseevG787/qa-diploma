package ru.netology.web.data;

//public class DbInteraction {
//
//    public static Connection getConnection() throws SQLException {
//        String url = System.getProperty("url");
//        String username = System.getProperty("username");
//        String password = System.getProperty("password");
//        try {
//            return DriverManager.getConnection(url, username, password);
//        } catch (SQLException err) {
//            err.printStackTrace();
//        }
//        return null;
//    }
//
//    @SneakyThrows
//    public static void cleanDb() {
//        val deleteCreditRequest = "DELETE FROM credit_request_entity";
//        val deleteOrderEntity = "DELETE FROM order_entity";
//        val deletePaymentEntity = "DELETE FROM payment_entity";
//        val runner = new QueryRunner();
//        try (val conn = getConnection();
//        ) {
//            runner.update(conn, deleteCreditRequest);
//            runner.update(conn, deleteOrderEntity);
//            runner.update(conn, deletePaymentEntity);
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
//    }
//
//    @SneakyThrows
//    public static String getPaymentEntity() {
//        try (val conn = getConnection();
//             val countStmt = conn.createStatement()) {
//            val paymentStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
//            val resultSet = countStmt.executeQuery(paymentStatus);
//            if (resultSet.next()) {
//                return resultSet.getString("status");
//            }
//        } catch (SQLException err) {
//            err.printStackTrace();
//        }
//        return null;
//    }
//
//    @SneakyThrows
//    public static String getCreditEntity() {
//        try (val conn = getConnection();
//             val countStmt = conn.createStatement()) {
//            val creditStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
//            val resultSet = countStmt.executeQuery(creditStatus);
//            if (resultSet.next()) {
//                return resultSet.getString("status");
//            }
//        } catch (SQLException err) {
//            err.printStackTrace();
//        }
//        return null;
//    }
//}


import lombok.SneakyThrows;
import lombok.val;

import java.sql.DriverManager;
import java.sql.Statement;

public class DbInteraction {

    @SneakyThrows
    public static Statement getStatement() {
        String url = System.getProperty("url");
        String username = System.getProperty("username");
        String password = System.getProperty("password");
        val conn = DriverManager.getConnection(url, username, password);
        val statement = conn.createStatement();
        return statement;
    }

    @SneakyThrows
    public static String paymentStatus() {
        val statement = getStatement();
        val rs = statement.executeQuery("SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1;");
        if (rs.next()) {
            val status = rs.getString("status");
            return status;
        }
        return null;
    }

    @SneakyThrows
    public static String creditStatus() {
        val statement = getStatement();
        val rs = statement.executeQuery("SELECT * FROM credit_request_entity ORDER BY created DESC LIMIT 1;");
        if (rs.next()) {
            val status = rs.getString("status");
            return status;
        }
        return null;
    }


    @SneakyThrows
    public static void cleanDatabase() {
        val statement = getStatement();
        statement.executeUpdate("DELETE FROM  payment_entity;");
        statement.executeUpdate("DELETE FROM  order_entity;");
        statement.executeUpdate("DELETE FROM  credit_request_entity;");
    }
}



