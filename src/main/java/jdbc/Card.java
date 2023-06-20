package jdbc;

import java.sql.*;

public class Card {
    public final static String url = "jdbc:mysql://localhost:3306/test_schema";
    public final static String user = "root";
    public final static String password = "0506426773@Fn";
    public int cardNumber, balance, pin;
    public long clientId;

    public Card(int cardNumber, int balance, int pin, long clientId) {
       this.cardNumber = cardNumber;
       this.balance = balance;
       this.pin = pin;
       this.clientId = clientId;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public int getBalance() {
        return balance;
    }

    public int getPin() {
        return pin;
    }

    public long getClientId() {
        return clientId;
    }

    public long insertCard(Connection conn, Card card, long id) {
        String SQL = "insert into cards (card_number, card_balance, card_pin, client_id) "
                + "VALUES(?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, card.getCardNumber());
            pstmt.setInt(2, card.getBalance());
            pstmt.setInt(3, card.getPin());
            pstmt.setLong(4, card.getClientId());

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

}
