package fr.bibliotheque.metier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Emprunt {
    private int id;
    private int livreId;
    private int adherentId;
    private Date dateEmprunt;
    private Date dateRetourPrevue;
    private Date dateRetourEffective;
    private boolean retourne;

    //Format pour les dates
    private static final SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");

    //Constructeur par defaut
    public Emprunt(){
        this.dateEmprunt = new Date();
        this.retourne = false;
    }

    //Constructeur avec paramètres
    public Emprunt(int livreId, int adherentId, Date dateEmprunt, Date dateRetourPrevue){
        this.livreId = livreId;
        this.adherentId = adherentId;
        this.dateRetourPrevue = dateRetourPrevue;
        this.retourne = false;
    }

    //Constructeur complet
    public Emprunt(int id, int livreId, int adherentId, Date dateEmprunt, Date dateRetourPrevue, boolean retourne){
        this.id = id;
        this.livreId = livreId;
        this.adherentId = adherentId;
        this.dateRetourPrevue = dateRetourPrevue;
        this.retourne = retourne;
    }

    //Getters et Setters
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getLivreId() {
        return livreId;
    }

    public void setLivreId(int livreId) {
        this.livreId = livreId;
    }

    public int getAdherentId() {
        return adherentId;
    }

    public void setAdherentId(int adherentId) {
        this.adherentId = adherentId;
    }

    public Date getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(Date dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    //Conversion String -> Date
    public void setDateEmprunt(String dateEmpruntStr){
        try{
            this.dateEmprunt = sdf.parse(dateEmpruntStr);
        }catch(ParseException e){
            e.printStackTrace();
        }
    }

    public Date getDateRetourPrevue(){
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(Date dateRetourPrevue){
        this.dateRetourPrevue = dateRetourPrevue;
    }

    //Conversion String -> Date
    public void setDateRetourPrevue(String dateRetourPrevue){
        try{
            this.dateRetourPrevue = sdf.parse(dateRetourPrevue);
        }catch(ParseException e){
            e.printStackTrace();
        }
    }

    public Date getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setDateRetourEffective(Date dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    // Conversion String -> Date
    public void setDateRetourEffective(String dateRetourEffectiveStr) {
        try {
            this.dateRetourEffective = sdf.parse(dateRetourEffectiveStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean isRetourne() {
        return retourne;
    }

    public void setRetourne(boolean retourne){
        this.retourne = retourne;
    }

    public boolean estEnRetard() {
        if (retourne) {
            return dateRetourEffective != null && dateRetourEffective.after(dateRetourPrevue);
        } else {
            return new Date().after(dateRetourPrevue);
        }
    }

    public int dureeEmprunt() {
        Date finEmprunt = retourne ? dateRetourEffective : new Date();
        long diffInMillies = Math.abs(finEmprunt.getTime() - dateEmprunt.getTime());
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    // Méthodes utilitaires pour conversion date/string
    public String dateEmpruntToString() {
        return dateEmprunt != null ? sdf.format(dateEmprunt) : "";
    }

    public String dateRetourPrevueToString() {
        return dateRetourPrevue != null ? sdf.format(dateRetourPrevue) : "";
    }

    public String dateRetourEffectiveToString() {
        return dateRetourEffective != null ? sdf.format(dateRetourEffective) : "";
    }

    @Override
    public String toString() {
        return "Emprunt [id=" + id + ", livreId=" + livreId + ", adherentId=" + adherentId +
                ", dateEmprunt=" + dateEmpruntToString() + ", dateRetourPrevue=" + dateRetourPrevueToString() +
                ", dateRetourEffective=" + dateRetourEffectiveToString() + ", retourne=" + retourne + "]";
    }

}

