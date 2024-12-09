import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/databaseproject";
    private static final String USER = "root";
    private static final String PASSWORD = "1203";
    private static Scanner scanner = new Scanner(System.in);
    public static int id = 0;
    public static String query;

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            System.out.println("Connected successfully!");

            boolean exit = false;

            while (!exit) {
                System.out.println("Choose an operation number:");
                System.out.println("1. Add record");
                System.out.println("2. Delete record");
                System.out.println("3. Update record");
                System.out.println("4. Execute queries");
                System.out.println("5. Exit");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addRecord(conn);
                        break;
                    case 2:
                        deleteRecord(conn);
                        break;
                    case 3:
                        updateRecord(conn);
                        break;
                    case 4:
                        executeQueries(conn);
                        break;
                    case 5:
                        exit = true;
                        scanner.close();
                        break;
                    default:
                        System.out.println("Not an option. Try again.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addRecord(Connection conn) {
        System.out.println("Adding a new customer record.");
        int customerId = 0;

        System.out.println("Enter Name:");
        String name = scanner.next();
        System.out.println("Enter Contact Information:");
        scanner.nextLine();
        String contactInfo = scanner.nextLine();
        System.out.println("Enter Billing Details:");
        String billingDetails = scanner.nextLine();

        String sql = "INSERT INTO CUSTOMER (Customer_ID, Name, Contact_Information, Billing_Details) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.setString(2, name);
            pstmt.setString(3, contactInfo);
            pstmt.setString(4, billingDetails);
            pstmt.executeUpdate();
            System.out.println("Record added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteRecord(Connection conn) {
        System.out.println("Deleting a customer record.");

        System.out.println("Enter Customer ID to delete:");
        int customerId = scanner.nextInt();

        String sql = "DELETE FROM CUSTOMER WHERE Customer_ID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
            System.out.println("Record deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateRecord(Connection conn) {
        System.out.println("Updating a customer record.");

        System.out.println("Enter Customer ID to update:");
        int customerId = scanner.nextInt();
        System.out.println("Enter new Name:");
        String name = scanner.next();
        System.out.println("Enter new Contact Information:");
        scanner.nextLine();
        String contactInfo = scanner.nextLine();
        System.out.println("Enter new Billing Details:");
        String billingDetails = scanner.nextLine();

        String sql = "UPDATE CUSTOMER SET Name = ?, Contact_Information = ?, Billing_Details = ? WHERE Customer_ID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, contactInfo);
            pstmt.setString(3, billingDetails);
            pstmt.setInt(4, customerId);
            pstmt.executeUpdate();
            System.out.println("Record updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void executeQueries(Connection conn) {
        System.out.println("Choose a stored procedure number:");
        System.out.println("1. Find customer ID based on name.");
        System.out.println("2. Find customer name based on ID.");
        System.out.println("3. Find billing info based on ID.");
        System.out.println("4. Find contact info based on ID.");
        System.out.println("5. Find all details based off ID.");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter customer name: ");
                String name = scanner.next();

                query = "{CALL findID(?, ?)}";
                try (CallableStatement storedProcedure = conn.prepareCall(query)) {

                    storedProcedure.setString(1, name);
                    storedProcedure.registerOutParameter(2, Types.INTEGER);

                    storedProcedure.execute();

                    int customerID = storedProcedure.getInt(2);

                    if (customerID != 0) {
                        System.out.println("Customer ID: " + customerID);
                    } else {
                        System.out.println("Customer not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case 2:
                System.out.print("Enter customer ID: ");
                int input = scanner.nextInt();

                query = "{CALL findName(?, ?)}";
                try (CallableStatement storedProcedure = conn.prepareCall(query)) {

                    storedProcedure.setInt(1, input);
                    storedProcedure.registerOutParameter(2, Types.VARCHAR);

                    storedProcedure.execute();

                    String customerName = storedProcedure.getString(2);

                    if (customerName != null) {
                        System.out.println("Customer Name: " + customerName);
                    } else {
                        System.out.println("Customer not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;

            case 3:
                System.out.print("Enter customer ID: ");
                input = scanner.nextInt();

                query = "{CALL findBillingInfo(?, ?)}";
                try (CallableStatement storedProcedure = conn.prepareCall(query)) {

                    storedProcedure.setInt(1, input);
                    storedProcedure.registerOutParameter(2, Types.VARCHAR);

                    storedProcedure.execute();

                    String billingInfo = storedProcedure.getString(2);

                    if (billingInfo != null) {
                        System.out.println("Customer's Billing Info: " + billingInfo);
                    } else {
                        System.out.println("Customer not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            case 4:
                System.out.print("Enter customer ID: ");
                input = scanner.nextInt();

                query = "{CALL findContactInfo(?, ?)}";
                try (CallableStatement storedProcedure = conn.prepareCall(query)) {

                    storedProcedure.setInt(1, input);
                    storedProcedure.registerOutParameter(2, Types.VARCHAR);

                    storedProcedure.execute();

                    String contactInfo = storedProcedure.getString(2);

                    if (contactInfo != null) {
                        System.out.println("Customer's Contact Info: " + contactInfo);
                    } else {
                        System.out.println("Customer not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case 5:
                System.out.print("Enter customer ID: ");
                input = scanner.nextInt();

                query = "{CALL findAll(?, ?, ?, ?)}";
                try (CallableStatement storedProcedure = conn.prepareCall(query)) {

                    storedProcedure.setInt(1, input);
                    storedProcedure.registerOutParameter(2, Types.VARCHAR);
                    storedProcedure.registerOutParameter(3, Types.VARCHAR);
                    storedProcedure.registerOutParameter(4, Types.VARCHAR);

                    storedProcedure.execute();

                    String customerName = storedProcedure.getString(2);
                    String contactInfo = storedProcedure.getString(3);
                    String billingDetails = storedProcedure.getString(4);

                    if (contactInfo != null) {
                        System.out.println("Customer Name: " + customerName);
                        System.out.println("Customer's Contact Info: " + contactInfo);
                        System.out.println("Customer's Billing Info: " + billingDetails);
                    } else {
                        System.out.println("Customer not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            default:
                System.out.println("Not an option. Try again.");
        }
    }
}
