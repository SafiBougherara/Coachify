package com.example.Coachify;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Coachify extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Charger la vue initiale (logger.fxml)
        FXMLLoader fxmlLoader = new FXMLLoader(Coachify.class.getResource("logger.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Coachify");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
