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
    public void handleAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/bibliotheque/ui/vues/Dash.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) accueilButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleGestionLivres() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/bibliotheque/ui/vues/GestionLivre.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) gestionLivresButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleGestionAdherents() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/bibliotheque/ui/vues/GestionAdherents.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) gestionAdherentsButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleGestionEmprunts() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/bibliotheque/ui/vues/GestionEmprunts.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) gestionEmpruntsButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 