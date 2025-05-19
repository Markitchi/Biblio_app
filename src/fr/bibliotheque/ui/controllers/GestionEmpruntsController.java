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
    private TableColumn<Emprunt, Date> enCoursDateEmpruntColumn;
    @FXML
    private TableColumn<Emprunt, Date> enCoursDateRetourPrevueColumn;

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
    private TableColumn<Emprunt, Date> searchDateEmpruntColumn;
    @FXML
    private TableColumn<Emprunt, Date> searchDateRetourPrevueColumn;
    @FXML
    private TableColumn<Emprunt, Date> searchDateRetourEffectiveColumn;

    // Emprunt
    @FXML
    private TextField empruntLivreIdField;
    @FXML
    private TextField empruntAdherentIdField;
    @FXML
    private DatePicker empruntDatePicker;
    @FXML
    private Button empruntButton;

    // Retour
    @FXML
    private TextField retourIdField;
    @FXML
    private DatePicker retourDatePicker;
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

        // Configuration des colonnes des emprunts en cours
        configureEnCoursColumns();
        
        // Configuration des colonnes de recherche
        configureSearchColumns();

        // Initialisation des actions des boutons
        empruntButton.setOnAction(e -> handleEmprunt());
        retourButton.setOnAction(e -> handleRetour());

        // Chargement initial des emprunts en cours
        refreshEnCoursTable();
    }

    private void configureEnCoursColumns() {
        enCoursIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        enCoursLivreIdColumn.setCellValueFactory(new PropertyValueFactory<>("livreId"));
        enCoursAdherentIdColumn.setCellValueFactory(new PropertyValueFactory<>("adherentId"));
        enCoursDateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        enCoursDateRetourPrevueColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetourPrevue"));
    }

    private void configureSearchColumns() {
        searchIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        searchLivreIdColumn.setCellValueFactory(new PropertyValueFactory<>("livreId"));
        searchAdherentIdColumn.setCellValueFactory(new PropertyValueFactory<>("adherentId"));
        searchDateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        searchDateRetourPrevueColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetourPrevue"));
        searchDateRetourEffectiveColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetourEffective"));
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
            Date dateEmprunt = new Date(); // Date actuelle
            Date dateRetourPrevue = new Date(dateEmprunt.getTime() + (14 * 24 * 60 * 60 * 1000L)); // +14 jours

            Emprunt emprunt = new Emprunt();
            emprunt.setLivreId(livreId);
            emprunt.setAdherentId(adherentId);
            emprunt.setDateEmprunt(dateEmprunt);
            emprunt.setDateRetourPrevue(dateRetourPrevue);

            if (gestionnaire.emprunterLivre(emprunt)) {
                clearEmpruntFields();
                refreshEnCoursTable();
            } else {
                showError("Erreur lors de l'emprunt du livre");
            }
        } catch (Exception e) {
            showError("Erreur lors de l'emprunt du livre: " + e.getMessage());
        }
    }

    private void handleRetour() {
        try {
            int id = Integer.parseInt(retourIdField.getText());
            Date dateRetour = new Date(); // Date actuelle

            if (gestionnaire.retournerLivre(id, dateRetour)) {
                clearRetourFields();
                refreshEnCoursTable();
            } else {
                showError("Erreur lors du retour du livre");
            }
        } catch (Exception e) {
            showError("Erreur lors du retour du livre: " + e.getMessage());
        }
    }

    private void refreshEnCoursTable() {
        enCoursTable.getItems().clear();
        enCoursTable.getItems().addAll(gestionnaire.listerEmpruntsEnCours());
    }

    private void clearEmpruntFields() {
        empruntLivreIdField.clear();
        empruntAdherentIdField.clear();
        empruntDatePicker.setValue(null);
    }

    private void clearRetourFields() {
        retourIdField.clear();
        retourDatePicker.setValue(null);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 