package fr.bibliotheque.service;

import fr.bibliotheque.metier.Livre;
import fr.bibliotheque.metier.Adherent;
import fr.bibliotheque.metier.Emprunt;
import fr.bibliotheque.metier.Administrateur;
import fr.bibliotheque.util.BaseDeDonnees;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestionnaireImpl implements IGestionLivre, IGestionAdherent, IGestionEmprunt,IGestionAdministrateur {
    private Connection connexion;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public GestionnaireImpl() {
        connexion = BaseDeDonnees.getInstance().getConnexion();
    }

    //Methode pour initialiser la base de données
    public void initierBaseDeDonnees() {
        BaseDeDonnees.getInstance().initialiserBaseDeDonnees();
    }

    //Implementation de IGestionLivre
    @Override
    public boolean ajouterLivre(Livre livre) {
        String sql = "INSERT INTO Livre (titre, auteur, isbn, annee_publication,disponible) VALUES(?,?,?,?,?)";

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
    public boolean supprimerLivre(int id) {
        String sql = "DELETE FROM Livre WHERE id=?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public boolean modifierLivre(Livre livre) {
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
            return false;
        }
    }

    @Override
    public List<Livre> rechercherLivre(String critere) {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM Livre WHERE titre LIKE ? OR isbn LIKE ?";
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
                livre.setAnneePublication(rs.getInt("annnee_publication"));
                livre.setDisponible(rs.getBoolean("disponible"));

                livres.add(livre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livres;
    }

    @Override
    public List<Livre> listerTousLivres() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM Livre";

        try (Statement stmt = connexion.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Livre livre = new Livre();
                livre.setId(rs.getInt("id"));
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setIsbn(rs.getString("isbn"));
                livre.setAuteur(rs.getString("annee_publiccation"));
                livre.setDisponible(rs.getBoolean("disponible"));

                livres.add(livre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livres;

    }

    @Override
    public Livre getLivreParId(int id) {
        String sql = "SELECT * FROM Livre WHERE id= ?";

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

    //Implementation de IGestionAdherent

    @Override
    public boolean ajouterAdherent(Adherent adherent) {
        String sql = "INSERT INTO Adherent (nom, prenom, adresse, telephone,email) VALUES (?,?,?,?,?)";

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
    public boolean supprimerAdherent(int id) {
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
    public boolean modifierAdherent(Adherent adherent) {
        String sql = "UPDATE Adherent SET nom = ?, prenom = ?, adresse = ?, telephone = ?, email = ? WHERE id= ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, adherent.getNom());
            pstmt.setString(2, adherent.getPrenom());
            pstmt.setString(3, adherent.getTelephone());
            pstmt.setString(4, adherent.getAdresse());
            pstmt.setString(5, adherent.getEmail());
            pstmt.setInt(6, adherent.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }

    }

    @Override
    public List<Adherent> rechercherAdherent(String critere) {
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
    public List<Adherent> listerTousAdherents(){
        List<Adherent> adherents = new ArrayList<>();
        String sql = "SELECT * FROM Adherent";


        try(PreparedStatement pstmt = connexion.prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Adherent adherent = new Adherent();
                adherent.setId(rs.getInt("id"));
                adherent.setNom(rs.getString("nom"));
                adherent.setPrenom(rs.getString("prenom"));
                adherent.setAdresse(rs.getString("adresse"));
                adherent.setTelephone(rs.getString("telephone"));
                adherent.setEmail(rs.getString("email"));

                adherents.add(adherent);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

    return adherents;
    }

    @Override
    public Adherent getAdherentParId(int id){
        String sql = "SELECT * FROM Adherent WHERE id=?";

        try(PreparedStatement pstmt = connexion.prepareStatement(sql)){
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                Adherent adherent = new Adherent();
                adherent.setId(rs.getInt("id"));
                adherent.setNom(rs.getString("nom"));
                adherent.setPrenom(rs.getString("prenom"));
                adherent.setAdresse(rs.getString("adresse"));
                adherent.setTelephone(rs.getString("telephone"));
                adherent.setEmail(rs.getString("email"));

                return adherent;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }


    //Implementation de IGestionEmprunt
    @Override
    public boolean enregistrerEmprunt(Emprunt emprunt){
        //Verifier si le livre est disponible

        Livre livre = getLivreParId(emprunt.getLivreId());

        if(livre == null || !livre.estDisponible()){
            return false;
        }

        String sql = "INSERT INTO Emprunt (livre_id, adherent_id, date_emprunt, date_retour_prevue, retourne) VALUES (?, ?, ?, ?, 0)";

        try(PreparedStatement pstmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, emprunt.getLivreId());
            pstmt.setInt(2, emprunt.getAdherentId());
            pstmt.setString(3, emprunt.dateEmpruntToString());
            pstmt.setString(4, emprunt.dateRetourPrevueToString());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    emprunt.setId(generatedKeys.getInt(1));
                }
            }

            //Mettre a jour la disponibilite du livre

            livre.setDisponible(false);
            modifierLivre(livre);

            return true;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean enregistrerRetour(int empruntId, Date dateRetour){
        Emprunt emprunt = getEmpruntParId(empruntId);
        if(emprunt == null||emprunt.isRetourne()){
            return false;
        }

        String sql = "UPDATE Emprunt SET date_retour_effective = ?, retourne = 1 WHERE id = ?";

        try(PreparedStatement pstmt = connexion.prepareStatement(sql)){
            pstmt.setString(1,sdf.format(dateRetour));
            pstmt.setInt(2, empruntId);

            int affectedRows = pstmt.executeUpdate();

            if(affectedRows >0){
                //Mettre a jour la disponibilite du livre
                Livre livre = getLivreParId(emprunt.getLivreId());
                if(livre != null){
                    livre.setDisponible(true);
                    modifierLivre(livre);
                }
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Emprunt> listerEmpruntsEnCours(){

        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT * FROM Emprunt WHERE retourne = 0";

        try(Statement stmt = connexion.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()){
                    Emprunt emprunt = new Emprunt();
                    emprunt.setId(rs.getInt("id"));
                    emprunt.setLivreId(rs.getInt("livre_id"));
                    emprunt.setAdherentId(rs.getInt("adherent_id"));
                    emprunt.setDateEmprunt(rs.getString("date_emprunt"));
                    emprunt.setDateRetourPrevue(rs.getString("date_retour_prevue"));
                    emprunt.setRetourne(rs.getBoolean("retourne"));

                    emprunts.add(emprunt);
                }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return emprunts;

    }
    @Override
    public List<Emprunt> listerHistoriqueEmprunts(){
        List <Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT * FROM Emprunt";

        try(Statement stmt = connexion.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                Emprunt emprunt = new Emprunt();
                emprunt.setId(rs.getInt("id"));
                emprunt.setLivreId(rs.getInt("livre_id"));
                emprunt.setAdherentId(rs.getInt("adherent_id"));
                emprunt.setDateEmprunt(rs.getString("date_emprunt"));
                emprunt.setDateRetourPrevue(rs.getString("date_emprunt"));
                emprunt.setDateRetourPrevue(rs.getString("date_retour_prevue"));

                String dateRetourEffective = rs.getString("date_retour_effective");
                if(dateRetourEffective != null){
                    emprunt.setDateRetourEffective(dateRetourEffective);
                }

                emprunt.setRetourne(rs.getBoolean("retourne"));

                emprunts.add(emprunt);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return emprunts;
    }

    @Override
    public List<Emprunt> listerEmpruntParAdherent(int adherentId){
        List<Emprunt> emprunts = new ArrayList<>();

        String sql = "SELECT * FROM Emprunt WHERE adherent_id = ?";
        try(PreparedStatement pstmt = connexion.prepareStatement(sql)){
            pstmt.setInt(1, adherentId);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Emprunt emprunt =new Emprunt();
                emprunt.setId(rs.getInt("id"));
                emprunt.setLivreId(rs.getInt("livre_id"));
                emprunt.setAdherentId(rs.getInt("adherent_id"));
                emprunt.setDateEmprunt(rs.getString("date_emprunt"));
                emprunt.setDateRetourPrevue(rs.getString("date_retour_prevue"));

                String dateRetourEffective = rs.getString("date_retour_prevue");
                if(dateRetourEffective != null){
                    emprunt.setDateRetourEffective(dateRetourEffective);
                }

                emprunt.setRetourne(rs.getBoolean("retourne"));

                emprunts.add(emprunt);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return emprunts;
    }

    @Override
    public List<Emprunt> listerEmpruntParLivre (int livreId){
        List<Emprunt> emprunts = new ArrayList<>();

        String sql = "SELECT * FROM Emprunt WHERE livre_id = ?";

        try(PreparedStatement pstmt = connexion.prepareStatement(sql)){
            pstmt.setInt(1, livreId);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Emprunt emprunt = new Emprunt();

                emprunt.setId(rs.getInt("id"));
                emprunt.setLivreId(rs.getInt("livre_id"));
                emprunt.setAdherentId(rs.getInt("adherent_id"));
                emprunt.setDateEmprunt(rs.getString("date_emprunt"));
                emprunt.setDateRetourPrevue(rs.getString("date_retour_prevue"));

                String dateRetourEffective = rs.getString("dateRetourEffective");
                if(dateRetourEffective != null){
                    emprunt.setDateRetourEffective(dateRetourEffective);
                }

                emprunt.setRetourne(rs.getBoolean("retourne"));
                emprunts.add(emprunt);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return emprunts;
    }

    @Override
    public Emprunt getEmpruntParId(int Id){
        String sql = "SELECT * FROM Emprunt WHERE id = ?";

        try(PreparedStatement pstmt = connexion.prepareStatement(sql)){
            pstmt.setInt(1,Id);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                Emprunt emprunt = new Emprunt();
                emprunt.setId(rs.getInt("id"));
                emprunt.setLivreId(rs.getInt("livre_id"));
                emprunt.setAdherentId(rs.getInt("adherent_id"));
                emprunt.setDateEmprunt(rs.getString("date_emprunt"));
                emprunt.setDateRetourPrevue(rs.getString("date_retour_prevue"));

                String dateRetourEffective = rs.getString("date_retour_effective");
                if (dateRetourEffective != null){
                    emprunt.setDateRetourEffective(dateRetourEffective);
                }

                emprunt.setRetourne(rs.getBoolean("retourne"));

                return emprunt;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //Implementation de IGestionAdministrateur
    @Override
    public Administrateur authentifier(String login, String motDePasse){
        String sql = "SELECT * FROM Administrateur WHERE login = ? AND mot_de_passe=?";

        try(PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, login);
            pstmt.setString(2,motDePasse);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                Administrateur admin = new Administrateur();
                admin.setId(rs.getInt("id"));
                admin.setNom(rs.getString("nom"));
                admin.setPrenom(rs.getString("prenom"));
                admin.setLogin(rs.getString("login"));
                admin.setMotDePasse(rs.getString("mot_de_passe"));

                return admin;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public boolean modifierMotDePasse(int adminId, String nouveauMotDePasse){
        String sql = "UPDATE Administrateur SET mot_de_passe = ? WHERE id = ?";

        try(PreparedStatement pstmt = connexion.prepareStatement(sql)){
            pstmt.setString(1, nouveauMotDePasse);
            pstmt.setInt(2,adminId);

            return pstmt.executeUpdate() >0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
