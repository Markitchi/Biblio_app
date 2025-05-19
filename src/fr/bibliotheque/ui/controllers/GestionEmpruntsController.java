package fr.bibliotheque.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import fr.bibliotheque.service.GestionnaireImpl;
import fr.bibliotheque.metier.Emprunt;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private TableView<Emprunt> empruntsEnCoursTable;
    @FXML
    private TableColumn<Emprunt, Integer> idColumn;
    @FXML
    private TableColumn<Emprunt, Integer> livreIdColumn;
    @FXML
    private TableColumn<Emprunt, Integer> adherentIdColumn;
    @FXML
    private TableColumn<Emprunt, LocalDate> dateEmpruntColumn;
    @FXML
    private TableColumn<Emprunt, LocalDate> dateRetourPrevueColumn;
    @FXML
    private TableColumn<Emprunt, LocalDate> dateRetourEffectiveColumn;
    @FXML
    private TableColumn<Emprunt, Boolean> retourneColumn;

    // Recherche
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<Emprunt> searchResultsTable;
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
    private TableColumn<Emprunt, LocalDate> searchDateRetourEffectiveColumn;
    @FXML
    private TableColumn<Emprunt, Boolean> searchRetourneColumn;

    // Emprunt
    @FXML
    private TextField livreIdField;
    @FXML
    private TextField adherentIdField;
    @FXML
    private DatePicker dateEmpruntPicker;
    @FXML
    private DatePicker dateRetourPicker;
    @FXML
    private Button enregistrerButton;

    // Retour
    @FXML
    private TextField empruntIdField;
    @FXML
    private Button retournerButton;

    private GestionnaireImpl gestionnaire;

    @FXML
    public void initialize() {
        gestionnaire = new GestionnaireImpl();
        
        // Initialisation de la navigation
        accueilButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/Dash.fxml"));
        gestionLivresButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/GestionLivre.fxml"));
        gestionAdherentsButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/GestionAdherents.fxml"));
        gestionEmpruntsButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/GestionEmprunts.fxml"));

        // Configuration des colonnes des emprunts en cours
        configureEnCoursColumns();
        
        // Configuration des colonnes de recherche
        configureSearchColumns();

        // Initialisation des actions des boutons
        enregistrerButton.setOnAction(e -> handleEmprunt());
        retournerButton.setOnAction(e -> handleRetour());

        // Chargement initial des emprunts en cours
        loadEmpruntsEnCours();
    }

    private void configureEnCoursColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        livreIdColumn.setCellValueFactory(new PropertyValueFactory<>("livreId"));
        adherentIdColumn.setCellValueFactory(new PropertyValueFactory<>("adherentId"));
        dateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        dateRetourPrevueColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetourPrevue"));
        dateRetourEffectiveColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetourEffective"));
        retourneColumn.setCellValueFactory(new PropertyValueFactory<>("retourne"));
    }

    private void configureSearchColumns() {
        searchIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        searchLivreIdColumn.setCellValueFactory(new PropertyValueFactory<>("livreId"));
        searchAdherentIdColumn.setCellValueFactory(new PropertyValueFactory<>("adherentId"));
        searchDateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        searchDateRetourPrevueColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetourPrevue"));
        searchDateRetourEffectiveColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetourEffective"));
        searchRetourneColumn.setCellValueFactory(new PropertyValueFactory<>("retourne"));
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

    private void loadEmpruntsEnCours() {
        ObservableList<Emprunt> emprunts = FXCollections.observableArrayList(gestionnaire.getEmpruntsEnCours());
        empruntsEnCoursTable.setItems(emprunts);
    }

    private void handleEmprunt() {
        try {
            int livreId = Integer.parseInt(livreIdField.getText());
            int adherentId = Integer.parseInt(adherentIdField.getText());
            LocalDate dateEmprunt = dateEmpruntPicker.getValue();
            LocalDate dateRetour = dateRetourPicker.getValue();

            if (dateEmprunt == null || dateRetour == null) {
                showAlert("Erreur", "Veuillez sélectionner les dates d'emprunt et de retour", Alert.AlertType.ERROR);
                return;
            }

            Emprunt emprunt = new Emprunt();
            emprunt.setLivreId(livreId);
            emprunt.setAdherentId(adherentId);
            emprunt.setDateEmprunt(dateEmprunt);
            emprunt.setDateRetourPrevue(dateRetour);
            emprunt.setRetourne(false);

            gestionnaire.addEmprunt(emprunt);
            clearEmpruntFields();
            loadEmpruntsEnCours();
            showAlert("Succès", "Emprunt enregistré avec succès", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des IDs valides", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de l'enregistrement de l'emprunt: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void handleRetour() {
        try {
            int empruntId = Integer.parseInt(empruntIdField.getText());
            gestionnaire.retournerLivre(empruntId);
            clearRetournerFields();
            loadEmpruntsEnCours();
            showAlert("Succès", "Livre retourné avec succès", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer un ID d'emprunt valide", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors du retour du livre: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void clearEmpruntFields() {
        livreIdField.clear();
        adherentIdField.clear();
        dateEmpruntPicker.setValue(null);
        dateRetourPicker.setValue(null);
    }

    private void clearRetournerFields() {
        empruntIdField.clear();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 