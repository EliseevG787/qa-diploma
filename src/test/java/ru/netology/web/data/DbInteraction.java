package ru.netology.web.data;

import lombok.SneakyThrows;
import lombok.val;

import java.sql.DriverManager;
import java.sql.Statement;

public class DbInteraction {

    @SneakyThrows
    public static Statement getStatement() {
        val url = System.getProperty("url");
        val username = System.getProperty("username");
        val password = System.getProperty("password");
        val conn = DriverManager.getConnection(url, username, password);
        val statement = conn.createStatement();
        return statement;
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        val statement = getStatement();
        val rs = statement.executeQuery("SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1;");
        if (rs.next()) {
            val status = rs.getString("status");
            return status;
        }
        return null;
    }

    @SneakyThrows
    public static String getCreditStatus() {
        val statement = getStatement();
        val rs = statement.executeQuery("SELECT * FROM credit_request_entity ORDER BY created DESC LIMIT 1;");
        if (rs.next()) {
            val status = rs.getString("status");
            return status;
        }
        return null;
    }

    @SneakyThrows
    public static String getPaymentAmount() {
        val statement = getStatement();
        val rs = statement.executeQuery("SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1;");
        if (rs.next()) {
            val amount = rs.getString("amount");
            return amount;
        }
        return null;
    }

    @SneakyThrows
    public static String getCredit_id() {
        val statement = getStatement();
        val rs = statement.executeQuery("SELECT * FROM order_entity ORDER BY created DESC LIMIT 1;");
        if (rs.next()) {
            val credit_id = rs.getString("credit_id");
            return credit_id;
        }
        return null;
    }

    @SneakyThrows
    public static String getTransaction_id() {
        val statement = getStatement();
        val rs = statement.executeQuery("SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1;");
        if (rs.next()) {
            val transaction_id = rs.getString("transaction_id");
            return transaction_id;
        }
        return null;
    }

    @SneakyThrows
    public static String getPayment_id() {
        val statement = getStatement();
        val rs = statement.executeQuery("SELECT * FROM order_entity ORDER BY created DESC LIMIT 1;");
        if (rs.next()) {
            val payment_id = rs.getString("payment_id");
            return payment_id;
        }
        return null;
    }

    @SneakyThrows
    public static String getBank_id() {
        val statement = getStatement();
        val rs = statement.executeQuery("SELECT * FROM credit_request_entity ORDER BY created DESC LIMIT 1;");
        if (rs.next()) {
            val bank_id = rs.getString("bank_id");
            return bank_id;
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



