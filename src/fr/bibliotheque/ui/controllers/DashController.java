package fr.bibliotheque.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import fr.bibliotheque.metier.Administrateur;

public class DashController extends BaseController {
    @FXML
    private Text welcomeText;
    
    @FXML
    private Button gestionLivresBtn;
    
    @FXML
    private Button gestionAdherentsBtn;
    
    @FXML
    private Button gestionEmpruntsBtn;
    
    @FXML
    public void initialize() {
        // Les actions sont maintenant gérées par les méthodes héritées de BaseController
    }
    
    @Override
    public void setAdmin(Administrateur admin) {
        super.setAdmin(admin);
        welcomeText.setText("Bonjour, " + admin.getLogin());
    }

    @Override
    protected Scene getScene() {
        return welcomeText.getScene();
    }
} 