package fr.bibliotheque.service;

import fr.bibliotheque.metier.Emprunt;
import java.util.Date;
import java.util.List;

public interface IGestionEmprunt {
    boolean enregistrerEmprunt(Emprunt emprunt);
    boolean enregistrerRetour(int empruntId, Date dateRetour);
    List<Emprunt> listerEmpruntsEnCours();
    List<Emprunt> listerHistoriqueEmprunts();
    List<Emprunt> listerEmpruntParAdherent(int adherentId);
    List<Emprunt> listerEmpruntParLivre(int livreId);
    Emprunt getEmpruntParId(int id);
}
