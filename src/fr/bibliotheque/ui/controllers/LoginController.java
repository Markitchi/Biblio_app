package fr.bibliotheque.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import fr.bibliotheque.service.GestionnaireImpl;
import fr.bibliotheque.metier.Administrateur;

public class LoginController {
    @FXML
    private TextField emailField;
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
    public void handleLogin() {
        String login = emailField.getText();
        String motDePasse = passwordField.getText();

        if (login.isEmpty() || motDePasse.isEmpty()) {
            showError("Veuillez remplir tous les champs");
            return;
        }

        Administrateur admin = gestionnaire.authentifier(login, motDePasse);
        if (admin != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/bibliotheque/ui/vues/Dash.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                showError("Erreur lors du chargement de la vue: " + e.getMessage());
            }
        } else {
            showError("Identifiants incorrects");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
} 