package fr.bibliotheque.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import fr.bibliotheque.metier.Administrateur;
import java.util.Stack;
import java.io.IOException;

public abstract class BaseController {
    protected Administrateur currentAdmin;
    private static Stack<Scene> navigationHistory = new Stack<>();
    
    public void setAdmin(Administrateur admin) {
        this.currentAdmin = admin;
    }
    
    @FXML
    protected void handleAccueil() {
        loadView("/fr/bibliotheque/ui/vues/Dash.fxml");
    }

    @FXML
    protected void handleGestionLivres() {
        loadView("/fr/bibliotheque/ui/vues/GestionLivre.fxml");
    }

    @FXML
    protected void handleGestionAdherents() {
        loadView("/fr/bibliotheque/ui/vues/GestionAdherents.fxml");
    }

    @FXML
    protected void handleGestionEmprunts() {
        loadView("/fr/bibliotheque/ui/vues/GestionEmprunts.fxml");
    }

    @FXML
    protected void handleRetour() {
        if (!navigationHistory.isEmpty()) {
            Scene previousScene = navigationHistory.pop();
            Stage stage = (Stage) getScene().getWindow();
            stage.setScene(previousScene);
        }
    }

    protected void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = getScene();
            if (scene != null) {
                scene.setRoot(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Scene getScene() {
        return null; // À surcharger dans les classes filles
    }

    protected void showError(String message) {
        // Cette méthode sera implémentée dans les contrôleurs spécifiques
    }
} 