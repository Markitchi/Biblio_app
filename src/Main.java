import fr.bibliotheque.service.GestionnaireImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialisation du gestionnaire (connexion à la base de données)
        GestionnaireImpl gestionnaire = new GestionnaireImpl();
        gestionnaire.initierBaseDeDonnees();
        
        // Chargement de la vue de connexion
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/bibliotheque/ui/vues/Login.fxml"));
        Parent root = loader.load();
        
        // Configuration de la fenêtre principale
        primaryStage.setTitle("Gestion de Bibliothèque");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    @Override
    public void stop() {
        // Fermeture de la connexion à la base de données à la fin du programme
        fr.bibliotheque.util.BaseDeDonnees.getInstance().fermerConnexion();
        System.out.println("Connexion à la base de données fermée.");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}