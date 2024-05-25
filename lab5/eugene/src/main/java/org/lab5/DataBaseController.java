package org.lab5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseController {

    public Connection connectToDataBase(String dataBaseName, String user, String password) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dataBaseName, user, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public void createTable(Connection connection, String tableName) {
        String query = "CREATE TABLE " + tableName +
                " (CONTACT_ID SERIAL, " +
                "FIRST_NAME VARCHAR(50) NOT NULL, " +
                "LAST_NAME VARCHAR(50) NOT NULL, " +
                "PHONE VARCHAR(50) NOT NULL, " +
                "EMAIL VARCHAR(50) NOT NULL, " +
                "PRIMARY KEY (CONTACT_ID));";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertRow(Connection connection, String tableName, String firstName, String lastName, String phone, String email) {
        String query = String.format("INSERT INTO %s(FIRST_NAME, LAST_NAME, PHONE, EMAIL) values('%s', '%s', '%s', '%s');",
                tableName, firstName, lastName, phone, email);
        try {
            connection.createStatement().executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void readAllData(Connection connection, String tableName) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM " + tableName + " ORDER BY CONTACT_ID");
            printTable(resultSet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateRow(Connection connection, String tableName, String id, String param, String newValue) {
        try {
            String query = param.equals("ALL") ? updateAllFields(tableName, id, newValue.split(",")) :
                    String.format("UPDATE %s SET %s='%s' WHERE contact_id='%s'", tableName, param, newValue, id);
            connection.createStatement().executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchByKey(Connection connection, String tableName, String column, String key) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM %s WHERE %s='%s'", tableName, column, key));
            printTable(resultSet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printTable(ResultSet resultSet) {
        try {
            System.out.printf("%-5s | %-10s | %-15s | %-10s | %-10s\n", "ID", "FIRST_NAME", "SECOND_NAME", "PHONE", "EMAIL");
            while (resultSet.next()) {
                System.out.printf("%-5s | %-10s | %-15s | %-10s | %-10s\n", resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String updateAllFields(String tableName, String id, String[] newValues) {
        return String.format(
                "UPDATE %s SET FIRST_NAME='%s', LAST_NAME='%s', PHONE='%s', EMAIL='%s' WHERE contact_id='%s'",
                tableName, newValues[0], newValues[1], newValues[2], newValues[3], id);
    }
}
