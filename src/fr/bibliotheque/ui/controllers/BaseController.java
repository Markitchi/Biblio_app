package fr.bibliotheque.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import fr.bibliotheque.metier.Administrateur;
import java.util.Stack;

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
            // Sauvegarder la scène actuelle dans l'historique
            Scene currentScene = getScene();
            if (currentScene != null) {
                navigationHistory.push(currentScene);
            }

            // Charger la nouvelle vue
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            
            // Passer l'admin aux autres contrôleurs si nécessaire
            if (loader.getController() instanceof BaseController) {
                ((BaseController) loader.getController()).setAdmin(currentAdmin);
            }
            
            Stage stage = (Stage) getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
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