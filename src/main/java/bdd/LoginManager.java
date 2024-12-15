package bdd;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class LoginManager {

    // Méthode de login modifiée pour vérifier le mot de passe avec hachage
    public boolean login(String username, String password) {
        BddManager bddManager = new BddManager();
        Connection connection = bddManager.connection();
        ResultSet rs = null;
        String sql_request = "SELECT password FROM users WHERE username = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql_request);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");

                // Vérification du mot de passe avec BCrypt
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    return true; // Mot de passe correct
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false; // Mot de passe incorrect ou utilisateur non trouvé
    }

    // Méthode d'ajout d'un utilisateur avec hachage du mot de passe
    public void addUser(String username, String password) {
        BddManager bddManager = new BddManager();
        Connection connection = bddManager.connection();

        // Hachage du mot de passe
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String sql_request = "INSERT INTO users (username, password) VALUES (?, ?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql_request);
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword); // Utilisation du mot de passe haché
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
