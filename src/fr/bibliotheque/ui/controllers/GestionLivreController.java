package fr.bibliotheque.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import fr.bibliotheque.metier.Livre;
import fr.bibliotheque.service.GestionnaireImpl;

public class GestionLivreController extends BaseController {
    @FXML
    private TextField searchField;
    
    @FXML
    private TableView<Livre> livresTable;
    
    @FXML
    private TableColumn<Livre, Integer> idColumn;
    
    @FXML
    private TableColumn<Livre, String> titreColumn;
    
    @FXML
    private TableColumn<Livre, String> auteursColumn;
    
    @FXML
    private TableColumn<Livre, String> isbnColumn;
    
    @FXML
    private TableColumn<Livre, Integer> anneeColumn;
    
    @FXML
    private TableColumn<Livre, Boolean> dispoColumn;
    
    // Champs pour l'ajout
    @FXML
    private TextField titreField;
    
    @FXML
    private TextField auteursField;
    
    @FXML
    private TextField isbnField;
    
    @FXML
    private TextField anneeField;
    
    // Champs pour la modification
    @FXML
    private TextField modifIdField;
    
    @FXML
    private TextField modifTitreField;
    
    @FXML
    private TextField modifAuteursField;
    
    @FXML
    private TextField modifIsbnField;
    
    @FXML
    private TextField modifAnneeField;
    
    @FXML
    private TextField modifDispoField;
    
    // Champs pour la suppression
    @FXML
    private TextField supprIdField;
    
    private GestionnaireImpl gestionnaire;
    private ObservableList<Livre> livresList;
    
    @FXML
    public void initialize() {
        gestionnaire = new GestionnaireImpl();
        livresList = FXCollections.observableArrayList();
        
        // Configuration des colonnes
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        auteursColumn.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        anneeColumn.setCellValueFactory(new PropertyValueFactory<>("anneePublication"));
        dispoColumn.setCellValueFactory(new PropertyValueFactory<>("disponible"));
        
        // Charger tous les livres
        loadLivres();
    }
    
    private void loadLivres() {
        livresList.clear();
        livresList.addAll(gestionnaire.listerTousLivres());
        livresTable.setItems(livresList);
    }
    
    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.isEmpty()) {
            loadLivres();
            return;
        }
        
        livresList.clear();
        livresList.addAll(gestionnaire.rechercherLivre(searchText));
    }
    
    @FXML
    private void handleAddLivre() {
        try {
            String titre = titreField.getText();
            String auteur = auteursField.getText();
            String isbn = isbnField.getText();
            int annee = Integer.parseInt(anneeField.getText());
            
            if (titre.isEmpty() || auteur.isEmpty() || isbn.isEmpty()) {
                showError("Veuillez remplir tous les champs");
                return;
            }
            
            Livre livre = new Livre(titre, auteur, isbn, annee);
            if (gestionnaire.ajouterLivre(livre)) {
                // Réinitialiser les champs
                titreField.clear();
                auteursField.clear();
                isbnField.clear();
                anneeField.clear();
                
                // Recharger la liste
                loadLivres();
            } else {
                showError("Erreur lors de l'ajout du livre");
            }
            
        } catch (NumberFormatException e) {
            showError("L'année doit être un nombre valide");
        }
    }
    
    @FXML
    private void handleModifyLivre() {
        try {
            int id = Integer.parseInt(modifIdField.getText());
            String titre = modifTitreField.getText();
            String auteur = modifAuteursField.getText();
            String isbn = modifIsbnField.getText();
            int annee = Integer.parseInt(modifAnneeField.getText());
            boolean dispo = Integer.parseInt(modifDispoField.getText()) == 1;
            
            if (titre.isEmpty() || auteur.isEmpty() || isbn.isEmpty()) {
                showError("Veuillez remplir tous les champs");
                return;
            }
            
            Livre livre = new Livre(id, titre, auteur, isbn, annee, dispo);
            if (gestionnaire.modifierLivre(livre)) {
                // Réinitialiser les champs
                modifIdField.clear();
                modifTitreField.clear();
                modifAuteursField.clear();
                modifIsbnField.clear();
                modifAnneeField.clear();
                modifDispoField.clear();
                
                // Recharger la liste
                loadLivres();
            } else {
                showError("Erreur lors de la modification du livre");
            }
            
        } catch (NumberFormatException e) {
            showError("L'ID, l'année et la disponibilité doivent être des nombres valides");
        }
    }
    
    @FXML
    private void handleDeleteLivre() {
        try {
            int id = Integer.parseInt(supprIdField.getText());
            if (gestionnaire.supprimerLivre(id)) {
                // Réinitialiser le champ
                supprIdField.clear();
                
                // Recharger la liste
                loadLivres();
            } else {
                showError("Erreur lors de la suppression du livre");
            }
            
        } catch (NumberFormatException e) {
            showError("L'ID doit être un nombre valide");
        }
    }
    
    @Override
    protected void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 