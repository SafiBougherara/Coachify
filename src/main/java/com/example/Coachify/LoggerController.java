package com.example.Coachify;

import bdd.LoginManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;


public class LoggerController {


    @FXML
    private TextField user;
    @FXML
    private TextField pass;




    @FXML
    private void switchToMyView(ActionEvent event) throws IOException {

        if (!checkconnection()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Wrong username or password");
            alert.showAndWait();
        }else {
            // Récupérer le Stage à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Charger le fichier myview.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("myview.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            // Appliquer la nouvelle scène au Stage
            stage.setScene(scene);
        }
    }

    public boolean checkconnection(){
        String user = this.user.getText();
        String pass = this.pass.getText();

        LoginManager loginManager = new LoginManager();
        return loginManager.login(user, pass);

        // dans le cas où plus aucun utilisateur dans la bdd

//      return true;
    }
}
