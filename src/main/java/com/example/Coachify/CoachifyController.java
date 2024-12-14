package com.example.Coachify;

import Models.Client;
import bdd.ProgramManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
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
import tools.Generator;

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
    private ListView<String> info_entrepriseList;


    ObservableList<Exercice> items = FXCollections.observableArrayList();
    ObservableList<Client> items2 = FXCollections.observableArrayList();
    ObservableList<Exercice> items3 = FXCollections.observableArrayList();

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
            Double time = Generator.SumAmmount(this.items3);
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
                int repExercice = rs.getInt("répétitions");
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
        } else if (!email.matches("^(.+)@(.+)$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une adresse email valide");
            alert.showAndWait();
        } else {
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

    public void generate_program_pdf() {

        Double time = Generator.SumAmmount(this.items3);
        System.out.println("temps total : " + time);

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

            System.out.println("numéro du programme : " + num_program + " et numero du client : " + client_id);
            fm.addProgram(num_program, status, time, client_id);
        }
    }

/*
    @FXML
    public void load_info_entreprise() {
        // Spécifier le chemin du fichier JSON
        String filePath = "src/main/resources/json/informations_entreprise.json";

        try {
            // Créer un ObjectMapper pour gérer le JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // Lire le fichier JSON et le convertir en liste de Map
            File file = new File(filePath);
            List<Map<String, Object>> dataList;

            if (file.exists()) {
                dataList = objectMapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});
            } else {
                dataList = new ArrayList<>();
            }

            // Récupérer les informations de chaque entrée et les ajouter à la ListView
            ObservableList<String> displayList = FXCollections.observableArrayList();

            for (Map<String, Object> entry : dataList) {
                String phone = (String) entry.get("phone");
                String adresse = (String) entry.get("adresse");
                String email = (String) entry.get("email");

                // Format des informations à afficher dans la ListView
                String displayInfo = "phone: " + phone + ", Adresse: " + adresse + ", Email: " + email;
                displayList.add(displayInfo);
            }

            // Affecter la liste observable à la ListView
            info_entreprise.setItems(displayList);

            System.out.println("Les informations de l'entreprise ont été chargées avec succès.");

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
            e.printStackTrace();
        }
    }
*/

}