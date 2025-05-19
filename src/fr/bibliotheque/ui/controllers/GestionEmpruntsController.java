package fr.bibliotheque.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import fr.bibliotheque.metier.Emprunt;
import fr.bibliotheque.service.GestionnaireImpl;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.scene.Scene;

public class GestionEmpruntsController extends BaseController {
    @FXML
    private TextField searchField;
    
    @FXML
    private TableView<Emprunt> empruntsTable;
    
    @FXML
    private TableColumn<Emprunt, Integer> idColumn;
    
    @FXML
    private TableColumn<Emprunt, Integer> idLivreColumn;
    
    @FXML
    private TableColumn<Emprunt, Integer> idAdherentColumn;
    
    @FXML
    private TableColumn<Emprunt, Date> dateEmpruntColumn;
    
    @FXML
    private TableColumn<Emprunt, Date> dateRetourPrevueColumn;
    
    @FXML
    private TableColumn<Emprunt, Date> dateRetourEffectifColumn;
    
    @FXML
    private TableColumn<Emprunt, Boolean> retourneColumn;
    
    // Champs pour l'emprunt
    @FXML
    private TextField idLivreField;
    
    @FXML
    private TextField idAdherentField;
    
    @FXML
    private DatePicker dateEmpruntPicker;
    
    @FXML
    private DatePicker dateRetourPicker;
    
    // Champs pour le retour
    @FXML
    private TextField idEmpruntField;
    
    private GestionnaireImpl gestionnaire;
    private ObservableList<Emprunt> empruntsList;
    
    @FXML
    public void initialize() {
        gestionnaire = new GestionnaireImpl();
        empruntsList = FXCollections.observableArrayList();
        
        // Configuration des colonnes
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idLivreColumn.setCellValueFactory(new PropertyValueFactory<>("livreId"));
        idAdherentColumn.setCellValueFactory(new PropertyValueFactory<>("adherentId"));
        dateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        dateRetourPrevueColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetourPrevue"));
        dateRetourEffectifColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetourEffective"));
        retourneColumn.setCellValueFactory(new PropertyValueFactory<>("retourne"));
        
        // Charger les emprunts en cours
        loadEmpruntsEnCours();
    }
    
    private void loadEmpruntsEnCours() {
        empruntsList.clear();
        empruntsList.addAll(gestionnaire.listerEmpruntsEnCours());
        empruntsTable.setItems(empruntsList);
    }
    
    @FXML
    private void handleSearch() {
        try {
            int id = Integer.parseInt(searchField.getText());
            empruntsList.clear();
            Emprunt emprunt = gestionnaire.getEmpruntParId(id);
            if (emprunt != null) {
                empruntsList.add(emprunt);
            }
        } catch (NumberFormatException e) {
            showError("L'ID doit être un nombre valide");
        }
    }
    
    @FXML
    private void handleEmprunt() {
        try {
            int idLivre = Integer.parseInt(idLivreField.getText());
            int idAdherent = Integer.parseInt(idAdherentField.getText());
            LocalDate dateEmprunt = dateEmpruntPicker.getValue();
            LocalDate dateRetour = dateRetourPicker.getValue();
            
            if (dateEmprunt == null || dateRetour == null) {
                showError("Veuillez sélectionner les dates");
                return;
            }
            
            Emprunt emprunt = new Emprunt();
            emprunt.setLivreId(idLivre);
            emprunt.setAdherentId(idAdherent);
            emprunt.setDateEmprunt(Date.from(dateEmprunt.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            emprunt.setDateRetourPrevue(Date.from(dateRetour.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            
            if (gestionnaire.enregistrerEmprunt(emprunt)) {
                // Réinitialiser les champs
                idLivreField.clear();
                idAdherentField.clear();
                dateEmpruntPicker.setValue(null);
                dateRetourPicker.setValue(null);
                
                // Recharger la liste
                loadEmpruntsEnCours();
            } else {
                showError("Erreur lors de l'enregistrement de l'emprunt");
            }
            
        } catch (NumberFormatException e) {
            showError("Les IDs doivent être des nombres valides");
        }
    }
    
    @FXML
    private void handleRetour() {
        try {
            int idEmprunt = Integer.parseInt(idEmpruntField.getText());
            if (gestionnaire.enregistrerRetour(idEmprunt, new Date())) {
                // Réinitialiser le champ
                idEmpruntField.clear();
                
                // Recharger la liste
                loadEmpruntsEnCours();
            } else {
                showError("Erreur lors de l'enregistrement du retour");
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
        return adherentComboBox.getScene();
    }
} 