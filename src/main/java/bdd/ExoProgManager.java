package bdd;

import java.sql.*;

public class ExoProgManager {

    //création d'un lien exo_prog quand un programme est créé
    public boolean addExoProg(int idExo, int idProg) {
        BddManager bddManager = new BddManager();
        Connection Connection = bddManager.connection();
        String sql_request = "INSERT INTO exo_prog (id_exo, id_prog) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = Connection.prepareStatement(sql_request);
            pstmt.setInt(1, idExo);
            pstmt.setInt(2, idProg);
            return pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //suppression d'un lien exo_prog quand le programme est supprimé
    public void removeExoProg(int progId) {
        BddManager bddManager = new BddManager();
        Connection Connection = bddManager.connection();
        String sql_request = "DELETE FROM exo_prog WHERE id_prog = ?";
        try {
            PreparedStatement pstmt = Connection.prepareStatement(sql_request);
            pstmt.setInt(1, progId);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /*
    Recherche de tous les liens exo_prog d'un programme et récupère par jointure
    avec l'exo_id le contenu des exercices dans la table exercices.
    */
    public ResultSet getExoProg(int progId) {
        BddManager bdd = new BddManager();
        Connection connection = bdd.connection();
        ResultSet rs = null;
        String sql_request = "SELECT e.* FROM exo_prog ep " +
                             "INNER JOIN exercices e ON ep.exo_id = e.id " +
                             "WHERE ep.prog_id = ?;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql_request);
            pstmt.setInt(1, progId);
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
