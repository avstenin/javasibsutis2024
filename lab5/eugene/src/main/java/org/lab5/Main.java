package org.lab5;

import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String tableName = "phonebook";
        Scanner scanner = new Scanner(System.in);
        DataBaseController dataBaseController = new DataBaseController();

        try (Connection connection = dataBaseController.connectToDataBase("test", "postgres", "123")) {
            if (connection != null)
            {
                boolean running = true;
                while (running) {
                    showMenu();
                    String choice = scanner.nextLine();
                    switch (choice) {
                        case "1":
                            dataBaseController.createTable(connection, tableName);
                            break;
                        case "2":
                            insertRow(scanner, dataBaseController, connection, tableName);
                            break;
                        case "3":
                            dataBaseController.readAllData(connection, tableName);
                            break;
                        case "4":
                            updateRow(scanner, dataBaseController, connection, tableName);
                            break;
                        case "5":
                            searchByKey(scanner, dataBaseController, connection, tableName);
                            break;
                        case "6":
                            running = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Create table");
        System.out.println("2. Insert row");
        System.out.println("3. Read all data");
        System.out.println("4. Update row");
        System.out.println("5. Search by key");
        System.out.println("6. Exit");
    }

    private static void insertRow(Scanner scanner, DataBaseController dataBaseController, Connection connection, String tableName) {
        System.out.println("Enter FIRST NAME:");
        String firstName = scanner.nextLine();
        System.out.println("Enter LAST NAME:");
        String lastName = scanner.nextLine();
        System.out.println("Enter PHONE:");
        String phone = scanner.nextLine();
        System.out.println("Enter EMAIL:");
        String email = scanner.nextLine();
        dataBaseController.insertRow(connection, tableName, firstName, lastName, phone, email);
    }

    private static void updateRow(Scanner scanner, DataBaseController dataBaseController, Connection connection, String tableName) {
        System.out.println("Enter ID to update:");
        String id = scanner.nextLine();
        String param = getUpdateParam(scanner);
        if (param.equals("ALL")) {
            System.out.println("Enter new FIRST NAME:");
            String firstName = scanner.nextLine();
            System.out.println("Enter new LAST NAME:");
            String lastName = scanner.nextLine();
            System.out.println("Enter new PHONE:");
            String phone = scanner.nextLine();
            System.out.println("Enter new EMAIL:");
            String email = scanner.nextLine();
            String newValues = firstName + "," + lastName + "," + phone + "," + email;
            dataBaseController.updateRow(connection, tableName, id, param, newValues);
        } else {
            System.out.println("Enter new " + param + ":");
            String newValue = scanner.nextLine();
            dataBaseController.updateRow(connection, tableName, id, param, newValue);
        }
    }

    private static String getUpdateParam(Scanner scanner) {
        while (true) {
            System.out.println("What do you want to update?");
            System.out.println("1. FIRST NAME");
            System.out.println("2. LAST NAME");
            System.out.println("3. PHONE");
            System.out.println("4. EMAIL");
            System.out.println("5. ALL");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    return "FIRST_NAME";
                case "2":
                    return "LAST_NAME";
                case "3":
                    return "PHONE";
                case "4":
                    return "EMAIL";
                case "5":
                    return "ALL";
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void searchByKey(Scanner scanner, DataBaseController dataBaseController, Connection connection, String tableName) {
        System.out.println("What do you want to find?");
        System.out.println("1. FIRST NAME");
        System.out.println("2. LAST NAME");
        System.out.println("3. PHONE");
        System.out.println("4. EMAIL");
        String column = null;
        while (column == null) {
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    column = "FIRST_NAME";
                    break;
                case "2":
                    column = "LAST_NAME";
                    break;
                case "3":
                    column = "PHONE";
                    break;
                case "4":
                    column = "EMAIL";
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.print("Enter key: ");
        String key = scanner.nextLine();
        dataBaseController.searchByKey(connection, tableName, column, key);
    }
}
