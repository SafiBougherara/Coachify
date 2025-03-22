package Test;

import bdd.ProgramManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramManagerTest {

    private ProgramManager programManager;
    private int testProgramId = -1;
    private final int TEST_CLIENT_ID = 1; // Assurez-vous que ce client existe dans la base de données
    private final int TEST_PROGRAM_NUMBER = 999;

    @BeforeEach
    void setUp() {
        programManager = new ProgramManager();
        System.out.println("\n=== Début du test ProgramManager ===");
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Nettoyage : supprimer le programme de test s'il existe
        if (testProgramId > 0) {
            programManager.removeProgram(testProgramId);
        }
        System.out.println("=== Fin du test ProgramManager ===\n");
    }

    @Test
    public void testAddGetRemoveProgram() throws SQLException {
        try {
            // Ajouter un programme
            System.out.println("1. Ajout d'un programme: #" + TEST_PROGRAM_NUMBER + " pour le client " + TEST_CLIENT_ID);
            programManager.addProgram(TEST_PROGRAM_NUMBER, false, 60.0, TEST_CLIENT_ID);

            // Récupérer l'ID du programme
            System.out.println("2. Récupération de l'ID du programme");
            testProgramId = programManager.getProgramId(TEST_PROGRAM_NUMBER, TEST_CLIENT_ID);
            assertTrue(testProgramId > 0, "L'ID du programme doit être valide");

            // Récupérer le programme et vérifier ses détails
            System.out.println("3. Vérification des détails du programme");
            ResultSet programDetails = programManager.getProgramDetails(testProgramId);
            assertNotNull(programDetails, "Les détails du programme ne doivent pas être null");
            assertTrue(programDetails.next(), "Les détails du programme doivent être présents");
            
            // Vérifier le statut du programme
            assertFalse(programDetails.getBoolean("status"), "Le statut initial du programme doit être false");

            // Récupérer le programme via getOneProgram
            System.out.println("4. Récupération du programme via getOneProgram");
            ResultSet oneProgram = programManager.getOneProgram(TEST_CLIENT_ID);
            assertNotNull(oneProgram, "Le programme récupéré ne doit pas être null");
            assertTrue(oneProgram.next(), "Le programme doit être présent dans le résultat");
            
            // Vérifier toutes les propriétés du programme
            System.out.println("5. Vérification des propriétés du programme");
            assertEquals(testProgramId, oneProgram.getInt("id"), "L'ID du programme doit correspondre");
            assertEquals(TEST_PROGRAM_NUMBER, oneProgram.getInt("num_program"), "Le numéro du programme doit correspondre");
            assertEquals(TEST_CLIENT_ID, oneProgram.getInt("client_id"), "L'ID du client doit correspondre");
            assertEquals(60.0, oneProgram.getDouble("time"), 0.01, "Le temps du programme doit correspondre");
            assertFalse(oneProgram.getBoolean("status"), "Le statut du programme doit être false");

            // Supprimer le programme
            System.out.println("6. Suppression du programme avec ID=" + testProgramId);
            programManager.removeProgram(testProgramId);

            // Vérifier que le programme n'existe plus
            System.out.println("7. Vérification de la suppression");
            ResultSet afterDeletion = programManager.getOneProgram(TEST_CLIENT_ID);
            boolean programStillExists = false;
            while (afterDeletion.next()) {
                if (afterDeletion.getInt("id") == testProgramId) {
                    programStillExists = true;
                    break;
                }
            }
            assertFalse(programStillExists, "Le programme ne doit plus être présent après suppression");

            System.out.println("8. Test terminé avec succès !");

        } catch (SQLException e) {
            System.err.println("Erreur SQL pendant le test: " + e.getMessage());
            throw e;
        } catch (AssertionError e) {
            System.err.println("Échec de l'assertion: " + e.getMessage());
            throw e;
        }
    }
}
