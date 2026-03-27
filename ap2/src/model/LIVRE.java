package model;

public class LIVRE {
    private String ISBN;
    private String titre;
    private float prix;
    private ADHERENT emprunteur;
    private AUTEUR auteur;
    
    public LIVRE(String iSBN, String titre, float prix, ADHERENT emprunteur, AUTEUR auteur) {
        ISBN = iSBN;
        this.titre = titre;
        this.prix = prix;
        this.emprunteur = emprunteur;
        this.auteur = auteur;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public ADHERENT getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(ADHERENT emprunteur) {
        this.emprunteur = emprunteur;
    }

    public AUTEUR getAuteur() {
        return auteur;
    }

    public void setAuteur(AUTEUR auteur) {
        this.auteur = auteur;
    }
}