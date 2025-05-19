package fr.bibliotheque.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DashController {
    @FXML
    private Button accueilButton;
    @FXML
    private Button gestionLivresButton;
    @FXML
    private Button gestionAdherentsButton;
    @FXML
    private Button gestionEmpruntsButton;

    @FXML
    public void initialize() {
        // Initialisation des boutons
        accueilButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/Dash.fxml"));
        gestionLivresButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/GestionLivre.fxml"));
        gestionAdherentsButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/GestionAdherents.fxml"));
        gestionEmpruntsButton.setOnAction(e -> handleNavigation("/fr/bibliotheque/ui/vues/GestionEmprunts.fxml"));
    }

    private void handleNavigation(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) accueilButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 