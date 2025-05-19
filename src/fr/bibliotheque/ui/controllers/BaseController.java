package fr.bibliotheque.ui.controllers;

import fr.bibliotheque.metier.Administrateur;

public abstract class BaseController {
    protected Administrateur currentAdmin;
    
    public void setAdmin(Administrateur admin) {
        this.currentAdmin = admin;
    }
    
    protected void showError(String message) {
        // Cette méthode sera implémentée dans les contrôleurs spécifiques
    }
} 