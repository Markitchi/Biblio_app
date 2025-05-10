package fr.bibliotheque.dao;
import fr.bibliotheque.metier.Administrateur;
import java.util.List;

public interface AdministrateurDAO {
    boolean ajouter(Administrateur administrateur);
    boolean supprimer(int id);
    boolean modifier(Administrateur administrateur);
    Administrateur trouverParId(int id);
    Administrateur authentifier(String login, String motDePasse);
    List<Administrateur> listerTous();
}
