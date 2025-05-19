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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    private Button searchButton;
    @FXML
    private TableView<Adherent> searchResultsTable;
    @FXML
    private TableColumn<Adherent, Integer> idColumn;
    @FXML
    private TableColumn<Adherent, String> nomColumn;
    @FXML
    private TableColumn<Adherent, String> prenomColumn;
    @FXML
    private TableColumn<Adherent, String> adresseColumn;
    @FXML
    private TableColumn<Adherent, String> telephoneColumn;
    @FXML
    private TableColumn<Adherent, String> emailColumn;

    // Ajout
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField adresseField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField emailField;
    @FXML
    private Button ajouterButton;

    // Modification
    @FXML
    private TextField modifNomField;
    @FXML
    private TextField modifPrenomField;
    @FXML
    private TextField modifAdresseField;
    @FXML
    private TextField modifTelephoneField;
    @FXML
    private TextField modifEmailField;
    @FXML
    private TextField modifIdField;
    @FXML
    private Button modifierButton;

    // Suppression
    @FXML
    private TextField deleteIdField;
    @FXML
    private Button supprimerButton;

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
        configureTableColumns(idColumn, nomColumn, prenomColumn, 
                            adresseColumn, telephoneColumn, emailColumn);
        
        // Configuration des colonnes de la liste complète
        configureTableColumns(allIdColumn, allNomColumn, allPrenomColumn, 
                            allAdresseColumn, allTelephoneColumn, allEmailColumn);

        // Initialisation des actions des boutons
        ajouterButton.setOnAction(e -> handleAjouter());
        modifierButton.setOnAction(e -> handleModifier());
        supprimerButton.setOnAction(e -> handleSupprimer());

        // Chargement initial des adhérents
        loadAllAdherents();
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

    private void loadAllAdherents() {
        ObservableList<Adherent> adherents = FXCollections.observableArrayList(gestionnaire.getAllAdherents());
        allAdherentsTable.setItems(adherents);
    }

    @FXML
    public void handleSearch() {
        String searchTerm = searchField.getText();
        ObservableList<Adherent> results = FXCollections.observableArrayList(gestionnaire.searchAdherents(searchTerm));
        searchResultsTable.setItems(results);
    }

    @FXML
    public void handleAjouter() {
        try {
            Adherent adherent = new Adherent();
            adherent.setNom(nomField.getText());
            adherent.setPrenom(prenomField.getText());
            adherent.setAdresse(adresseField.getText());
            adherent.setTelephone(telephoneField.getText());
            adherent.setEmail(emailField.getText());

            gestionnaire.addAdherent(adherent);
            clearAddFields();
            loadAllAdherents();
            showAlert("Succès", "Adhérent ajouté avec succès", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de l'ajout de l'adhérent: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleModifier() {
        try {
            int id = Integer.parseInt(modifIdField.getText());
            Adherent adherent = gestionnaire.getAdherentById(id);
            if (adherent != null) {
                adherent.setNom(modifNomField.getText());
                adherent.setPrenom(modifPrenomField.getText());
                adherent.setAdresse(modifAdresseField.getText());
                adherent.setTelephone(modifTelephoneField.getText());
                adherent.setEmail(modifEmailField.getText());

                gestionnaire.updateAdherent(adherent);
                clearModifyFields();
                loadAllAdherents();
                showAlert("Succès", "Adhérent modifié avec succès", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Erreur", "Adhérent non trouvé", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de la modification de l'adhérent: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleSupprimer() {
        try {
            int id = Integer.parseInt(deleteIdField.getText());
            gestionnaire.deleteAdherent(id);
            clearDeleteFields();
            loadAllAdherents();
            showAlert("Succès", "Adhérent supprimé avec succès", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de la suppression de l'adhérent: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void clearAddFields() {
        nomField.clear();
        prenomField.clear();
        adresseField.clear();
        telephoneField.clear();
        emailField.clear();
    }

    private void clearModifyFields() {
        modifIdField.clear();
        modifNomField.clear();
        modifPrenomField.clear();
        modifAdresseField.clear();
        modifTelephoneField.clear();
        modifEmailField.clear();
    }

    private void clearDeleteFields() {
        deleteIdField.clear();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 