package jdbc;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Client {

    public String name;
    public String surname;

    public Client(){}
    public Client(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public int age;

    public static void main(String[] args) {


    }


    public long insertClient(Connection conn, Client client) {
        String SQL = "insert into clients (client_name, client_surname, client_age) "
                + "VALUES(?,?,?)";

        long id = 0;

        try (
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, client.getName());
                pstmt.setString(2, client.getSurname());
                pstmt.setInt(3, client.getAge());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    public static void getProductByName(Connection conn, String name1, int count, int cardNumber, int pin) throws SQLException {
        String sql = "SELECT * FROM products WHERE product_name = '"+name1+"'";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next() ){
            int id = resultSet.getInt("product_id");
            String name = resultSet.getString("product_name");
            int price = resultSet.getInt("product_price");
            System.out.println(id + " " + name + " - " + price);
            int totalAmount = price * count;
            System.out.println("TotalAmount: " + totalAmount);
            checkBalance(conn, totalAmount, cardNumber, pin, id);
        }

    }
   public static void checkBalance(Connection conn, int totalAmount, int cardNumber, int pin, int productId) throws SQLException {

           String sql = "SELECT * FROM cards WHERE card_pin = '"+pin+"'";
           Statement statement = conn.createStatement();
           ResultSet resultSet = statement.executeQuery(sql);
           int balans = 0;
       while (resultSet.next() ) {

           int id = resultSet.getInt("card_id");
           balans = resultSet.getInt("card_balance");
           int client_id = resultSet.getInt("client_id");
           System.out.println("id " + id + "\nbalans " + balans + "\nclient_id " + client_id);
           int remainingAmount = balans - totalAmount;
           if (remainingAmount >= 0) {

               System.out.println("success");
               String sql1 = "UPDATE cards SET card_balance = '"+remainingAmount+"'WHERE client_id = '"+client_id+"'";
               Statement statement1 = conn.createStatement();
               statement1.execute(sql1);
                insertOrder(conn, productId, id);
               getOrderByUser(conn, client_id);
           }
           else System.out.println("invalid balans");
       }
   }

   public static void insertOrder(Connection conn, int productId, int cardId ){
       String SQL = "insert into orders (product_id, card_id) "
               + "VALUES(?,?)";

       long id = 0;

       try (
               PreparedStatement pstmt = conn.prepareStatement(SQL,
                       Statement.RETURN_GENERATED_KEYS)) {

           //pstmt.setInt(1, 1);
           pstmt.setInt(1, productId);
           pstmt.setInt(2, cardId);

           int affectedRows = pstmt.executeUpdate();
           // check the affected rows
           if (affectedRows > 0) {
               // get the ID back
               try (ResultSet rs = pstmt.getGeneratedKeys()) {
                   if (rs.next()) {
                       id = rs.getLong(1);
                   }
               } catch (SQLException ex) {
                   System.out.println(ex.getMessage());
               }
           }
       } catch (SQLException ex) {
           System.out.println(ex.getMessage());
       }
    }

    public static void getOrderByUser(Connection conn,int clientId ) throws SQLException {
        //String sql = "select * from orders o where o.card_id in (select card_id from cards c where c.client_id = '"+clientId+"')";
        String sql = "select p.product_name from products p where p.product_id in (select o.product_id from orders o where o.card_id in (select card_id from cards c where c.client_id = '"+clientId+"'))";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next() ){
            /*int id = resultSet.getInt("order_id");
            String product_id = resultSet.getString("product_id");*/
            String product_name = resultSet.getString("product_name");
            System.out.println("prroduct name - " + product_name);
            //System.out.println(id + " " + product_id + " - " + card_id);
        }
    }
}
