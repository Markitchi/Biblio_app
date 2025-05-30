package fr.bibliotheque.metier;

public class Administrateur {
    private int id;
    private String nom;
    private String prenom;
    private String login;
    private String motDePasse;

    //Constructeur par defaut
    public Administrateur(){

    }

    //Constructeur avec paramètres
    public Administrateur(String nom, String prenom,String login, String motDePasse){
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.motDePasse = motDePasse;
    }

    //Constructeur Complet
    public Administrateur(int id,String nom, String prenom,String login, String motDePasse){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.motDePasse = motDePasse;
    }
    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    // Méthode d'authentification
    public boolean authentifier(String login, String motDePasse) {
        return this.login.equals(login) && this.motDePasse.equals(motDePasse);
    }

    @Override
    public String toString() {
        return "Administrateur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", login=" + login + "]";
    }
}




