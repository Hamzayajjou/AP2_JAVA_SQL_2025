package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View_Accueil {

    private JFrame frame;

    public View_Accueil() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame("Bibliothèque - Accueil");
        frame.setBounds(100, 100, 400, 310);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // On récupère le nom de l'adhérent connecté pour l'afficher dans le message de bienvenue
        String nomAdherent = controller.mainMVC.getM().getAdherentConnecte().getPrenom()
            + " " + controller.mainMVC.getM().getAdherentConnecte().getNom();

        JLabel titreLabel = new JLabel("BIBLIOTHÈQUE");
        titreLabel.setBounds(100, 10, 200, 30);
        titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(titreLabel);

        JLabel lblBienvenue = new JLabel("Bonjour, " + nomAdherent);
        lblBienvenue.setBounds(50, 40, 300, 20);
        lblBienvenue.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblBienvenue);

        JButton btnLivres = new JButton("Voir tous les livres");
        btnLivres.setBounds(50, 75, 300, 40);
        frame.getContentPane().add(btnLivres);

        JButton btnEmprunter = new JButton("Emprunter un livre");
        btnEmprunter.setBounds(50, 125, 300, 40);
        frame.getContentPane().add(btnEmprunter);

        JButton btnCompte = new JButton("Voir mon compte");
        btnCompte.setBounds(50, 175, 300, 40);
        frame.getContentPane().add(btnCompte);

        JButton btnDeconnexion = new JButton("Déconnexion");
        btnDeconnexion.setBounds(50, 230, 300, 30);
        frame.getContentPane().add(btnDeconnexion);

        btnLivres.addActionListener(e -> new View_Catalogue());

        btnEmprunter.addActionListener(e -> new View_Emprunt());

        btnCompte.addActionListener(e ->
            new View_Compte(controller.mainMVC.getM().getAdherentConnecte())
        );

        btnDeconnexion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // On réinitialise l'adhérent connecté avant de revenir à la connexion
                controller.mainMVC.getM().setAdherentConnecte(null);
                frame.dispose();
                new View_Connexion();
            }
        });
    }
}