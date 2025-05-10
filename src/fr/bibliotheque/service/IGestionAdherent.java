package fr.bibliotheque.service;
import fr.bibliotheque.metier.Adherent;
import java.util.List;
public interface IGestionAdherent {
    boolean ajouterAdherent(Adherent adherent);
    boolean supprimerAdherent(int id);
    boolean modifierAdherent(Adherent adherent);
    List<Adherent> rechercherAdherent(String critere);
    List<Adherent> listerTousAdherents();
    Adherent getAdherentParId(int id);
}


