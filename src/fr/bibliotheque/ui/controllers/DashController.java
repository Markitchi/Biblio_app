package fr.bibliotheque.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import fr.bibliotheque.metier.Administrateur;

public class DashController {
    @FXML
    private Text welcomeText;
    
    @FXML
    private Button gestionLivresBtn;
    
    @FXML
    private Button gestionAdherentsBtn;
    
    @FXML
    private Button gestionEmpruntsBtn;
    
    private Administrateur currentAdmin;
    
    @FXML
    public void initialize() {
        setupButtonActions();
    }
    
    public void setAdmin(Administrateur admin) {
        this.currentAdmin = admin;
        welcomeText.setText("Bonjour, " + admin.getLogin());
    }
    
    @FXML
    private void handleAccueil() {
        // Recharger la vue du tableau de bord
        loadView("/fr/bibliotheque/ui/vues/Dash.fxml");
    }
    
    @FXML
    private void handleGestionLivres() {
        loadView("/fr/bibliotheque/ui/vues/GestionLivre.fxml");
    }
    
    @FXML
    private void handleGestionAdherents() {
        loadView("/fr/bibliotheque/ui/vues/GestionAdherents.fxml");
    }
    
    @FXML
    private void handleGestionEmprunts() {
        loadView("/fr/bibliotheque/ui/vues/GestionEmprunts.fxml");
    }
    
    private void setupButtonActions() {
        // Les actions sont maintenant gérées par les méthodes @FXML
    }
    
    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            
            // Passer l'admin aux autres contrôleurs si nécessaire
            if (loader.getController() instanceof BaseController) {
                ((BaseController) loader.getController()).setAdmin(currentAdmin);
            }
            
            Stage stage = (Stage) welcomeText.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 