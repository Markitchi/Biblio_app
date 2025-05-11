package fr.bibliotheque.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDeDonnees {
    private static BaseDeDonnees instance;
    private Connection connexion;
    private static final String DB_URL="jdbc:sqlite:src/db/bibliotheque.db";


    private BaseDeDonnees(){
        try{
          //Charger le driver SQlite
            Class.forName("org.sqlite.JDBC");

          //Creer la connexion
          connexion = DriverManager.getConnection(DB_URL);
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public static synchronized BaseDeDonnees getInstance(){
        if(instance == null){
          instance = new BaseDeDonnees();
        }
        return instance;
    }

    public Connection getConnexion() {
        return connexion;
    }

    public void initialiserBaseDeDonnees(){
        try(Statement stmt = connexion.createStatement()){
            // CREATION DES TABLES DE LA BASE DE DONNEE


            //Table Administrateur

            stmt.execute("CREATE TABLE IF NOT EXISTS Administrateur ("+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "nom TEXT NOT NULL,"+
                    "prenom TEXT NOT NULL,"+
                    "login TEXT NOT NULL UNIQUE,"+
                    "mot_de_passe TEXT NOT NULL"+
                    ")");

            //Inserer un administrateur par defaut si aucun n'existe

            stmt.execute("INSERT OR IGNORE INTO Administrateur (id, nom, prenom, login, mot_de_passe)"+
                    "SELECT 1, 'Admin', 'Admin', 'admin', 'admin'"+
                    "WHERE NOT EXISTS (SELECT 1 FROM Administrateur)");

            //Table Livre
            stmt.execute("CREATE TABLE IF NOT EXISTS Livre ("+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "titre TEXT NOT NULL,"+
                    "auteur TEXT NOT NULL,"+
                    "isbn TEXT UNIQUE,"+
                    "annee_publication INTEGER," +
                    "disponible BOOLEAN DEFAULT 1" +
                    ")");

            //Table Emprunt

            stmt.execute("CREATE TABLE IF NOT EXISTS Emprunt(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "livre_id INTEGER NOT NULL," +
                    "adherent_id INTEGER NOT NULL," +
                    "date_emprunt TEXT NOT NULL," +
                    "date_retour_prevue TEXT NOT NULL," +
                    "date_retour_effective TEXT," +
                    "retourne BOOLEAN DEFAULT 0," +
                    "FOREIGN KEY (livre_id) REFERENCES Livre(id)," +
                    "FOREIGN KEY (adherent_id) REFERENCES Adherent(id)" +
                    ")");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void fermerConnexion(){
        try{
            if (connexion != null && !connexion.isClosed()){
                connexion.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
