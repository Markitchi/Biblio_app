package fr.bibliotheque.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import fr.bibliotheque.service.GestionnaireImpl;
import fr.bibliotheque.metier.Adherent;

public class GestionAdherentsController {
    @FXML
    private Button accueilButton;
    @FXML
    private Button gestionLivresButton;
    @FXML
    private Button gestionAdherentsButton;
    @FXML
    private Button gestionEmpruntsButton;
    
    // Recherche
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Adherent> searchTable;
    @FXML
    private TableColumn<Adherent, Integer> searchIdColumn;
    @FXML
    private TableColumn<Adherent, String> searchNomColumn;
    @FXML
    private TableColumn<Adherent, String> searchPrenomColumn;
    @FXML
    private TableColumn<Adherent, String> searchAdresseColumn;
    @FXML
    private TableColumn<Adherent, String> searchTelephoneColumn;
    @FXML
    private TableColumn<Adherent, String> searchEmailColumn;

    // Ajout
    @FXML
    private TextField addNomField;
    @FXML
    private TextField addPrenomField;
    @FXML
    private TextField addAdresseField;
    @FXML
    private TextField addTelephoneField;
    @FXML
    private TextField addEmailField;
    @FXML
    private Button addButton;

    // Modification
    @FXML
    private TextField modIdField;
    @FXML
    private TextField modNomField;
    @FXML
    private TextField modPrenomField;
    @FXML
    private TextField modAdresseField;
    @FXML
    private TextField modTelephoneField;
    @FXML
    private TextField modEmailField;
    @FXML
    private Button modButton;

    // Suppression
    @FXML
    private TextField deleteIdField;
    @FXML
    private Button deleteButton;

    // Liste complète
    @FXML
    private TableView<Adherent> allAdherentsTable;
    @FXML
    private TableColumn<Adherent, Integer> allIdColumn;
    @FXML
    private TableColumn<Adherent, String> allNomColumn;
    @FXML
    private TableColumn<Adherent, String> allPrenomColumn;
    @FXML
    private TableColumn<Adherent, String> allAdresseColumn;
    @FXML
    private TableColumn<Adherent, String> allTelephoneColumn;
    @FXML
    private TableColumn<Adherent, String> allEmailColumn;

    private GestionnaireImpl gestionnaire;

    @FXML
    public void initialize() {
        gestionnaire = new GestionnaireImpl();
        
        // Initialisation de la navigation
        accueilButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/Dash.fxml"));
        gestionLivresButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/GestionLivre.fxml"));
        gestionAdherentsButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/GestionAdherents.fxml"));
        gestionEmpruntsButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/GestionEmprunts.fxml"));

        // Configuration des colonnes de recherche
        configureTableColumns(searchIdColumn, searchNomColumn, searchPrenomColumn, 
                            searchAdresseColumn, searchTelephoneColumn, searchEmailColumn);
        
        // Configuration des colonnes de la liste complète
        configureTableColumns(allIdColumn, allNomColumn, allPrenomColumn, 
                            allAdresseColumn, allTelephoneColumn, allEmailColumn);

        // Initialisation des actions des boutons
        addButton.setOnAction(e -> handleAddAdherent());
        modButton.setOnAction(e -> handleModifyAdherent());
        deleteButton.setOnAction(e -> handleDeleteAdherent());

        // Chargement initial des adhérents
        refreshAllAdherentsTable();
    }

    private void configureTableColumns(TableColumn<Adherent, Integer> idCol,
                                     TableColumn<Adherent, String> nomCol,
                                     TableColumn<Adherent, String> prenomCol,
                                     TableColumn<Adherent, String> adresseCol,
                                     TableColumn<Adherent, String> telephoneCol,
                                     TableColumn<Adherent, String> emailCol) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telephoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
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

    private void handleAddAdherent() {
        try {
            String nom = addNomField.getText();
            String prenom = addPrenomField.getText();
            String adresse = addAdresseField.getText();
            String telephone = addTelephoneField.getText();
            String email = addEmailField.getText();

            Adherent adherent = new Adherent();
            adherent.setNom(nom);
            adherent.setPrenom(prenom);
            adherent.setAdresse(adresse);
            adherent.setTelephone(telephone);
            adherent.setEmail(email);

            if (gestionnaire.ajouterAdherent(adherent)) {
                clearAddFields();
                refreshAllAdherentsTable();
            } else {
                showError("Erreur lors de l'ajout de l'adhérent");
            }
        } catch (Exception e) {
            showError("Erreur lors de l'ajout de l'adhérent: " + e.getMessage());
        }
    }

    private void handleModifyAdherent() {
        try {
            int id = Integer.parseInt(modIdField.getText());
            String nom = modNomField.getText();
            String prenom = modPrenomField.getText();
            String adresse = modAdresseField.getText();
            String telephone = modTelephoneField.getText();
            String email = modEmailField.getText();

            Adherent adherent = new Adherent();
            adherent.setId(id);
            adherent.setNom(nom);
            adherent.setPrenom(prenom);
            adherent.setAdresse(adresse);
            adherent.setTelephone(telephone);
            adherent.setEmail(email);

            if (gestionnaire.modifierAdherent(adherent)) {
                clearModifyFields();
                refreshAllAdherentsTable();
            } else {
                showError("Erreur lors de la modification de l'adhérent");
            }
        } catch (Exception e) {
            showError("Erreur lors de la modification de l'adhérent: " + e.getMessage());
        }
    }

    private void handleDeleteAdherent() {
        try {
            int id = Integer.parseInt(deleteIdField.getText());
            if (gestionnaire.supprimerAdherent(id)) {
                clearDeleteFields();
                refreshAllAdherentsTable();
            } else {
                showError("Erreur lors de la suppression de l'adhérent");
            }
        } catch (Exception e) {
            showError("Erreur lors de la suppression de l'adhérent: " + e.getMessage());
        }
    }

    private void refreshAllAdherentsTable() {
        allAdherentsTable.getItems().clear();
        allAdherentsTable.getItems().addAll(gestionnaire.listerTousAdherents());
    }

    private void clearAddFields() {
        addNomField.clear();
        addPrenomField.clear();
        addAdresseField.clear();
        addTelephoneField.clear();
        addEmailField.clear();
    }

    private void clearModifyFields() {
        modIdField.clear();
        modNomField.clear();
        modPrenomField.clear();
        modAdresseField.clear();
        modTelephoneField.clear();
        modEmailField.clear();
    }

    private void clearDeleteFields() {
        deleteIdField.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 