package Test;

import bdd.ExerciceManager;
import bdd.ExoProgManager;
import bdd.ProgramManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ExoProgManagerTest {

    private ExoProgManager exoProgManager;
    private ExerciceManager exerciceManager;
    private ProgramManager programManager;
    private int testExerciceId = -1;
    private int testProgramId = -1;
    private final int TEST_CLIENT_ID = 1; // Assurez-vous que ce client existe dans la base de données
    private final int TEST_PROGRAM_NUMBER = 999;

    @BeforeEach
    void setUp() {
        exoProgManager = new ExoProgManager();
        exerciceManager = new ExerciceManager();
        programManager = new ProgramManager();
        System.out.println("\n=== Début du test ExoProgManager ===");
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Nettoyage : supprimer l'exercice et le programme de test s'ils existent
        if (testExerciceId > 0) {
            exerciceManager.removeExercice(testExerciceId);
        }
        if (testProgramId > 0) {
            programManager.removeProgram(testProgramId);
        }
        System.out.println("=== Fin du test ExoProgManager ===\n");
    }

    @Test
    public void testAddGetRemoveExoProg() throws SQLException {
        try {
            // Créer un exercice pour le test
            System.out.println("1. Création d'un exercice de test");
            exerciceManager.addExercice("Test Exercise", 15.0, 10);
            ResultSet rsExercice = exerciceManager.getExercices();
            assertTrue(rsExercice.next(), "L'exercice doit être créé");
            testExerciceId = rsExercice.getInt("id");
            assertTrue(testExerciceId > 0, "L'ID de l'exercice doit être valide");

            // Créer un programme pour le test
            System.out.println("2. Création d'un programme de test");
            programManager.addProgram(TEST_PROGRAM_NUMBER, false, 30.0, TEST_CLIENT_ID);
            testProgramId = programManager.getProgramId(TEST_PROGRAM_NUMBER, TEST_CLIENT_ID);
            assertTrue(testProgramId > 0, "Le programme doit être créé");

            // Ajouter le lien exo_prog
            System.out.println("3. Création du lien exo_prog");
            exoProgManager.addExoProg(testExerciceId, testProgramId);

            // Vérifier que l'exercice est bien lié au programme
            System.out.println("4. Vérification du lien exo_prog");
            ResultSet rsExoProg = exoProgManager.getExoProg(testProgramId);
            assertNotNull(rsExoProg, "Le ResultSet ne doit pas être null");
            assertTrue(rsExoProg.next(), "Il doit y avoir au moins un exercice lié");
            
            // Vérifier les propriétés de l'exercice lié
            System.out.println("5. Vérification des propriétés de l'exercice lié");
            assertEquals(testExerciceId, rsExoProg.getInt("id"), "L'ID de l'exercice doit correspondre");
            assertEquals("Test Exercise", rsExoProg.getString("name"), "Le nom de l'exercice doit correspondre");
            assertEquals(15.0, rsExoProg.getDouble("time"), 0.01, "Le temps de l'exercice doit correspondre");
            assertEquals(10, rsExoProg.getInt("repetitions"), "Le nombre de répétitions doit correspondre");

            // Supprimer le lien exo_prog
            System.out.println("6. Suppression du lien exo_prog");
            exoProgManager.removeExoProg(testProgramId);

            // Vérifier que le lien n'existe plus
            System.out.println("7. Vérification de la suppression du lien");
            ResultSet rsAfterDeletion = exoProgManager.getExoProg(testProgramId);
            assertFalse(rsAfterDeletion.next(), "Il ne doit plus y avoir d'exercices liés au programme");

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
