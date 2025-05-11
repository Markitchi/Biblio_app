package fr.bibliotheque.ui.controllers;

import fr.bibliotheque.dao.DAOFactory;
import fr.bibliotheque.metier.Administrateur;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    
    @FXML
    private Node loginCard; // Correspond à la VBox avec styleClass="login-card"
    
    private DAOFactory daoFactory;
    
    public LoginController() {
        this.daoFactory = DAOFactory.getInstance();
    }
    
    @FXML
    private void initialize() {
        // Charger le logo
        try {
            Image logo = new Image(getClass().getResource("/fr/bibliotheque/ui/assets/library_logo.jpg").toExternalForm());
            logoImage.setImage(logo);
        } catch (Exception e) {
            // Image not found, do nothing
        }

        // Animation d'apparition de la carte de connexion
        if (loginCard == null && logoImage != null) {
            // Fallback: essayer de trouver la carte par le parent du logo
            loginCard = logoImage.getParent();
        }
        if (loginCard != null) {
            FadeTransition fade = new FadeTransition(Duration.millis(900), loginCard);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();

            ScaleTransition scale = new ScaleTransition(Duration.millis(900), loginCard);
            scale.setFromX(0.92);
            scale.setFromY(0.92);
            scale.setToX(1);
            scale.setToY(1);
            scale.play();
        }
        // Animation du logo
        if (logoImage != null) {
            FadeTransition fadeLogo = new FadeTransition(Duration.millis(1200), logoImage);
            fadeLogo.setFromValue(0);
            fadeLogo.setToValue(1);
            fadeLogo.play();

            ScaleTransition scaleLogo = new ScaleTransition(Duration.millis(1200), logoImage);
            scaleLogo.setFromX(0.7);
            scaleLogo.setFromY(0.7);
            scaleLogo.setToX(1);
            scaleLogo.setToY(1);
            scaleLogo.play();
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
