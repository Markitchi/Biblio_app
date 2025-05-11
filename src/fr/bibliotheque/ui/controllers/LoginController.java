package fr.bibliotheque.ui.controllers;

import fr.bibliotheque.dao.DAOFactory;
import fr.bibliotheque.metier.Administrateur;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Label errorLabel;

    @FXML
    private ImageView logoImage;
    
    private DAOFactory daoFactory;
    
    public LoginController() {
        this.daoFactory = DAOFactory.getInstance();
    }
    
    @FXML
    private void initialize() {
        // Charger le logo depuis les ressources
        try {
            Image logo = new Image(getClass().getResource("/fr/bibliotheque/ui/assets/library_logo.jpg").toExternalForm());
            logoImage.setImage(logo);
        } catch (Exception e) {
            // Si l'image n'est pas trouvée, ne rien faire
        }
    }
    
    @FXML
    private void handleLogin() {
        String login = emailField.getText().trim();
        String motDePasse = passwordField.getText().trim();
        
        // Validation basique
        if (login.isEmpty() || motDePasse.isEmpty()) {
            showError("Veuillez remplir tous les champs");
            return;
        }
        
        // Authentification via le DAO
        Administrateur admin = daoFactory.getAdministrateurDAO().authentifier(login, motDePasse);
        
        if (admin != null) {
            // Fermer la fenêtre de connexion
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            
            // TODO: Ouvrir la fenêtre principale de l'application
            // Vous devrez implémenter l'ouverture de la fenêtre principale ici
        } else {
            showError("Login ou mot de passe incorrect");
        }
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
