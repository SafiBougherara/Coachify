package bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgramManager {

    public void addProgram(int num_program, boolean status, double time, int client_id) {
        BddManager bm = new BddManager();
        Connection Connection = bm.connection();
        String sql_request = "INSERT INTO programs (num_program, status, time, client_id) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = Connection.prepareStatement(sql_request);
            pstmt.setInt(1, num_program);
            pstmt.setBoolean(2, status);
            pstmt.setDouble(3, time);
            pstmt.setInt(4, client_id);
            pstmt.execute();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeProgram(int id) {
        BddManager bm = new BddManager();
        Connection Connection = bm.connection();
        String sql_request = "DELETE FROM programs WHERE id = ?";
        try {
            PreparedStatement pstmt = Connection.prepareStatement(sql_request);
            pstmt.setInt(1, id);
            pstmt.execute();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getOneProgram(int client_id) {
        BddManager bm = new BddManager();
        Connection Connection = bm.connection();
        String sql_request = "SELECT * FROM programs WHERE client_id = ?";

        try {
            PreparedStatement pstmt = Connection.prepareStatement(sql_request);
            pstmt.setInt(1, client_id);
            ResultSet rs = pstmt.executeQuery();
            return rs;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getProgramId(int num_program, int client_id) {
        BddManager bm = new BddManager();
        Connection Connection = bm.connection();
        String sql_request = "SELECT id FROM programs WHERE num_program = ? AND client_id = ?";
        try {
            PreparedStatement pstmt = Connection.prepareStatement(sql_request);
            pstmt.setInt(1, num_program);
            pstmt.setInt(2, client_id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("id");
                return id;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /*
    * getProgramDetails permet de recuperer le status du programme ('status') dans ma table programs
    * puis, par jointure sur la table exo_prog, de recuperer tous les id d'exercices (exo_id) par rapport
    *  à l'id du programme (prog_id), et enfin, de recuperer via l'id des exercices,les contenus des colonnes
    * 'name', 'time' et 'repetitions' dans la table exercices
    *
    * */
    public ResultSet getProgramDetails(int id) {
        BddManager bm = new BddManager();
        Connection connection = bm.connection();
        ResultSet rs = null;

        String sql_request = "SELECT p.status, e.id AS exo_id, e.name, e.time, e.repetitions " +
                "FROM programs p " +
                "LEFT JOIN exo_prog ep ON p.id = ep.prog_id " +
                "LEFT JOIN exercices e ON ep.exo_id = e.id " +
                "WHERE p.id = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql_request);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            //System.out.println(rs);
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
