package com.example.Coachify;

import Models.Client;
import Models.Program;
import Models.User;
import bdd.LoginManager;
import bdd.ProgramManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import bdd.ExerciceManager;
import bdd.ClientManager;
import Models.Exercice;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class CoachifyController {


    //exercice
    @FXML
    private TextField nameExercice;
    @FXML
    private TextField timeExercice;

    @FXML
    private TextField repExercice;


    //client
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField phone;
    @FXML
    private TextArea adresse;
    @FXML
    private TextField email;
    @FXML
    private DatePicker birth_date;


    //info_entreprise
    @FXML
    private TextField phoneE;
    @FXML
    private TextArea adresseE;
    @FXML
    private TextField emailE;
    @FXML
    private ListView info_entreprise;


    //client_choice
    @FXML
    private ChoiceBox clientChoice;

    //exerciceChoice
    @FXML
    private ChoiceBox exerciceChoice;

    //utilisateur
    @FXML
    private TextField add_username;
    @FXML
    private TextField add_password;

    //lists
    @FXML
    private ListView exerciceList;
    @FXML
    private ListView temp_exerciceList;
    @FXML
    private ListView clientList;
    @FXML
    private TextField temp_sum;
    @FXML
    private ListView programList;
    @FXML
    private ListView info_usersList;

    @FXML
    private ListView<String> info_entrepriseList;


    ObservableList<Exercice> items = FXCollections.observableArrayList();
    ObservableList<Client> items2 = FXCollections.observableArrayList();
    ObservableList<Exercice> items3 = FXCollections.observableArrayList();

    ObservableList<Program> items4 = FXCollections.observableArrayList();

    ObservableList<User> items5 = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        //lier items à la listeView au démarrage
        exerciceList.setItems(items);
        clientList.setItems(items2);

        //vider les listes au demarrage
        temp_sum.setText("total : 0.0 minutes");


        //charger les exercices initiaux (optionnel)
        loadExercices();
        loadClients();

        //load_info_entreprise();
    }

    private boolean checkFieldsOnRecept() {
        if (clientChoice.getSelectionModel().isEmpty() && !exerciceChoice.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez selectionner un client");
            alert.showAndWait();
        }
        if (exerciceChoice.getSelectionModel().isEmpty() && !clientChoice.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez selectionner un exercice");
            alert.showAndWait();
        }
        if (clientChoice.getSelectionModel().isEmpty() && exerciceChoice.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez selectionner un client et un exercice");
            alert.showAndWait();
        }
        if (!clientChoice.getSelectionModel().isEmpty() && !exerciceChoice.getSelectionModel().isEmpty()) {
            return true;

        }else {
            return false;
        }

    }

    @FXML
    public void load_client_program(Event e){
        loadClients();
        loadExercices();
        programList.setItems(items4);
        clientChoice.setItems(items2);
        exerciceChoice.setItems(items);
    }

    @FXML
    public void add_exercice_on_recept(ActionEvent event){

        if (this.checkFieldsOnRecept()) {
            // on récupeère les valeurs de la choiceBox et on l'add dans item3
            Exercice exercice = (Exercice) exerciceChoice.getSelectionModel().getSelectedItem();
            items3.add(exercice);
            //tem.out.println(items3);

            // on l'add dans le listview temporaire
            temp_exerciceList.setItems(items3);
            Double time = items3.stream().mapToDouble(Exercice::getTime).sum();
            temp_sum.setText("total : " + time + " minutes");
        }


    }

    @FXML
    public void add_exercice(ActionEvent event){
        String exerciceName = this.nameExercice.getText();
        String timeInput = this.timeExercice.getText();
        String repExerciceInput = this.repExercice.getText();

        if (exerciceName.isEmpty() || timeInput.isEmpty() || repExerciceInput.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
        } else {
            try {
                double time = Double.parseDouble(timeInput);
                int repExercice = Integer.parseInt(repExerciceInput);
                ExerciceManager sm = new ExerciceManager();
                if(sm.addExercice(exerciceName, time, repExercice)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Exercice non-ajouté, probleme de base de données");
                    alert.showAndWait();
                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Exercice ajouté avec succes");
                    alert.showAndWait();
                }
                this.loadExercices();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez entrer un prix valide");
                alert.showAndWait();
            }
        }
    }

    private void loadExercices(){
        this.items.clear();

        try {
            ExerciceManager sm = new ExerciceManager();
            ResultSet rs = sm.getExercices();


            while (rs.next()) {
                String name = rs.getString("name");
                double time = rs.getDouble("time");
                int repExercice = rs.getInt("repetitions");
                int id = rs.getInt("id");
                Exercice exercice = new Exercice(name, time, id, repExercice);
                this.items.add(exercice);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    @FXML
    public void add_client(ActionEvent event) throws SQLException {
        String firstname = this.firstname.getText();
        String lastname = this.lastname.getText();
        String phone = this.phone.getText();
        String adresse = this.adresse.getText();
        String email = this.email.getText();
        LocalDate birthdate = this.birth_date.getValue();

        if (firstname.isEmpty() || lastname.isEmpty() || phone.isEmpty() || adresse.isEmpty() || email.isEmpty() || birthdate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs avec des informations valides");
            alert.showAndWait();
        }
        else if (!email.matches("^(.+)@(.+)$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une adresse email valide");
            alert.showAndWait();
        }
        else if (!phone.matches("^(\\+33|0)[1-9]([-. ]?[0-9]{2}){4}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer un numéro de téléphone valide");
            alert.showAndWait();
        }
        else if (birthdate.isAfter(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une date de naissance valide");
            alert.showAndWait();
        }
        else {
            ClientManager cm = new ClientManager();
            cm.addClient(firstname, lastname, phone, adresse, email, birthdate);
            this.loadClients();
        }
    }

    public void remove_selected_client(ActionEvent event){

        Client client = (Client) clientList.getSelectionModel().getSelectedItem();
        if (client != null) {
            System.out.println("numéro du client : " + client.getId());
            ClientManager cm = new ClientManager();
            cm.removeClient(client.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Client supprimé avec succes");
            alert.showAndWait();
            this.loadClients();
        }
    }
    public void remove_selected_exercice(ActionEvent event){

        Exercice exercice = (Exercice) exerciceList.getSelectionModel().getSelectedItem();
        if (exercice != null) {
            System.out.println("numéro de l'exercice : " + exercice.getId());
            ExerciceManager sm = new ExerciceManager();
            sm.removeExercice(exercice.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Exercice supprimé avec succes");
            alert.showAndWait();
            this.loadExercices();
        }
    }

    public void remove_selected_program(ActionEvent event){

        Program program = (Program) programList.getSelectionModel().getSelectedItem();
        if (program != null) {
            System.out.println("numéro du programme : " + program.getId());
            ProgramManager pm = new ProgramManager();
            pm.removeProgram(program.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Programme supprimé avec succes");
            alert.showAndWait();
            this.loadProgram();
        }
    }

    private void loadClients(){
        this.items2.clear();

        try {
            ClientManager cm = new ClientManager();
            ResultSet rs = cm.getClients();


            while (rs.next()) {
                String lastname = rs.getString("name");
                String firstname = rs.getString("firstname");
                int id = rs.getInt("id");
                String phone = rs.getString("phone");
                String adresse = rs.getString("adresse");
                String email = rs.getString("mail");
                LocalDate birthdate = rs.getDate("birth_date").toLocalDate();
                Client client = new Client(id, firstname, lastname, phone, adresse, email, birthdate);
                this.items2.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void loadProgram() {
        this.items4.clear();

        try {
            ProgramManager pm = new ProgramManager();
            Client client = (Client) clientChoice.getSelectionModel().getSelectedItem();
            int client_id = client.getId();
            ResultSet rs = pm.getOneProgram(client_id);
            while (rs.next()) {
                int num_program = rs.getInt("num_program");
                boolean status = rs.getBoolean("status");
                double time = rs.getDouble("time");
                int id = rs.getInt("id");
                Program program = new Program(id, num_program, status, time, client_id);
                this.items4.add(program);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void refresh_program_list(ActionEvent event) {
        if(clientChoice.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez choisir un Client");
            alert.showAndWait();
        }else {
            this.loadProgram();
        }
    }

    @FXML
    public void add_info_entreprise(ActionEvent event) {
        // Récupérer les valeurs des champs
        String phone = phoneE.getText();
        String adresse = adresseE.getText();
        String email = emailE.getText();

        // Nouvelle entrée à ajouter (sans infosSupplementaires)
        Map<String, Object> newEntry = new HashMap<>();
        newEntry.put("phone", phone);
        newEntry.put("adresse", adresse);
        newEntry.put("email", email);

        if (phone.isEmpty() || adresse.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
        } else if (!email.matches("^(.+)@(.+)$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une adresse email valide");
            alert.showAndWait();
        }else{

            // Spécifier le chemin du fichier JSON
            String filePath = "src/main/resources/json/informations_entreprise.json";

            try {
                // Créer un ObjectMapper pour gérer le JSON
                ObjectMapper objectMapper = new ObjectMapper();

                // Créer une liste contenant uniquement la nouvelle entrée
                List<Map<String, Object>> dataList = new ArrayList<>();
                dataList.add(newEntry);

                // Sauvegarder la nouvelle liste dans le fichier JSON, en remplaçant son contenu
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), dataList);


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Informations ajoutées avec succes");
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Impossible d'ajouter les informations");
                alert.showAndWait();
                e.printStackTrace();
            }

        }
    }

    @FXML
    public void clean_added_exercices(ActionEvent event) {
        this.items3.clear();
        temp_sum.setText("total : 0.0 minutes");
    }

    public void generate_program() {

        Double time = items3.stream().mapToDouble(Exercice::getTime).sum();
        System.out.println(items3);
        System.out.println("temps total : " + time + " minutes");

        if (time == 0.0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez ajouter au moins un exercice");
            alert.showAndWait();
        } else {
            ProgramManager fm = new ProgramManager();
            int num_program = (int) (Math.random() * 1000);
            boolean status = false;
            Client client = (Client) clientChoice.getSelectionModel().getSelectedItem();
            int client_id = client.getId();

            System.out.println("programme numéro " + num_program + " et client numero " + client_id);
            fm.addProgram(num_program, status, time, client_id);
            loadProgram();
        }
    }

    public void add_user(ActionEvent event) {

        String username = this.add_username.getText();
        String password = this.add_password.getText();


        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
        } else {
            LoginManager loginManager = new LoginManager();
            loginManager.addUser(username, password);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Utilisateur ajouté avec succes");
            alert.showAndWait();

            loadUser();
        }
    }


    public void load_user_tab(Event e) {
        loadUser();
        info_usersList.setItems(this.items5);
    }

    @FXML
    public void loadUser() {
        this.items5.clear();

        try {
            LoginManager loginManager = new LoginManager();
            ResultSet rs = loginManager.getAllUsers();

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                int id = rs.getInt("id");
                User user = new User(id, username, password);
                this.items5.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove_user(ActionEvent event) {
        User user = (User) info_usersList.getSelectionModel().getSelectedItem();
        LoginManager loginManager = new LoginManager();

        int userCount = loginManager.getUserCount(); // Récupérer le nombre total d'utilisateurs

        if (userCount == 1) { // Vérifie s'il reste un seul utilisateur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Impossible de supprimer le dernier utilisateur");
            alert.showAndWait();
            return; // Sort de la méthode
        }

        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un utilisateur");
            alert.showAndWait();
        } else {

            loginManager.removeUser(user.getId()); // Supprimer l'utilisateur

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Utilisateur supprimé avec succès");
            alert.showAndWait();
            this.loadUser(); // Recharge la liste des utilisateurs
        }
    }


    public void deconnexion(ActionEvent event) {
        try {
            // Charger le fichier logger.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Coachify/logger.fxml"));
            Scene loginScene = new Scene(loader.load(), 450, 500); // Dimensions de la scène

            // Récupérer le Stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène (connexion)
            stage.setScene(loginScene);

            // Optionnel : Changer le titre de la fenêtre
            stage.setTitle("Connexion");

            // Afficher la scène de connexion
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Afficher une erreur si le fichier FXML ne se charge pas
        }
    }


}