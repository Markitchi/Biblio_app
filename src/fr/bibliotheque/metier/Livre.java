package fr.bibliotheque.metier;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private String isbn;
    private int anneePublication;
    private boolean disponible;

    //Constructeur par defaut
    public Livre(){
        this.disponible = true;
    }

    // Constructeur avec Paramètres
    public Livre(String titre, String auteur, String isbn, int anneePublication){
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
        this.anneePublication = anneePublication;
        this.disponible = true;
    }

    //Constructeur avec ID.
    public Livre(int id, String titre, String auteur, String isbn, int anneePublication, boolean disponible){
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
        this.anneePublication = anneePublication;
        this.disponible = disponible;
    }

    //Getters et Setters
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    public boolean estDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}