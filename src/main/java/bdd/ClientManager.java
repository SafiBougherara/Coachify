package bdd;

import java.sql.*;
import java.time.LocalDate;

public class ClientManager {

    public void addClient(String firstname, String name, String phone, String adresse, String email, LocalDate birth_date) {
        BddManager bddManager = new BddManager();
        Connection Connection = bddManager.connection();
        String sql_request = "INSERT INTO clients (firstname, name, phone, adresse, mail, birth_date) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = Connection.prepareStatement(sql_request);
            pstmt.setString(1, firstname);
            pstmt.setString(2, name);
            pstmt.setString(3, phone);
            pstmt.setString(4, adresse);
            pstmt.setString(5, email);
            pstmt.setDate(6, Date.valueOf(birth_date));

            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public ResultSet getClients(){
        BddManager bdd = new BddManager();
        Connection connection = bdd.connection();
        ResultSet rs = null;
        String sql_request = "SELECT * FROM clients";
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sql_request);
            //System.out.println(rs);
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeClient(int id) {
        BddManager bdd = new BddManager();
        Connection connection = bdd.connection();
        String sql_request = "DELETE FROM clients WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql_request);
            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
