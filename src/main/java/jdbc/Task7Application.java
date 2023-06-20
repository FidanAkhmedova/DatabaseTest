package jdbc;

import java.sql.*;
import java.util.Scanner;

public class Task7Application {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("1.Menu\n2.Exit");
        int choice = scanner.nextInt();

        if(choice == 1){

            System.out.println("Menu: ");
            String url = "jdbc:mysql://localhost:3306/test_schema";
            String user = "root";
            String password = "0506426773@Fn";

            try {
                //
                String query = "Select * from order_menu";
                Connection connection = DriverManager.getConnection(url, user, password);
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                getResult(statement, query);

                //get
                String query1 = "SELECT COUNT(*) FROM client_order";
                if (getCountOfRecords(statement, query1) == 0){
                    System.out.println("There is no order, yet");
                }

                //insert
                setResult1(statement);
                statement.close();
                //connection.close();
            }
            catch (SQLException e) {
               e.printStackTrace();
            }
        }
        else if(choice == 2){
            System.out.println("Exit system ");
        }
        else System.out.println("There is no such choice");
    }

    public static void getResult(Statement statement, String query) throws SQLException {
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next() ){
            int id = resultSet.getInt("menu_id");
            String name = resultSet.getString("menu_name");
            int price = resultSet.getInt("menu_price");
            System.out.println(id + " " + name + " - " + price);
        }
    }


    public static void setResult(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO client_order(order_id, order_name, order_price, order_count) VALUES (?,?,?,?)");
        //("insert into data(username,password,name,email,country,age,sex) values(?,?,?,?,?,?,?)");

        Scanner scanner = new Scanner(System.in);
        System.out.println("input order id");
        int id = scanner.nextInt();
        System.out.println("input order name");
        String name = scanner.next();
        System.out.println("input order price");
        int price = scanner.nextInt();
        System.out.println("input order count");
        int count = scanner.nextInt();
        /*String query2 = "INSERT INTO client_order (order_id, order_name, order_price, order_count)"
                +"VALUES (?,?,?,?)";*/
        preparedStatement.setInt(1,id);
        preparedStatement.setString(2,name);
        preparedStatement.setInt(3,price);
        preparedStatement.setInt(4,count);
        int i = preparedStatement.executeUpdate();
        if(i!=0){
            System.out.println("added");
        }
        else{
            System.out.println("failed to add");
        }
        //preparedStatement.setInt(4,1);
        /*int j = preparedStatement.executeUpdate(query2);
        System.out.println("j " + j);*/
        connection.close();
    }
    public static void setResult1(Statement statement) throws SQLException {
        Scanner scanner = new Scanner(System.in);


        System.out.println("input order id");
        int id = scanner.nextInt();

        System.out.println("input order name");
        String name = scanner.next();

        System.out.println("input order price");
        int price = scanner.nextInt();

        System.out.println("input order count");
        int count = scanner.nextInt();

        System.out.println(id + " " + name + " - " + price + " " + count);
        String query = "INSERT INTO client_order (order_id, order_name, order_price, order_count)"
                +" VALUES (1,'tiramisu', 3, 2)";
                //+" VALUES("+id+","+'name'+","+price+","+count+")";
        statement.executeUpdate(query);

    }
        public static int getCountOfRecords(Statement statement, String query) throws SQLException {

        int count = 0;
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            count = resultSet.getInt("COUNT(*)");
            System.out.println("count of records " + count);count++;
        }
        return count;
        }
}
