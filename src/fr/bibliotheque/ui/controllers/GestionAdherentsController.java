package fr.bibliotheque.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import fr.bibliotheque.metier.Adherent;
import fr.bibliotheque.service.GestionnaireImpl;

public class GestionAdherentsController extends BaseController {
    @FXML
    private TextField searchField;
    
    @FXML
    private TableView<Adherent> adherentsTable;
    
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
    
    // Champs pour l'ajout
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
    
    // Champs pour la modification
    @FXML
    private TextField modifIdField;
    
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
    
    // Champs pour la suppression
    @FXML
    private TextField supprIdField;
    
    private GestionnaireImpl gestionnaire;
    private ObservableList<Adherent> adherentsList;
    
    @FXML
    public void initialize() {
        gestionnaire = new GestionnaireImpl();
        adherentsList = FXCollections.observableArrayList();
        
        // Configuration des colonnes
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        // Charger tous les adhérents
        loadAdherents();
    }
    
    private void loadAdherents() {
        adherentsList.clear();
        adherentsList.addAll(gestionnaire.listerTousAdherents());
        adherentsTable.setItems(adherentsList);
    }
    
    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.isEmpty()) {
            loadAdherents();
            return;
        }
        
        adherentsList.clear();
        adherentsList.addAll(gestionnaire.rechercherAdherent(searchText));
    }
    
    @FXML
    private void handleAddAdherent() {
        try {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String adresse = adresseField.getText();
            String telephone = telephoneField.getText();
            String email = emailField.getText();
            
            if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || telephone.isEmpty() || email.isEmpty()) {
                showError("Veuillez remplir tous les champs");
                return;
            }
            
            Adherent adherent = new Adherent(nom, prenom, adresse, telephone, email);
            if (gestionnaire.ajouterAdherent(adherent)) {
                // Réinitialiser les champs
                nomField.clear();
                prenomField.clear();
                adresseField.clear();
                telephoneField.clear();
                emailField.clear();
                
                // Recharger la liste
                loadAdherents();
            } else {
                showError("Erreur lors de l'ajout de l'adhérent");
            }
            
        } catch (Exception e) {
            showError("Erreur lors de l'ajout de l'adhérent");
        }
    }
    
    @FXML
    private void handleModifyAdherent() {
        try {
            int id = Integer.parseInt(modifIdField.getText());
            String nom = modifNomField.getText();
            String prenom = modifPrenomField.getText();
            String adresse = modifAdresseField.getText();
            String telephone = modifTelephoneField.getText();
            String email = modifEmailField.getText();
            
            if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || telephone.isEmpty() || email.isEmpty()) {
                showError("Veuillez remplir tous les champs");
                return;
            }
            
            Adherent adherent = new Adherent(id, nom, prenom, adresse, telephone, email);
            if (gestionnaire.modifierAdherent(adherent)) {
                // Réinitialiser les champs
                modifIdField.clear();
                modifNomField.clear();
                modifPrenomField.clear();
                modifAdresseField.clear();
                modifTelephoneField.clear();
                modifEmailField.clear();
                
                // Recharger la liste
                loadAdherents();
            } else {
                showError("Erreur lors de la modification de l'adhérent");
            }
            
        } catch (NumberFormatException e) {
            showError("L'ID doit être un nombre valide");
        }
    }
    
    @FXML
    private void handleDeleteAdherent() {
        try {
            int id = Integer.parseInt(supprIdField.getText());
            if (gestionnaire.supprimerAdherent(id)) {
                // Réinitialiser le champ
                supprIdField.clear();
                
                // Recharger la liste
                loadAdherents();
            } else {
                showError("Erreur lors de la suppression de l'adhérent");
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

    @Override
    protected Scene getScene() {
        return nomField.getScene();
    }
} 