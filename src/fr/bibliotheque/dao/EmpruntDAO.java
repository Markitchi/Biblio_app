package fr.bibliotheque.dao;

import fr.bibliotheque.metier.Emprunt;

import java.util.Date;
import java.util.List;

public interface EmpruntDAO {
    boolean ajouter(Emprunt emprunt);
    boolean enregistrerRetour(int id, Date dateRetour);
    Emprunt trouverParId(int id);
    List<Emprunt> listerEmpruntsEnCours();
    List<Emprunt> listerTous();
    List<Emprunt> listerParAdherent(int adherentId);
    List<Emprunt> listerParLivre(int livreId);
}
