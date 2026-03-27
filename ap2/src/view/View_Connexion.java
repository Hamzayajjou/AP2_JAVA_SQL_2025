package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.mainMVC;

public class View_Connexion {

    private JFrame frame;

    public View_Connexion() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame("Connexion");
        frame.setBounds(100, 100, 400, 228);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitre = new JLabel("CONNEXION");
        lblTitre.setBounds(80, 19, 200, 30);
        lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblTitre);

        JLabel lblNumAdherent = new JLabel("Numéro adhérent :");
        lblNumAdherent.setBounds(50, 60, 200, 25);
        frame.getContentPane().add(lblNumAdherent);

        JTextField txtNumAdherent = new JTextField();
        txtNumAdherent.setBounds(50, 85, 200, 25);
        frame.getContentPane().add(txtNumAdherent);

        JLabel lblExemple = new JLabel("Exemples : A001, A002, A003...");
        lblExemple.setBounds(50, 110, 300, 20);
        frame.getContentPane().add(lblExemple);

        JButton btnConnexion = new JButton("Se connecter");
        btnConnexion.setBounds(50, 140, 120, 30);
        frame.getContentPane().add(btnConnexion);

        JButton btnQuitter = new JButton("Quitter");
        btnQuitter.setBounds(180, 140, 100, 30);
        frame.getContentPane().add(btnQuitter);

        // La touche Entrée déclenche le bouton de connexion
        frame.getRootPane().setDefaultButton(btnConnexion);

        btnConnexion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numAdherent = txtNumAdherent.getText().trim();

                if (numAdherent.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer votre numéro d'adhérent.");
                    return;
                }

                // On cherche l'adhérent dans la liste chargée en mémoire
                model.ADHERENT adherent = mainMVC.getM().findAdherent(numAdherent);

                if (adherent != null) {
                    mainMVC.getM().setAdherentConnecte(adherent);
                    frame.dispose();
                    new View_Accueil();
                } else {
                    JOptionPane.showMessageDialog(frame,
                        "Adhérent introuvable. Vérifiez votre numéro (ex : A001).");
                }
            }
        });

        btnQuitter.addActionListener(e -> System.exit(0));
    }
}