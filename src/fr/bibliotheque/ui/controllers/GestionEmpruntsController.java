package fr.bibliotheque.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import fr.bibliotheque.service.GestionnaireImpl;
import fr.bibliotheque.model.Emprunt;

import java.time.LocalDate;

public class GestionEmpruntsController {
    @FXML
    private Button accueilButton;
    @FXML
    private Button gestionLivresButton;
    @FXML
    private Button gestionAdherentsButton;
    @FXML
    private Button gestionEmpruntsButton;
    
    // Emprunts en cours
    @FXML
    private TableView<Emprunt> enCoursTable;
    @FXML
    private TableColumn<Emprunt, Integer> enCoursIdColumn;
    @FXML
    private TableColumn<Emprunt, Integer> enCoursLivreIdColumn;
    @FXML
    private TableColumn<Emprunt, Integer> enCoursAdherentIdColumn;
    @FXML
    private TableColumn<Emprunt, LocalDate> enCoursDateEmpruntColumn;
    @FXML
    private TableColumn<Emprunt, LocalDate> enCoursDateRetourPrevueColumn;
    @FXML
    private TableColumn<Emprunt, LocalDate> enCoursDateRetourEffectifColumn;
    @FXML
    private TableColumn<Emprunt, Boolean> enCoursRetourneColumn;

    // Recherche
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Emprunt> searchTable;
    @FXML
    private TableColumn<Emprunt, Integer> searchIdColumn;
    @FXML
    private TableColumn<Emprunt, Integer> searchLivreIdColumn;
    @FXML
    private TableColumn<Emprunt, Integer> searchAdherentIdColumn;
    @FXML
    private TableColumn<Emprunt, LocalDate> searchDateEmpruntColumn;
    @FXML
    private TableColumn<Emprunt, LocalDate> searchDateRetourPrevueColumn;
    @FXML
    private TableColumn<Emprunt, LocalDate> searchDateRetourEffectifColumn;
    @FXML
    private TableColumn<Emprunt, Boolean> searchRetourneColumn;

    // Nouvel emprunt
    @FXML
    private TextField empruntLivreIdField;
    @FXML
    private TextField empruntAdherentIdField;
    @FXML
    private DatePicker empruntDateEmpruntPicker;
    @FXML
    private DatePicker empruntDateRetourPicker;
    @FXML
    private Button empruntButton;

    // Retour
    @FXML
    private TextField retourEmpruntIdField;
    @FXML
    private Button retourButton;

    private GestionnaireImpl gestionnaire;

    @FXML
    public void initialize() {
        gestionnaire = new GestionnaireImpl();
        
        // Initialisation de la navigation
        accueilButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/Dash.fxml"));
        gestionLivresButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/GestionLivre.fxml"));
        gestionAdherentsButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/GestionAdherents.fxml"));
        gestionEmpruntsButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/GestionEmprunts.fxml"));

        // Configuration des colonnes des tables
        configureTableColumns(enCoursIdColumn, enCoursLivreIdColumn, enCoursAdherentIdColumn,
                            enCoursDateEmpruntColumn, enCoursDateRetourPrevueColumn,
                            enCoursDateRetourEffectifColumn, enCoursRetourneColumn);

        configureTableColumns(searchIdColumn, searchLivreIdColumn, searchAdherentIdColumn,
                            searchDateEmpruntColumn, searchDateRetourPrevueColumn,
                            searchDateRetourEffectifColumn, searchRetourneColumn);

        // Initialisation des actions des boutons
        empruntButton.setOnAction(e -> handleEmprunt());
        retourButton.setOnAction(e -> handleRetour());

        // Chargement initial des emprunts en cours
        refreshEnCoursTable();
    }

    private void configureTableColumns(TableColumn<Emprunt, Integer> idCol,
                                     TableColumn<Emprunt, Integer> livreIdCol,
                                     TableColumn<Emprunt, Integer> adherentIdCol,
                                     TableColumn<Emprunt, LocalDate> dateEmpruntCol,
                                     TableColumn<Emprunt, LocalDate> dateRetourPrevueCol,
                                     TableColumn<Emprunt, LocalDate> dateRetourEffectifCol,
                                     TableColumn<Emprunt, Boolean> retourneCol) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        livreIdCol.setCellValueFactory(new PropertyValueFactory<>("livreId"));
        adherentIdCol.setCellValueFactory(new PropertyValueFactory<>("adherentId"));
        dateEmpruntCol.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        dateRetourPrevueCol.setCellValueFactory(new PropertyValueFactory<>("dateRetourPrevue"));
        dateRetourEffectifCol.setCellValueFactory(new PropertyValueFactory<>("dateRetourEffectif"));
        retourneCol.setCellValueFactory(new PropertyValueFactory<>("retourne"));
    }

    private void handleNavigation(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) accueilButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleEmprunt() {
        try {
            int livreId = Integer.parseInt(empruntLivreIdField.getText());
            int adherentId = Integer.parseInt(empruntAdherentIdField.getText());
            LocalDate dateEmprunt = empruntDateEmpruntPicker.getValue();
            LocalDate dateRetourPrevue = empruntDateRetourPicker.getValue();

            if (dateEmprunt == null || dateRetourPrevue == null) {
                showError("Veuillez sélectionner les dates d'emprunt et de retour");
                return;
            }

            Emprunt emprunt = new Emprunt();
            emprunt.setLivreId(livreId);
            emprunt.setAdherentId(adherentId);
            emprunt.setDateEmprunt(dateEmprunt);
            emprunt.setDateRetourPrevue(dateRetourPrevue);
            emprunt.setRetourne(false);

            gestionnaire.enregistrerEmprunt(emprunt);
            clearEmpruntFields();
            refreshEnCoursTable();
        } catch (Exception e) {
            showError("Erreur lors de l'enregistrement de l'emprunt: " + e.getMessage());
        }
    }

    private void handleRetour() {
        try {
            int empruntId = Integer.parseInt(retourEmpruntIdField.getText());
            gestionnaire.enregistrerRetour(empruntId);
            clearRetourFields();
            refreshEnCoursTable();
        } catch (Exception e) {
            showError("Erreur lors de l'enregistrement du retour: " + e.getMessage());
        }
    }

    private void refreshEnCoursTable() {
        enCoursTable.getItems().clear();
        enCoursTable.getItems().addAll(gestionnaire.listerEmpruntsEnCours());
    }

    private void clearEmpruntFields() {
        empruntLivreIdField.clear();
        empruntAdherentIdField.clear();
        empruntDateEmpruntPicker.setValue(null);
        empruntDateRetourPicker.setValue(null);
    }

    private void clearRetourFields() {
        retourEmpruntIdField.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 