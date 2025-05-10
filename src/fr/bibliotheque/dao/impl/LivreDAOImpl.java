package fr.bibliotheque.dao.impl;


import fr.bibliotheque.dao.LivreDAO;
import fr.bibliotheque.metier.Livre;
import fr.bibliotheque.util.BaseDeDonnees;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAOImpl implements LivreDAO {
    private Connection connexion;

    public LivreDAOImpl() {
        connexion = BaseDeDonnees.getInstance().getConnexion();
    }

    @Override
    public boolean ajouter(Livre livre) {
        String sql = "INSERT INTO Livre (titre, auteur, isbn, annee_publication, disponible) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, livre.getTitre());
            pstmt.setString(2, livre.getAuteur());
            pstmt.setString(3, livre.getIsbn());
            pstmt.setInt(4, livre.getAnneePublication());
            pstmt.setBoolean(5, livre.estDisponible());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        livre.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean supprimer(int id) {
        String sql = "DELETE FROM Livre WHERE id = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean modifier(Livre livre) {
        String sql = "UPDATE Livre SET titre = ?, auteur = ?, isbn = ?, annee_publication = ?, disponible = ? WHERE id = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, livre.getTitre());
            pstmt.setString(2, livre.getAuteur());
            pstmt.setString(3, livre.getIsbn());
            pstmt.setInt(4, livre.getAnneePublication());
            pstmt.setBoolean(5, livre.estDisponible());
            pstmt.setInt(6, livre.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Livre trouverParId(int id) {
        String sql = "SELECT * FROM Livre WHERE id = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Livre livre = new Livre();
                livre.setId(rs.getInt("id"));
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setIsbn(rs.getString("isbn"));
                livre.setAnneePublication(rs.getInt("annee_publication"));
                livre.setDisponible(rs.getBoolean("disponible"));

                return livre;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Livre> rechercher(String critere) {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM Livre WHERE titre LIKE ? OR auteur LIKE ? OR isbn LIKE ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            String recherche = "%" + critere + "%";
            pstmt.setString(1, recherche);
            pstmt.setString(2, recherche);
            pstmt.setString(3, recherche);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Livre livre = new Livre();
                livre.setId(rs.getInt("id"));
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setIsbn(rs.getString("isbn"));
                livre.setAnneePublication(rs.getInt("annee_publication"));
                livre.setDisponible(rs.getBoolean("disponible"));

                livres.add(livre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livres;
    }

    @Override
    public List<Livre> listerTous() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM Livre";

        try (Statement stmt = connexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Livre livre = new Livre();
                livre.setId(rs.getInt("id"));
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setIsbn(rs.getString("isbn"));
                livre.setAnneePublication(rs.getInt("annee_publication"));
                livre.setDisponible(rs.getBoolean("disponible"));

                livres.add(livre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livres;
    }
}
