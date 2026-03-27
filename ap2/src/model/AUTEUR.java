package model;

public class AUTEUR {
    private String num;
    private String nom;
    private String prenom;
    private String date_naissance;
    private String description;
    
    public AUTEUR(String num, String nom, String prenom, String date_naissance, String description) {
        this.num = num;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.description = description;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}