package fr.bibliotheque.dao;

import fr.bibliotheque.metier.Adherent;
import java.util.List;

public interface AdherentDAO {
    boolean ajouter(Adherent adherent);
    boolean supprimer(int id);
    boolean modifier(Adherent adherent);
    Adherent trouverParId(int id);
    List<Adherent> rechercher(String critere);
    List<Adherent> listerTous();
}
