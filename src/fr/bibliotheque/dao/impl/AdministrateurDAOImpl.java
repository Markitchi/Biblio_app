package fr.bibliotheque.dao.impl;

import fr.bibliotheque.dao.AdministrateurDAO;
import fr.bibliotheque.metier.Administrateur;
import fr.bibliotheque.util.BaseDeDonnees;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AdministrateurDAOImpl implements AdministrateurDAO{
    private Connection connexion;
    public AdministrateurDAOImpl(){
        connexion = BaseDeDonnees.getInstance().getConnexion();
    };
    @Override
    public boolean ajouter(Administrateur administrateur){
        String sql = "INSERT INTO Administrateur(nom, prenom, login, mot_de_passe) VALUES (?,?,?,?)";
        try(PreparedStatement pstmt = connexion.prepareStatement(sql)){
            pstmt.setString(1,administrateur.getNom());
            pstmt.setString(2, administrateur.getPrenom());
            pstmt.setString(3, administrateur.getLogin());
            pstmt.setString(4, administrateur.getMotDePasse());

            int affectedRows = pstmt.executeUpdate();

            if(affectedRows >0){
                try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                    if (generatedKeys.next()){
                        administrateur.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean supprimer(int id){
        String sql = "DELETE FROM Administrateur WHERE id = ?";

        try(PreparedStatement pstmt = connexion.prepareStatement(sql)){
            pstmt.setInt(1,id);
            return pstmt.executeUpdate()>0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean modifier(Administrateur administrateur){
        String sql = "UPDATE Administrateur SET id = ?, nom = ?, prenom = ?, login=?, mot_de_passe = ?";

        try(PreparedStatement pstmt = connexion.prepareStatement(sql)){
            pstmt.setInt(1, administrateur.getId());
            pstmt.setString(2, administrateur.getNom());
            pstmt.setString(3, administrateur.getPrenom());
            pstmt.setString(4, administrateur.getLogin());
            pstmt.setString(5, administrateur.getMotDePasse());

            return pstmt.executeUpdate()>0;
        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Administrateur trouverParId(int id){
        String sql = "SELECT * FROM Administrateur WHERE id = ?";

        try(PreparedStatement pstmt = connexion.prepareStatement(sql)){
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                Administrateur admin = new Administrateur();
                admin.setId(rs.getInt("id"));
                admin.setNom(rs.getString("nom"));
                admin.setPrenom(rs.getString("prenom"));
                admin.setLogin(rs.getString("login"));
                admin.setMotDePasse(rs.getString("mot_de_passe"));
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Administrateur authentifier(String login, String motDePasse){
        String sql = "SELECT * FROM Administrateur WHERE login = ? AND mot_de_passe = ?";

        try(PreparedStatement pstmt = connexion.prepareStatement(sql)){
            pstmt.setString(1, login);
            pstmt.setString(1, motDePasse);

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
        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Administrateur> listerTous(){
        List<Administrateur> administrateurs = new ArrayList<>();
        String sql ="SELECT * FROM Administrateur";

        try(Statement stmt = connexion.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                Administrateur admin = new Administrateur();
                admin.setId(rs.getInt("id"));
                admin.setNom(rs.getString("nom"));
                admin.setPrenom(rs.getString("prenom"));
                admin.setLogin(rs.getString("login"));
                admin.setMotDePasse(rs.getString("mot_de_passe"));

                administrateurs.add(admin);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return administrateurs;
    }
}
