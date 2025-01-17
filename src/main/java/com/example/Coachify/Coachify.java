package com.example.Coachify;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class Coachify extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Charger la vue initiale (logger.fxml)
        FXMLLoader fxmlLoader = new FXMLLoader(Coachify.class.getResource("logger.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 500);

        // Définir l'icône personnalisée
        String iconPath = "/assets/my_icon.ico"; // Chemin relatif à partir de resources
        InputStream iconStream = Coachify.class.getResourceAsStream(iconPath);
        if (iconStream != null) {
            stage.getIcons().add(new Image(iconStream));
        } else {
            System.err.println("L'icône personnalisée n'a pas été trouvée : " + iconPath);
        }

        // Configurer la fenêtre
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
