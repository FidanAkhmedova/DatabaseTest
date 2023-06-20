package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

public class Test {
    public final static String url = "jdbc:mysql://localhost:3306/test_schema";
    public final static String user = "root";
    public final static String password = "0506426773@Fn";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.New Card\n2.Start shopping\n3.Exit");
        int input = scanner.nextInt();

        if (input == 3) return;
        else if(input == 1){



        }
    }
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}
