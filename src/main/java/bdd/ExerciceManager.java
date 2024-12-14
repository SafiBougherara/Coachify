package bdd;

import java.sql.*;

public class ExerciceManager {

    public boolean addExercice(String name, double time, int repExercice) {
        BddManager bddManager = new BddManager();
        Connection Connection = bddManager.connection();
        String sql_request = "INSERT INTO exercices (name, time, répétitions) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = Connection.prepareStatement(sql_request);
            pstmt.setString(1, name);
            pstmt.setDouble(2, time);
            pstmt.setInt(3, repExercice);
            return pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getExercices(){
        BddManager bdd = new BddManager();
        Connection connection = bdd.connection();
        ResultSet rs = null;
        String sql_request = "SELECT * FROM exercices";
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sql_request);
            //System.out.println(rs);
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeExercice(int id) {
        BddManager bdd = new BddManager();
        Connection connection = bdd.connection();
        String sql_request = "DELETE FROM exercices WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql_request);
            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
