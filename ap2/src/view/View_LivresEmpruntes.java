package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.mainMVC;
import model.ADHERENT;
import model.LIVRE;

public class View_LivresEmpruntes {

    private JFrame frame;
    private ADHERENT adherent;
    private DefaultListModel<String> listModel;

    public View_LivresEmpruntes(ADHERENT adherent) {
        this.adherent = adherent;
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame("Mes livres empruntés");
        frame.setBounds(100, 100, 500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitre = new JLabel("LIVRES EMPRUNTÉS");
        lblTitre.setBounds(150, 20, 200, 30);
        lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblTitre);

        listModel = new DefaultListModel<>();
        JList<String> listLivres = new JList<>(listModel);
        chargerLivresEmpruntes();

        JScrollPane scrollPane = new JScrollPane(listLivres);
        scrollPane.setBounds(50, 60, 400, 150);
        frame.getContentPane().add(scrollPane);

        JButton btnRetourner = new JButton("Retourner");
        btnRetourner.setBounds(50, 220, 100, 30);
        frame.getContentPane().add(btnRetourner);

        JButton btnFermer = new JButton("Fermer");
        btnFermer.setBounds(160, 220, 100, 30);
        btnFermer.addActionListener(e -> frame.dispose());
        frame.getContentPane().add(btnFermer);

        btnRetourner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int indexSelectionne = listLivres.getSelectedIndex();
                if (indexSelectionne == -1) {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un livre.");
                    return;
                }

                String itemSelectionne = listModel.getElementAt(indexSelectionne);

                // Si la liste affiche le message vide, il n'y a rien à retourner
                if (!itemSelectionne.contains(" - ")) {
                    JOptionPane.showMessageDialog(frame, "Aucun livre à retourner.");
                    return;
                }

                // L'ISBN est la première partie de la chaîne, avant le premier " - "
                String isbn = itemSelectionne.split(" - ")[0];

                try {
                    model.LIVRE livre = mainMVC.getM().findLivre(isbn);
                    if (livre == null) {
                        JOptionPane.showMessageDialog(frame, "Erreur : livre introuvable.");
                        return;
                    }
                    if (livre.getEmprunteur() == null) {
                        JOptionPane.showMessageDialog(frame, "Erreur : ce livre n'est pas emprunté.");
                        return;
                    }

                    boolean succes = mainMVC.getM().retournerLivre(isbn);

                    if (succes) {
                        JOptionPane.showMessageDialog(frame, "Livre retourné avec succès !");
                        chargerLivresEmpruntes(); // on rafraîchit la liste après le retour
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erreur lors du retour du livre.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur de base de données : " + ex.getMessage());
                }
            }
        });
    }

    private void chargerLivresEmpruntes() {
        listModel.clear();

        // On utilise directement la liste de livres de l'adhérent (déjà maintenue à jour par le modèle)
        if (adherent.getListLivre().isEmpty()) {
            listModel.addElement("Aucun livre emprunté");
            return;
        }

        for (LIVRE livre : adherent.getListLivre()) {
            String auteur = "Inconnu";
            if (livre.getAuteur() != null) {
                auteur = livre.getAuteur().getPrenom() + " " + livre.getAuteur().getNom();
            }
            String item = livre.getISBN() + " - " + livre.getTitre()
                + " (" + auteur + ") - " + String.format("%.2f", livre.getPrix()) + " €";
            listModel.addElement(item);
        }
    }
}