package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

public class Task8Application {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.New Card\n2.Start shopping\n3.Exit");
        int input = scanner.nextInt();

        if (input == 3) return;
        else if(input == 1){
            String url = "jdbc:mysql://localhost:3306/test_schema";
            String user = "root";
            String password = "0506426773@Fn";

            Connection connection = null;
            try {
                connection = DriverManager.getConnection(url, user, password);
                //connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                setClient(statement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //getResult(statement, query);




        }
    }
    public static void setClient(Statement statement) throws SQLException {
        System.out.println("Please enter pin (4 digits)");
        Scanner scanner = new Scanner(System.in);
        int pin = scanner.nextInt();
        System.out.println("Confirm pin ");
        int confirmPin = scanner.nextInt();

        if (pin == confirmPin){
            System.out.println("Enter client name: ");
            String name = scanner.next();
            System.out.println("Enter client surname: ");
            String surname = scanner.next();
            System.out.println("Enter client age: ");
            int age = scanner.nextInt();
            int id = 3;
            String query = "insert into clients (client_id, client_name, client_surname, client_age)"+
            //"VALUES ('" +name+ "', '" +surname+ "', '" +age+ "')";
            "VALUES ('" +id+ "', '"+name+ "', '" +surname+ "', '" +age+ "')";
            var res = statement.executeUpdate(query);
            System.out.println(res + " lines changed");
            int balance = scanner.nextInt();
            setCard(statement, pin, balance);
        }
    }

    public static void setCard(Statement statement, int pin, int balance){
        Random random = new Random();
        int cardNumber = random.nextInt(1000,9999);
        System.out.println("Your card number is " + cardNumber);
    }
}
