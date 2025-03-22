package Test;

import bdd.ClientManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClientManagerTest {

    private ClientManager clientManager;
    private int testClientId = -1;

    @BeforeEach
    void setUp() {
        clientManager = new ClientManager();
        System.out.println("\n=== Début du test ClientManager ===");
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Nettoyage : supprimer le client de test s'il existe
        if (testClientId > 0) {
            clientManager.removeClient(testClientId);
        }
        System.out.println("=== Fin du test ClientManager ===\n");
    }

    @Test
    public void testAddGetRemoveClient() throws SQLException {
        try {
            // Ajouter un client
            System.out.println("1. Ajout d'un client: test test, 0612345678, test@gmail.com");
            clientManager.addClient("test", "test", "0612345678", "test", "test@gmail.com", LocalDate.now());

            // Récupérer la liste des clients après insertion
            System.out.println("2. Récupération des clients...");
            ResultSet rs = clientManager.getClients();
            assertNotNull(rs, "Le ResultSet ne doit pas être null");
            
            // Vérifier qu'il y a au moins un client
            assertTrue(rs.next(), "La liste des clients ne doit pas être vide après insertion");
            
            // Récupérer l'ID du client inséré
            testClientId = rs.getInt("id");
            System.out.println("3. Client créé avec ID: " + testClientId);
            assertTrue(testClientId > 0, "L'ID du client inséré doit être valide (supérieur à 0)");

            // Vérifier les données du client
            assertEquals("test", rs.getString("firstname"), "Le prénom doit correspondre");
            assertEquals("test", rs.getString("name"), "Le nom doit correspondre");
            assertEquals("0612345678", rs.getString("phone"), "Le téléphone doit correspondre");
            assertEquals("test", rs.getString("adresse"), "L'adresse doit correspondre");
            assertEquals("test@gmail.com", rs.getString("mail"), "L'email doit correspondre");

            // Supprimer le client ajouté
            System.out.println("4. Suppression du client avec ID=" + testClientId);
            clientManager.removeClient(testClientId);

            // Vérifier que le client a bien été supprimé
            ResultSet rsAfterDeletion = clientManager.getClients();
            boolean clientStillExists = false;
            while (rsAfterDeletion.next()) {
                if (rsAfterDeletion.getInt("id") == testClientId) {
                    clientStillExists = true;
                    break;
                }
            }
            assertFalse(clientStillExists, "Le client ne doit plus être présent après suppression");
            System.out.println("5. Test terminé avec succès !");

        } catch (SQLException e) {
            System.err.println("Erreur SQL pendant le test: " + e.getMessage());
            throw e;
        } catch (AssertionError e) {
            System.err.println("Échec de l'assertion: " + e.getMessage());
            throw e;
        }
    }
}
