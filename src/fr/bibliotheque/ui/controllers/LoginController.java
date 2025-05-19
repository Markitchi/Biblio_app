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
import fr.bibliotheque.service.AdminService;
import fr.bibliotheque.metier.Admin;

public class LoginController {
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Label errorLabel;
    
    private AdminService adminService;
    
    @FXML
    public void initialize() {
        adminService = new AdminService();
    }
    
    @FXML
    private void handleLogin() {
        String login = emailField.getText();
        String password = passwordField.getText();
        
        if (login.isEmpty() || password.isEmpty()) {
            showError("Veuillez remplir tous les champs");
            return;
        }
        
        Admin admin = adminService.authentifier(login, password);
        if (admin != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/bibliotheque/ui/vues/Dash.fxml"));
                Parent root = loader.load();
                DashController dashController = loader.getController();
                dashController.setAdmin(admin);
                
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                showError("Erreur lors du chargement de la page d'accueil");
                e.printStackTrace();
            }
        } else {
            showError("Login ou mot de passe incorrect");
        }
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
} 