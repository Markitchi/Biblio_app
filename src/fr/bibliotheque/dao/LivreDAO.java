package fr.bibliotheque.dao;

import fr.bibliotheque.metier.Livre;
import java.util.List;
public interface LivreDAO {
    boolean ajouter(Livre livre);
    boolean supprimer(int id);
    boolean modifier(Livre livre);
    Livre trouverParId(int id);
    List<Livre> rechercher(String critere);
    List<Livre> listerTous();
}
