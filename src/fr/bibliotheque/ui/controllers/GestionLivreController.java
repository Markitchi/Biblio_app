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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GestionLivreController {
    private GestionnaireImpl gestionnaire;

    // Navigation buttons
    @FXML private Button accueilButton;
    @FXML private Button gestionLivresButton;
    @FXML private Button gestionAdherentsButton;
    @FXML private Button gestionEmpruntsButton;

    // Search tab
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private TableView<Livre> searchResultsTable;
    @FXML private TableColumn<Livre, Integer> idColumn;
    @FXML private TableColumn<Livre, String> titreColumn;
    @FXML private TableColumn<Livre, String> auteursColumn;
    @FXML private TableColumn<Livre, String> isbnColumn;
    @FXML private TableColumn<Livre, Integer> anneeColumn;
    @FXML private TableColumn<Livre, Boolean> dispoColumn;

    // Add tab
    @FXML private TextField titreField;
    @FXML private TextField auteurField;
    @FXML private TextField isbnField;
    @FXML private TextField anneeField;
    @FXML private Button ajouterButton;

    // Modify tab
    @FXML private TextField modifTitreField;
    @FXML private TextField modifAuteurField;
    @FXML private TextField modifIsbnField;
    @FXML private TextField modifAnneeField;
    @FXML private TextField modifDispoField;
    @FXML private TextField modifIdField;
    @FXML private Button modifierButton;

    // Delete tab
    @FXML private TextField deleteIdField;
    @FXML private Button supprimerButton;

    // All books tab
    @FXML private TableView<Livre> allLivresTable;
    @FXML private TableColumn<Livre, Integer> allIdColumn;
    @FXML private TableColumn<Livre, String> allTitreColumn;
    @FXML private TableColumn<Livre, String> allAuteursColumn;
    @FXML private TableColumn<Livre, String> allIsbnColumn;
    @FXML private TableColumn<Livre, Integer> allAnneeColumn;
    @FXML private TableColumn<Livre, Boolean> allDispoColumn;

    @FXML
    public void initialize() {
        gestionnaire = new GestionnaireImpl();
        setupTableColumns();
        loadAllLivres();
    }

    private void setupTableColumns() {
        // Setup search results table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        auteursColumn.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        anneeColumn.setCellValueFactory(new PropertyValueFactory<>("anneePublication"));
        dispoColumn.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        // Setup all books table columns
        allIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allTitreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        allAuteursColumn.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        allIsbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        allAnneeColumn.setCellValueFactory(new PropertyValueFactory<>("anneePublication"));
        allDispoColumn.setCellValueFactory(new PropertyValueFactory<>("disponible"));
    }

    private void loadAllLivres() {
        ObservableList<Livre> livres = FXCollections.observableArrayList(gestionnaire.getAllLivres());
        allLivresTable.setItems(livres);
    }

    @FXML
    public void handleSearch() {
        String searchTerm = searchField.getText();
        ObservableList<Livre> results = FXCollections.observableArrayList(gestionnaire.searchLivres(searchTerm));
        searchResultsTable.setItems(results);
    }

    @FXML
    public void handleAjouter() {
        try {
            Livre livre = new Livre();
            livre.setTitre(titreField.getText());
            livre.setAuteur(auteurField.getText());
            livre.setIsbn(isbnField.getText());
            livre.setAnneePublication(Integer.parseInt(anneeField.getText()));
            livre.setDisponible(true);

            gestionnaire.addLivre(livre);
            clearAddFields();
            loadAllLivres();
            showAlert("Succès", "Livre ajouté avec succès", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de l'ajout du livre: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleModifier() {
        try {
            int id = Integer.parseInt(modifIdField.getText());
            Livre livre = gestionnaire.getLivreById(id);
            if (livre != null) {
                livre.setTitre(modifTitreField.getText());
                livre.setAuteur(modifAuteurField.getText());
                livre.setIsbn(modifIsbnField.getText());
                livre.setAnneePublication(Integer.parseInt(modifAnneeField.getText()));
                livre.setDisponible(Integer.parseInt(modifDispoField.getText()) == 1);

                gestionnaire.updateLivre(livre);
                clearModifyFields();
                loadAllLivres();
                showAlert("Succès", "Livre modifié avec succès", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Erreur", "Livre non trouvé", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de la modification du livre: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleSupprimer() {
        try {
            int id = Integer.parseInt(deleteIdField.getText());
            gestionnaire.deleteLivre(id);
            clearDeleteFields();
            loadAllLivres();
            showAlert("Succès", "Livre supprimé avec succès", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de la suppression du livre: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void clearAddFields() {
        titreField.clear();
        auteurField.clear();
        isbnField.clear();
        anneeField.clear();
    }

    private void clearModifyFields() {
        modifIdField.clear();
        modifTitreField.clear();
        modifAuteurField.clear();
        modifIsbnField.clear();
        modifAnneeField.clear();
        modifDispoField.clear();
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

    // Navigation methods
    @FXML
    public void handleAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/bibliotheque/ui/vues/Dash.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) accueilButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleGestionLivres() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/bibliotheque/ui/vues/GestionLivre.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) gestionLivresButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleGestionAdherents() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/bibliotheque/ui/vues/GestionAdherents.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) gestionAdherentsButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleGestionEmprunts() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/bibliotheque/ui/vues/GestionEmprunts.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) gestionEmpruntsButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 