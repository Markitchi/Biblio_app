package fr.bibliotheque.dao.impl;

import fr.bibliotheque.dao.AdherentDAO;
import fr.bibliotheque.metier.Adherent;
import fr.bibliotheque.util.BaseDeDonnees;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdherentDAOImpl implements AdherentDAO {
    private Connection connexion;

    public AdherentDAOImpl() {
        connexion = BaseDeDonnees.getInstance().getConnexion();
    }

    @Override
    public boolean ajouter(Adherent adherent) {
        String sql = "INSERT INTO Adherent (nom, prenom, adresse, telephone, email) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, adherent.getNom());
            pstmt.setString(2, adherent.getPrenom());
            pstmt.setString(3, adherent.getAdresse());
            pstmt.setString(4, adherent.getTelephone());
            pstmt.setString(5, adherent.getEmail());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        adherent.setId(generatedKeys.getInt(1));
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
        String sql = "DELETE FROM Adherent WHERE id = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean modifier(Adherent adherent) {
        String sql = "UPDATE Adherent SET nom = ?, prenom = ?, adresse = ?, telephone = ?, email = ? WHERE id = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, adherent.getNom());
            pstmt.setString(2, adherent.getPrenom());
            pstmt.setString(3, adherent.getAdresse());
            pstmt.setString(4, adherent.getTelephone());
            pstmt.setString(5, adherent.getEmail());
            pstmt.setInt(6, adherent.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Adherent trouverParId(int id) {
        String sql = "SELECT * FROM Adherent WHERE id = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Adherent adherent = new Adherent();
                adherent.setId(rs.getInt("id"));
                adherent.setNom(rs.getString("nom"));
                adherent.setPrenom(rs.getString("prenom"));
                adherent.setAdresse(rs.getString("adresse"));
                adherent.setTelephone(rs.getString("telephone"));
                adherent.setEmail(rs.getString("email"));

                return adherent;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Adherent> rechercher(String critere) {
        List<Adherent> adherents = new ArrayList<>();
        String sql = "SELECT * FROM Adherent WHERE nom LIKE ? OR prenom LIKE ? OR email LIKE ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            String recherche = "%" + critere + "%";
            pstmt.setString(1, recherche);
            pstmt.setString(2, recherche);
            pstmt.setString(3, recherche);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Adherent adherent = new Adherent();
                adherent.setId(rs.getInt("id"));
                adherent.setNom(rs.getString("nom"));
                adherent.setPrenom(rs.getString("prenom"));
                adherent.setAdresse(rs.getString("adresse"));
                adherent.setTelephone(rs.getString("telephone"));
                adherent.setEmail(rs.getString("email"));

                adherents.add(adherent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adherents;
    }

    @Override
    public List<Adherent> listerTous() {
        List<Adherent> adherents = new ArrayList<>();
        String sql = "SELECT * FROM Adherent";

        try (Statement stmt = connexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Adherent adherent = new Adherent();
                adherent.setId(rs.getInt("id"));
                adherent.setNom(rs.getString("nom"));
                adherent.setPrenom(rs.getString("prenom"));
                adherent.setAdresse(rs.getString("adresse"));
                adherent.setTelephone(rs.getString("telephone"));
                adherent.setEmail(rs.getString("email"));

                adherents.add(adherent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adherents;
    }
}
