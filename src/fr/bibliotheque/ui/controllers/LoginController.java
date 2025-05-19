package fr.bibliotheque.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import fr.bibliotheque.metier.Administrateur;
import fr.bibliotheque.service.GestionnaireImpl;

public class LoginController {
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Label errorLabel;
    
    private GestionnaireImpl gestionnaire;
    
    @FXML
    public void initialize() {
        gestionnaire = new GestionnaireImpl();
    }
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Veuillez remplir tous les champs");
            return;
        }
        
        try {
            Administrateur admin = gestionnaire.authentifier(username, password);
            if (admin != null) {
                // Charger le tableau de bord
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/bibliotheque/ui/vues/Dash.fxml"));
                Parent root = loader.load();
                
                // Obtenir le contrôleur et définir l'administrateur
                DashController dashController = loader.getController();
                dashController.setAdmin(admin);
                
                // Afficher la nouvelle scène
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                errorLabel.setText("Identifiants incorrects");
            }
        } catch (Exception e) {
            errorLabel.setText("Erreur lors de la connexion");
            e.printStackTrace();
        }
    }
} 