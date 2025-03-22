package Test;

import bdd.LoginManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginManagerTest {

    private LoginManager loginManager;
    private String testUsername = "testuser";
    private String testPassword = "testpass123";
    private int testUserId = -1;

    @BeforeEach
    void setUp() {
        loginManager = new LoginManager();
        System.out.println("\n=== Début du test LoginManager ===");
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Nettoyage : supprimer l'utilisateur de test s'il existe
        if (testUserId > 0) {
            loginManager.removeUser(testUserId);
        }
        System.out.println("=== Fin du test LoginManager ===\n");
    }

    @Test
    public void testAddLoginRemoveUser() throws SQLException {
        try {
            // Compter les utilisateurs avant l'ajout
            System.out.println("1. Comptage initial des utilisateurs");
            int initialCount = loginManager.getUserCount();

            // Ajouter un utilisateur
            System.out.println("2. Ajout d'un utilisateur: " + testUsername);
            loginManager.addUser(testUsername, testPassword);

            // Vérifier que le nombre d'utilisateurs a augmenté
            System.out.println("3. Vérification du nombre d'utilisateurs");
            assertEquals(initialCount + 1, loginManager.getUserCount(),
                    "Le nombre d'utilisateurs doit avoir augmenté de 1");

            // Tester le login avec les bonnes informations
            System.out.println("4. Test de connexion avec les bonnes informations");
            assertTrue(loginManager.login(testUsername, testPassword),
                    "Le login doit réussir avec les bonnes informations");

            // Tester le login avec un mauvais mot de passe
            System.out.println("5. Test de connexion avec un mauvais mot de passe");
            assertFalse(loginManager.login(testUsername, "wrongpassword"),
                    "Le login doit échouer avec un mauvais mot de passe");

            // Récupérer l'ID de l'utilisateur
            System.out.println("6. Récupération de l'ID de l'utilisateur");
            ResultSet rs = loginManager.getAllUsers();
            while (rs.next()) {
                if (testUsername.equals(rs.getString("username"))) {
                    testUserId = rs.getInt("id");
                    break;
                }
            }
            assertTrue(testUserId > 0, "L'ID de l'utilisateur doit être valide");

            // Supprimer l'utilisateur
            System.out.println("7. Suppression de l'utilisateur avec ID=" + testUserId);
            loginManager.removeUser(testUserId);

            // Vérifier que le nombre d'utilisateurs est revenu à l'initial
            System.out.println("8. Vérification du nombre d'utilisateurs après suppression");
            assertEquals(initialCount, loginManager.getUserCount(),
                    "Le nombre d'utilisateurs doit être revenu à sa valeur initiale");

            // Vérifier que le login ne fonctionne plus
            System.out.println("9. Vérification que le login ne fonctionne plus");
            assertFalse(loginManager.login(testUsername, testPassword),
                    "Le login doit échouer après la suppression de l'utilisateur");

            System.out.println("10. Test terminé avec succès !");

        } catch (SQLException e) {
            System.err.println("Erreur SQL pendant le test: " + e.getMessage());
            throw e;
        } catch (AssertionError e) {
            System.err.println("Échec de l'assertion: " + e.getMessage());
            throw e;
        }
    }
}
