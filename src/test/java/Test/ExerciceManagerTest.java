package Test;

import bdd.ExerciceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciceManagerTest {

    private ExerciceManager exerciceManager;
    private int testExerciceId = -1;
    private final String TEST_NAME = "Test_Exercice_" + System.currentTimeMillis();
    private final double TEST_TIME = 30.0;
    private final int TEST_REPETITIONS = 20;

    @BeforeEach
    void setUp() {
        exerciceManager = new ExerciceManager();
        System.out.println("\n=== Début du test ExerciceManager ===");
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Nettoyage : supprimer l'exercice de test s'il existe
        if (testExerciceId > 0) {
            exerciceManager.removeExercice(testExerciceId);
        }
        System.out.println("=== Fin du test ExerciceManager ===\n");
    }

    @Test
    public void testAddGetRemoveExercice() throws SQLException {
        try {
            // Ajouter un exercice avec un nom unique
            System.out.println("1. Ajout d'un exercice: " + TEST_NAME + ", " + TEST_TIME + "min, " + TEST_REPETITIONS + " répétitions");
            exerciceManager.addExercice(TEST_NAME, TEST_TIME, TEST_REPETITIONS);

            // Récupérer la liste des exercices après insertion
            System.out.println("2. Récupération des exercices");
            ResultSet rs = exerciceManager.getExercices();
            assertNotNull(rs, "Le ResultSet ne doit pas être null");
            
            // Trouver notre exercice de test
            System.out.println("3. Recherche de l'exercice de test");
            boolean found = false;
            while (rs.next() && !found) {
                if (TEST_NAME.equals(rs.getString("name"))) {
                    found = true;
                    testExerciceId = rs.getInt("id");
                    
                    // Vérifier les données de l'exercice
                    System.out.println("4. Vérification des données de l'exercice");
                    assertTrue(testExerciceId > 0, "L'ID de l'exercice inséré doit être valide (supérieur à 0)");
                    assertEquals(TEST_NAME, rs.getString("name"), "Le nom de l'exercice doit correspondre");
                    assertEquals(TEST_TIME, rs.getDouble("time"), 0.01, "Le temps de l'exercice doit correspondre");
                    assertEquals(TEST_REPETITIONS, rs.getInt("repetitions"), "Le nombre de répétitions doit correspondre");
                }
            }
            assertTrue(found, "L'exercice de test doit être trouvé dans la base de données");

            // Supprimer l'exercice ajouté
            System.out.println("5. Suppression de l'exercice avec ID=" + testExerciceId);
            exerciceManager.removeExercice(testExerciceId);

            // Vérifier que l'exercice a bien été supprimé
            System.out.println("6. Vérification de la suppression");
            ResultSet rsAfterDeletion = exerciceManager.getExercices();
            boolean exerciceStillExists = false;
            while (rsAfterDeletion.next()) {
                if (rsAfterDeletion.getInt("id") == testExerciceId) {
                    exerciceStillExists = true;
                    break;
                }
            }
            assertFalse(exerciceStillExists, "L'exercice ne doit plus être présent après suppression");

            System.out.println("7. Test terminé avec succès !");

        } catch (SQLException e) {
            System.err.println("Erreur SQL pendant le test: " + e.getMessage());
            throw e;
        } catch (AssertionError e) {
            System.err.println("Échec de l'assertion: " + e.getMessage());
            throw e;
        }
    }
}
