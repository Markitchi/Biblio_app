package fr.bibliotheque.dao;
import fr.bibliotheque.dao.impl.AdherentDAOImpl;
import fr.bibliotheque.dao.impl.AdministrateurDAOImpl;
import fr.bibliotheque.dao.impl.EmpruntDAOImpl;
import fr.bibliotheque.dao.impl.LivreDAOImpl;

public class DAOFactory {
    private static DAOFactory instance;

    private AdministrateurDAO administrateurDAO;
    private LivreDAO livreDAO;
    private AdherentDAO adherentDAO;
    private EmpruntDAO empruntDAO;

    private DAOFactory() {
        administrateurDAO = new AdministrateurDAOImpl();
        livreDAO = new LivreDAOImpl();
        adherentDAO = new AdherentDAOImpl();
        empruntDAO = new EmpruntDAOImpl();
    }

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public AdministrateurDAO getAdministrateurDAO() {
        return administrateurDAO;
    }

    public LivreDAO getLivreDAO() {
        return livreDAO;
    }

    public AdherentDAO getAdherentDAO() {
        return adherentDAO;
    }

    public EmpruntDAO getEmpruntDAO() {
        return empruntDAO;
    }
}

