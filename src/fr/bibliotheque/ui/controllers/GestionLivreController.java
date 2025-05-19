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
import fr.bibliotheque.metier.Livre;

public class GestionLivreController {
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
    private TableView<Livre> searchTable;
    @FXML
    private TableColumn<Livre, Integer> searchIdColumn;
    @FXML
    private TableColumn<Livre, String> searchTitreColumn;
    @FXML
    private TableColumn<Livre, String> searchAuteurColumn;
    @FXML
    private TableColumn<Livre, String> searchIsbnColumn;
    @FXML
    private TableColumn<Livre, Integer> searchAnneeColumn;
    @FXML
    private TableColumn<Livre, Boolean> searchDispoColumn;

    // Ajout
    @FXML
    private TextField addTitreField;
    @FXML
    private TextField addAuteurField;
    @FXML
    private TextField addIsbnField;
    @FXML
    private TextField addAnneeField;
    @FXML
    private Button addButton;

    // Modification
    @FXML
    private TextField modIdField;
    @FXML
    private TextField modTitreField;
    @FXML
    private TextField modAuteurField;
    @FXML
    private TextField modIsbnField;
    @FXML
    private TextField modAnneeField;
    @FXML
    private TextField modDispoField;
    @FXML
    private Button modButton;

    // Suppression
    @FXML
    private TextField deleteIdField;
    @FXML
    private Button deleteButton;

    // Liste complète
    @FXML
    private TableView<Livre> allBooksTable;
    @FXML
    private TableColumn<Livre, Integer> allIdColumn;
    @FXML
    private TableColumn<Livre, String> allTitreColumn;
    @FXML
    private TableColumn<Livre, String> allAuteurColumn;
    @FXML
    private TableColumn<Livre, String> allIsbnColumn;
    @FXML
    private TableColumn<Livre, Integer> allAnneeColumn;
    @FXML
    private TableColumn<Livre, Boolean> allDispoColumn;

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
        configureTableColumns(searchIdColumn, searchTitreColumn, searchAuteurColumn, 
                            searchIsbnColumn, searchAnneeColumn, searchDispoColumn);
        
        // Configuration des colonnes de la liste complète
        configureTableColumns(allIdColumn, allTitreColumn, allAuteurColumn, 
                            allIsbnColumn, allAnneeColumn, allDispoColumn);

        // Initialisation des actions des boutons
        addButton.setOnAction(e -> handleAddBook());
        modButton.setOnAction(e -> handleModifyBook());
        deleteButton.setOnAction(e -> handleDeleteBook());

        // Chargement initial des livres
        refreshAllBooksTable();
    }

    private void configureTableColumns(TableColumn<Livre, Integer> idCol,
                                     TableColumn<Livre, String> titreCol,
                                     TableColumn<Livre, String> auteurCol,
                                     TableColumn<Livre, String> isbnCol,
                                     TableColumn<Livre, Integer> anneeCol,
                                     TableColumn<Livre, Boolean> dispoCol) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        auteurCol.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        anneeCol.setCellValueFactory(new PropertyValueFactory<>("anneePublication"));
        dispoCol.setCellValueFactory(new PropertyValueFactory<>("disponible"));
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

    private void handleAddBook() {
        try {
            String titre = addTitreField.getText();
            String auteur = addAuteurField.getText();
            String isbn = addIsbnField.getText();
            int annee = Integer.parseInt(addAnneeField.getText());

            Livre livre = new Livre();
            livre.setTitre(titre);
            livre.setAuteur(auteur);
            livre.setIsbn(isbn);
            livre.setAnneePublication(annee);
            livre.setDisponible(true);

            if (gestionnaire.ajouterLivre(livre)) {
                clearAddFields();
                refreshAllBooksTable();
            } else {
                showError("Erreur lors de l'ajout du livre");
            }
        } catch (Exception e) {
            showError("Erreur lors de l'ajout du livre: " + e.getMessage());
        }
    }

    private void handleModifyBook() {
        try {
            int id = Integer.parseInt(modIdField.getText());
            String titre = modTitreField.getText();
            String auteur = modAuteurField.getText();
            String isbn = modIsbnField.getText();
            int annee = Integer.parseInt(modAnneeField.getText());
            boolean dispo = Integer.parseInt(modDispoField.getText()) == 1;

            Livre livre = new Livre();
            livre.setId(id);
            livre.setTitre(titre);
            livre.setAuteur(auteur);
            livre.setIsbn(isbn);
            livre.setAnneePublication(annee);
            livre.setDisponible(dispo);

            if (gestionnaire.modifierLivre(livre)) {
                clearModifyFields();
                refreshAllBooksTable();
            } else {
                showError("Erreur lors de la modification du livre");
            }
        } catch (Exception e) {
            showError("Erreur lors de la modification du livre: " + e.getMessage());
        }
    }

    private void handleDeleteBook() {
        try {
            int id = Integer.parseInt(deleteIdField.getText());
            if (gestionnaire.supprimerLivre(id)) {
                clearDeleteFields();
                refreshAllBooksTable();
            } else {
                showError("Erreur lors de la suppression du livre");
            }
        } catch (Exception e) {
            showError("Erreur lors de la suppression du livre: " + e.getMessage());
        }
    }

    private void refreshAllBooksTable() {
        allBooksTable.getItems().clear();
        allBooksTable.getItems().addAll(gestionnaire.listerTousLivres());
    }

    private void clearAddFields() {
        addTitreField.clear();
        addAuteurField.clear();
        addIsbnField.clear();
        addAnneeField.clear();
    }

    private void clearModifyFields() {
        modIdField.clear();
        modTitreField.clear();
        modAuteurField.clear();
        modIsbnField.clear();
        modAnneeField.clear();
        modDispoField.clear();
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