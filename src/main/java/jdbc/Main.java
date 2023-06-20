package jdbc;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public final static String url = "jdbc:mysql://localhost:3306/test_schema";
    public final static String user = "root";
    public final static String password = "0506426773@Fn";
    public static Connection conn;

    static {
        try {
            conn = connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.New Card\n2.Start shopping\n3.Exit");
        int input = scanner.nextInt();
        if (input == 1) {
            System.out.println("Please set pin (4 digits)");
            int pin = scanner.nextInt();
            System.out.println("Confirm pin ");
            int confirmPin = scanner.nextInt();

            if (pin == confirmPin) {
                System.out.println("Enter client name: ");
                String name = scanner.next();
                System.out.println("Enter client surname: ");
                String surname = scanner.next();
                System.out.println("Enter client age: ");
                int age = scanner.nextInt();

                Client client = new Client(name, surname, age);
                long id = client.insertClient(conn, client);
                System.out.println("id " + id);

                System.out.println("Enter balans: ");
                int balance = scanner.nextInt();
                Random random = new Random();
                int cardNumber = random.nextInt(1000,9999);
                System.out.println("Your card number is " + cardNumber);

                Card card = new Card(cardNumber, balance, pin, id);
                card.insertCard(conn, card, id);
            }
        }
        else if(input == 2){
            System.out.println("1. Buy product\n2. Show bought products\n3. Exit");
            int productInput = scanner.nextInt();
            if (productInput == 1){
                System.out.println("Enter product name ");
                String productName = scanner.next();
                System.out.println("Enter product count ");
                int productCount = scanner.nextInt();
                System.out.println("Enter card number ");
                int cardNumber = scanner.nextInt();
                System.out.println("Enter pin ");
                int pin = scanner.nextInt();

                try {
                    Client.getProductByName(conn, productName, productCount, cardNumber, pin);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (productInput == 2) {

            }

        }
    }
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
