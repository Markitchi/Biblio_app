package fr.bibliotheque.service;

import fr.bibliotheque.metier.Administrateur;

public interface IGestionAdministrateur {
    Administrateur authentifier(String login, String motDePasse);
    boolean modifierMotDePasse(int adminId, String nouveauMotDePasse);
}
