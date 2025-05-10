package fr.bibliotheque.metier;

public class Adherent {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;

        // Constructeur par defaut
    public Adherent(){

    }

    //Constructeur avec paramètres
    public Adherent(String nom, String prenom, String adresse, String telephone, String email){
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
    }

    //Constructeur avec ID
    public Adherent(int id, String nom, String prenom, String adresse, String telephone, String email){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
    }

    //Getters et Setters

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNom(){
        return nom;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Adherent [id=" + id + ", nom=" + nom + ", prenom=" + prenom +
                ", adresse=" + adresse + ", telephone=" + telephone + ", email=" + email + "]";
    }
}
