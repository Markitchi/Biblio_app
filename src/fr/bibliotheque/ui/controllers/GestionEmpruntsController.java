package fr.bibliotheque.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import fr.bibliotheque.metier.Emprunt;
import fr.bibliotheque.service.EmpruntService;
import java.time.LocalDate;

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
    private TableColumn<Emprunt, LocalDate> dateEmpruntColumn;
    
    @FXML
    private TableColumn<Emprunt, LocalDate> dateRetourPrevueColumn;
    
    @FXML
    private TableColumn<Emprunt, LocalDate> dateRetourEffectifColumn;
    
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
    
    private EmpruntService empruntService;
    private ObservableList<Emprunt> empruntsList;
    
    @FXML
    public void initialize() {
        empruntService = new EmpruntService();
        empruntsList = FXCollections.observableArrayList();
        
        // Configuration des colonnes
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idLivreColumn.setCellValueFactory(new PropertyValueFactory<>("idLivre"));
        idAdherentColumn.setCellValueFactory(new PropertyValueFactory<>("idAdherent"));
        dateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        dateRetourPrevueColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetourPrevue"));
        dateRetourEffectifColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetourEffectif"));
        retourneColumn.setCellValueFactory(new PropertyValueFactory<>("retourne"));
        
        // Charger les emprunts en cours
        loadEmpruntsEnCours();
    }
    
    private void loadEmpruntsEnCours() {
        empruntsList.clear();
        empruntsList.addAll(empruntService.getEmpruntsEnCours());
        empruntsTable.setItems(empruntsList);
    }
    
    @FXML
    private void handleSearch() {
        try {
            int id = Integer.parseInt(searchField.getText());
            empruntsList.clear();
            Emprunt emprunt = empruntService.getEmpruntById(id);
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
            
            Emprunt emprunt = new Emprunt(idLivre, idAdherent, dateEmprunt, dateRetour);
            empruntService.addEmprunt(emprunt);
            
            // Réinitialiser les champs
            idLivreField.clear();
            idAdherentField.clear();
            dateEmpruntPicker.setValue(null);
            dateRetourPicker.setValue(null);
            
            // Recharger la liste
            loadEmpruntsEnCours();
            
        } catch (NumberFormatException e) {
            showError("Les IDs doivent être des nombres valides");
        }
    }
    
    @FXML
    private void handleRetour() {
        try {
            int idEmprunt = Integer.parseInt(idEmpruntField.getText());
            empruntService.retournerLivre(idEmprunt);
            
            // Réinitialiser le champ
            idEmpruntField.clear();
            
            // Recharger la liste
            loadEmpruntsEnCours();
            
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