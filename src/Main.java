import fr.bibliotheque.service.GestionnaireImpl;

public class Main {
    public static void main(String[] args) {
        // Initialisation du gestionnaire (connexion à la base de données)
        GestionnaireImpl gestionnaire = new GestionnaireImpl();

        // Initialisation de la base de données (création des tables si besoin)
        gestionnaire.initierBaseDeDonnees();
        System.out.println("Base de données initialisée.");

        // Fermeture de la connexion à la base de données à la fin du programme
        fr.bibliotheque.util.BaseDeDonnees.getInstance().fermerConnexion();
        System.out.println("Connexion à la base de données fermée.");
    }
}