package ap2java_sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		//initialise mes variables pour la connection
		String BDD="ap2_2025";
		String url="jdbc:mysql://localhost:3306/"+BDD;
		String user="root";
		String mdp="";

		//je fais la connection avec le bon driver
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url,user,mdp);

		//je prépare mon statement sur la connexion
		Statement stmt = conn.createStatement();

		//je selectionne tous mes livres pour les afficher
		ResultSet resultats = stmt.executeQuery("SELECT * from LIVRE");
		while (resultats.next())
		{
			System.out.println(resultats.getString(1) + " - "+ resultats.getString(2) +" - "+resultats.getFloat(3));
		}

		//j'insère un livre dans la BDD
		//stmt.executeUpdate("INSERT INTO LIVRE VALUES('1','newtitre',10,null,null)");



		//CREATION D'UN OBJET LIVRE
		LIVRE livre1;
		livre1 = new LIVRE("1221", "titre", 100, null, null);

		//AFFICHER LE TITRE DU LIVRE1
		System.out.println(livre1.getTitre());


		//CREATION d'un AUTEUR
		AUTEUR auteur1 ;
		auteur1=new AUTEUR("A1", "martin", "martin", "01/01/1900" , "un très bon auteur");


		//AFFECTER l'auteur1 au livre1
		livre1.setAuteur(auteur1);

		//afficher le nom de l'auteur du livre1
		System.out.println(livre1.getAuteur().getNom());

		//création d'un adhérent
		ADHERENT ad1;
		ad1 = new ADHERENT("ad01", "dupond", "dupond", "dupond@gmail.fr" , new ArrayList<LIVRE>() );


		//je veux que l'adhérent1 emprunte le livre1

		//on le met dans sa liste
		ad1.getListLivre().add(livre1);
		//on ajoute l'adhérent au livre
		livre1.setEmprunteur(ad1);


		//afficher le nom de l'adhérent qui a emprunté le livre1
		if (livre1.getEmprunteur()==null)
		{
			System.out.println("livre dispo");
		}
		else
		{
			System.out.println("livre emprunté par "+livre1.getEmprunteur().getNom());
		}
		
		
	    //création un 2e livre emprunté par l'adhérent1
		LIVRE livre2 = new LIVRE("ISBN02", "titre2", 10, null, ad1);
		ad1.getListLivre().add(livre2);
		
		
		//AFFICHAGE DU NOMBRE DE LIVRE EMPRUNTE par l'adhérent1
		System.out.println(ad1.getListLivre().size());

		
		//AFFICHAGE DE LA LISTE DES LIVRES (isbn, titre) EMPRUNTE PAR L'ADHERENT1
		for (int i=0;i!=ad1.getListLivre().size();i++)
		{
			System.out.println(ad1.getListLivre().get(i).getISBN()+ " - "+ad1.getListLivre().get(i).getTitre());
		}
		//OU
		for (LIVRE unlivre : ad1.getListLivre())
		{
			System.out.println(unlivre.getISBN() + " - " + unlivre.getTitre());
		}
		
		
		
		

	}

}
