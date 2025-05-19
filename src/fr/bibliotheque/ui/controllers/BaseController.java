package fr.bibliotheque.ui.controllers;

import fr.bibliotheque.metier.Admin;

public abstract class BaseController {
    protected Admin currentAdmin;
    
    public void setAdmin(Admin admin) {
        this.currentAdmin = admin;
    }
    
    protected void showError(String message) {
        // Cette méthode sera implémentée dans les contrôleurs spécifiques
    }
} 