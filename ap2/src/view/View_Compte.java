package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.mainMVC;
import model.ADHERENT;

public class View_Compte {

    private JFrame frame;
    private ADHERENT adherent;

    public View_Compte(ADHERENT adherent) {
        this.adherent = adherent;
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame("Mon Compte");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitre = new JLabel("MON COMPTE");
        lblTitre.setBounds(120, 20, 200, 30);
        lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblTitre);

        JLabel lblNum = new JLabel("Numéro :");
        lblNum.setBounds(50, 60, 100, 25);
        frame.getContentPane().add(lblNum);

        // Le numéro d'adhérent n'est pas modifiable
        JLabel lblNumValue = new JLabel(adherent.getNum());
        lblNumValue.setBounds(160, 60, 200, 25);
        frame.getContentPane().add(lblNumValue);

        JLabel lblNom = new JLabel("Nom :");
        lblNom.setBounds(50, 90, 100, 25);
        frame.getContentPane().add(lblNom);

        JTextField txtNom = new JTextField(adherent.getNom());
        txtNom.setBounds(160, 90, 200, 25);
        frame.getContentPane().add(txtNom);

        JLabel lblPrenom = new JLabel("Prénom :");
        lblPrenom.setBounds(50, 120, 100, 25);
        frame.getContentPane().add(lblPrenom);

        JTextField txtPrenom = new JTextField(adherent.getPrenom());
        txtPrenom.setBounds(160, 120, 200, 25);
        frame.getContentPane().add(txtPrenom);

        JLabel lblEmail = new JLabel("Email :");
        lblEmail.setBounds(50, 150, 100, 25);
        frame.getContentPane().add(lblEmail);

        JTextField txtEmail = new JTextField(adherent.getEmail());
        txtEmail.setBounds(160, 150, 200, 25);
        frame.getContentPane().add(txtEmail);

        JButton btnModifier = new JButton("Enregistrer");
        btnModifier.setBounds(50, 190, 120, 30);
        frame.getContentPane().add(btnModifier);

        JButton btnMesLivres = new JButton("Mes livres");
        btnMesLivres.setBounds(180, 190, 120, 30);
        frame.getContentPane().add(btnMesLivres);

        JButton btnRetour = new JButton("Retour");
        btnRetour.setBounds(50, 230, 100, 30);
        btnRetour.addActionListener(e -> frame.dispose());
        frame.getContentPane().add(btnRetour);

        btnModifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nouveauNom    = txtNom.getText().trim();
                String nouveauPrenom = txtPrenom.getText().trim();
                String nouvelEmail   = txtEmail.getText().trim();

                if (nouveauNom.isEmpty() || nouveauPrenom.isEmpty() || nouvelEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Erreur : tous les champs doivent être remplis.");
                    return;
                }

                // Vérification basique du format de l'email
                if (!nouvelEmail.contains("@") || !nouvelEmail.contains(".")) {
                    JOptionPane.showMessageDialog(frame, "Erreur : adresse email invalide.");
                    return;
                }

                try {
                    boolean succes = mainMVC.getM().updateAdherent(
                        adherent.getNum(), nouveauNom, nouveauPrenom, nouvelEmail);

                    if (succes) {
                        JOptionPane.showMessageDialog(frame, "Informations mises à jour avec succès !");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erreur : impossible de mettre à jour les informations.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur de base de données : " + ex.getMessage());
                }
            }
        });

        btnMesLivres.addActionListener(e -> new View_LivresEmpruntes(adherent));
    }
}