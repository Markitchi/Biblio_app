package fr.bibliotheque.service;

import fr.bibliotheque.metier.Livre;
import java.util.List;

public interface IGestionLivre {
    boolean ajouterLivre(Livre livre);
    boolean supprimerLivre(int id);
    boolean modifierLivre(Livre livre);
    List<Livre> rechercherLivre(String critere);
    List <Livre> listerTousLivres();
    Livre getLivreParId(int id);
}
