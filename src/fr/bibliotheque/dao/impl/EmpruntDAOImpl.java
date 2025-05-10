package fr.bibliotheque.dao.impl;

import fr.bibliotheque.dao.EmpruntDAO;
import fr.bibliotheque.dao.LivreDAO;
import fr.bibliotheque.metier.Emprunt;
import fr.bibliotheque.metier.Livre;
import fr.bibliotheque.util.BaseDeDonnees;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmpruntDAOImpl implements EmpruntDAO {
    private Connection connexion;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private LivreDAO livreDAO;

    public EmpruntDAOImpl() {
        connexion = BaseDeDonnees.getInstance().getConnexion();
        livreDAO = new LivreDAOImpl();
    }

    @Override
    public boolean ajouter(Emprunt emprunt) {
        // Vérifier que le livre est disponible
        Livre livre = livreDAO.trouverParId(emprunt.getLivreId());
        if (livre == null || !livre.estDisponible()) {
            return false;
        }

        String sql = "INSERT INTO Emprunt (livre_id, adherent_id, date_emprunt, date_retour_prevue, retourne) VALUES (?, ?, ?, ?, 0)";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, emprunt.getLivreId());
            pstmt.setInt(2, emprunt.getAdherentId());
            pstmt.setString(3, emprunt.dateEmpruntToString());
            pstmt.setString(4, emprunt.dateRetourPrevueToString());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        emprunt.setId(generatedKeys.getInt(1));
                    }
                }

                // Mettre à jour la disponibilité du livre
                livre.setDisponible(false);
                livreDAO.modifier(livre);

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean enregistrerRetour(int id, Date dateRetour) {
        Emprunt emprunt = trouverParId(id);
        if (emprunt == null || emprunt.isRetourne()) {
            return false;
        }

        String sql = "UPDATE Emprunt SET date_retour_effective = ?, retourne = 1 WHERE id = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, sdf.format(dateRetour));
            pstmt.setInt(2, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Mettre à jour la disponibilité du livre
                Livre livre = livreDAO.trouverParId(emprunt.getLivreId());
                if (livre != null) {
                    livre.setDisponible(true);
                    livreDAO.modifier(livre);
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Emprunt trouverParId(int id) {
        String sql = "SELECT * FROM Emprunt WHERE id = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Emprunt emprunt = new Emprunt();
                emprunt.setId(rs.getInt("id"));
                emprunt.setLivreId(rs.getInt("livre_id"));
                emprunt.setAdherentId(rs.getInt("adherent_id"));
                emprunt.setDateEmprunt(rs.getString("date_emprunt"));
                emprunt.setDateRetourPrevue(rs.getString("date_retour_prevue"));

                String dateRetourEffective = rs.getString("date_retour_effective");
                if (dateRetourEffective != null) {
                    emprunt.setDateRetourEffective(dateRetourEffective);
                }

                emprunt.setRetourne(rs.getBoolean("retourne"));

                return emprunt;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Emprunt> listerEmpruntsEnCours() {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT * FROM Emprunt WHERE retourne = 0";

        try (Statement stmt = connexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Emprunt emprunt = new Emprunt();
                emprunt.setId(rs.getInt("id"));
                emprunt.setLivreId(rs.getInt("livre_id"));
                emprunt.setAdherentId(rs.getInt("adherent_id"));
                emprunt.setDateEmprunt(rs.getString("date_emprunt"));
                emprunt.setDateRetourPrevue(rs.getString("date_retour_prevue"));
                emprunt.setRetourne(rs.getBoolean("retourne"));

                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emprunts;
    }

    @Override
    public List<Emprunt> listerTous() {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT * FROM Emprunt";

        try (Statement stmt = connexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Emprunt emprunt = new Emprunt();
                emprunt.setId(rs.getInt("id"));
                emprunt.setLivreId(rs.getInt("livre_id"));
                emprunt.setAdherentId(rs.getInt("adherent_id"));
                emprunt.setDateEmprunt(rs.getString("date_emprunt"));
                emprunt.setDateRetourPrevue(rs.getString("date_retour_prevue"));

                String dateRetourEffective = rs.getString("date_retour_effective");
                if (dateRetourEffective != null) {
                    emprunt.setDateRetourEffective(dateRetourEffective);
                }

                emprunt.setRetourne(rs.getBoolean("retourne"));

                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emprunts;
    }

    @Override
    public List<Emprunt> listerParAdherent(int adherentId) {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT * FROM Emprunt WHERE adherent_id = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, adherentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Emprunt emprunt = new Emprunt();
                emprunt.setId(rs.getInt("id"));
                emprunt.setLivreId(rs.getInt("livre_id"));
                emprunt.setAdherentId(rs.getInt("adherent_id"));
                emprunt.setDateEmprunt(rs.getString("date_emprunt"));
                emprunt.setDateRetourPrevue(rs.getString("date_retour_prevue"));

                String dateRetourEffective = rs.getString("date_retour_effective");
                if (dateRetourEffective != null) {
                    emprunt.setDateRetourEffective(dateRetourEffective);
                }

                emprunt.setRetourne(rs.getBoolean("retourne"));

                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emprunts;
    }

    @Override
    public List<Emprunt> listerParLivre(int livreId) {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT * FROM Emprunt WHERE livre_id = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, livreId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Emprunt emprunt = new Emprunt();
                emprunt.setId(rs.getInt("id"));
                emprunt.setLivreId(rs.getInt("livre_id"));
                emprunt.setAdherentId(rs.getInt("adherent_id"));
                emprunt.setDateEmprunt(rs.getString("date_emprunt"));
                emprunt.setDateRetourPrevue(rs.getString("date_retour_prevue"));

                String dateRetourEffective = rs.getString("date_retour_effective");
                if (dateRetourEffective != null) {
                    emprunt.setDateRetourEffective(dateRetourEffective);
                }

                emprunt.setRetourne(rs.getBoolean("retourne"));

                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emprunts;
    }
}
