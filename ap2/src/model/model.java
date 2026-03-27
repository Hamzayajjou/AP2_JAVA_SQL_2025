package model;

import java.sql.*;
import java.util.ArrayList;

public class model {

    // Paramètres de connexion à la base de données MySQL
    private static final String URL    = "jdbc:mysql://localhost:3306/ap2_karim_2026";
    private static final String USER   = "root";
    private static final String PASSWD = "root";

    private ArrayList<LIVRE>    listLivre;
    private ArrayList<AUTEUR>   listAuteur;
    private ArrayList<ADHERENT> listAdherent;

    // L'adhérent qui est actuellement connecté à l'application
    private ADHERENT adherentConnecte;

    // Charge toutes les données depuis la BDD au démarrage de l'application
    public void getAll() throws SQLException {
        listAdherent = new ArrayList<>();
        listAuteur   = new ArrayList<>();
        listLivre    = new ArrayList<>();

        // même si une exception est levée, ce qui évite les fuites de connexion
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
             Statement  stmt = conn.createStatement()) {

            // Chargement des auteurs
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM auteur")) {
                while (rs.next()) {
                    listAuteur.add(new AUTEUR(
                        rs.getString("num"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("date_naissance"),
                        rs.getString("description")
                    ));
                }
            }

            // Chargement des adhérents (sans leurs livres pour l'instant)
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM adherent")) {
                while (rs.next()) {
                    listAdherent.add(new ADHERENT(
                        rs.getString("num"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        new ArrayList<>()
                    ));
                }
            }

            // Chargement des livres avec liaison vers auteur et emprunteur
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM livre")) {
                while (rs.next()) {
                    String isbn        = rs.getString("ISBN");
                    String titre       = rs.getString("titre");
                    float  prix        = rs.getFloat("prix");
                    String numAdherent = rs.getString("adherent");
                    String numAuteur   = rs.getString("auteur");

                    LIVRE livre = new LIVRE(isbn, titre, prix, null, null);

                    if (numAuteur != null) {
                        livre.setAuteur(findAuteur(numAuteur));
                    }

                    // Si le livre est emprunté, on lie le livre à l'adhérent et inversement
                    if (numAdherent != null) {
                        ADHERENT emprunteur = findAdherent(numAdherent);
                        livre.setEmprunteur(emprunteur);
                        if (emprunteur != null) {
                            emprunteur.getListLivre().add(livre);
                        }
                    }

                    listLivre.add(livre);
                }
            }
        }
    }

    // Recherche un adhérent dans la liste en mémoire par son numéro
    public ADHERENT findAdherent(String num) {
        if (num == null) return null;
        for (ADHERENT a : listAdherent) {
            if (num.equals(a.getNum())) return a;
        }
        return null;
    }

    public AUTEUR findAuteur(String num) {
        if (num == null) return null;
        for (AUTEUR a : listAuteur) {
            if (num.equals(a.getNum())) return a;
        }
        return null;
    }

    public LIVRE findLivre(String ISBN) {
        if (ISBN == null) return null;
        for (LIVRE l : listLivre) {
            if (ISBN.equals(l.getISBN())) return l;
        }
        return null;
    }

    // Met à jour la BDD et la liste en mémoire quand un adhérent emprunte un livre
    public boolean emprunterLivre(String ISBN, String numAdherent) throws SQLException {
        LIVRE livre = findLivre(ISBN);
        if (livre == null) return false;
        if (livre.getEmprunteur() != null) return false; // livre déjà emprunté

        ADHERENT adherent = findAdherent(numAdherent);
        if (adherent == null) return false;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
             Statement  stmt = conn.createStatement()) {
            String sql = "UPDATE livre SET adherent = '" + numAdherent + "' WHERE ISBN = '" + ISBN + "'";
            int rows = stmt.executeUpdate(sql);
            if (rows > 0) {
                // Mise à jour des objets en mémoire pour rester synchronisé avec la BDD
                livre.setEmprunteur(adherent);
                adherent.getListLivre().add(livre);
                return true;
            }
        }
        return false;
    }

    // Remet le livre à disposition et met à jour l'objet en mémoire
    public boolean retournerLivre(String ISBN) throws SQLException {
        LIVRE livre = findLivre(ISBN);
        if (livre == null) return false;
        if (livre.getEmprunteur() == null) return false;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
             Statement  stmt = conn.createStatement()) {
            String sql = "UPDATE livre SET adherent = NULL WHERE ISBN = '" + ISBN + "'";
            int rows = stmt.executeUpdate(sql);
            if (rows > 0) {
                ADHERENT ancien = livre.getEmprunteur();
                if (ancien != null) {
                    ancien.getListLivre().remove(livre);
                }
                livre.setEmprunteur(null);
                return true;
            }
        }
        return false;
    }

    // Modifie les infos d'un adhérent dans la BDD et dans l'objet en mémoire
    public boolean updateAdherent(String num, String nom, String prenom, String email)
            throws SQLException {
        ADHERENT adherent = findAdherent(num);
        if (adherent == null) return false;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
             Statement  stmt = conn.createStatement()) {
            String sql = "UPDATE adherent SET nom = '" + nom + "', prenom = '" + prenom
                       + "', email = '" + email + "' WHERE num = '" + num + "'";
            int rows = stmt.executeUpdate(sql);
            if (rows > 0) {
                adherent.setNom(nom);
                adherent.setPrenom(prenom);
                adherent.setEmail(email);
                return true;
            }
        }
        return false;
    }

    public ArrayList<LIVRE>    getListLivre()    { return listLivre;    }
    public ArrayList<AUTEUR>   getListAuteur()   { return listAuteur;   }
    public ArrayList<ADHERENT> getListAdherent() { return listAdherent; }

    public ADHERENT getAdherentConnecte()                  { return adherentConnecte; }
    public void     setAdherentConnecte(ADHERENT adherent) { this.adherentConnecte = adherent; }
}