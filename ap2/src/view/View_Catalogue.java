package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import controller.mainMVC;
import model.LIVRE;

public class View_Catalogue {

    private JFrame frame;
    private JTable table;

    public View_Catalogue() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame("Catalogue des livres");
        frame.setBounds(100, 100, 800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"ISBN", "Titre", "Auteur", "Prix", "Statut"};

        // On désactive l'édition des cellules pour que le tableau soit en lecture seule
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (LIVRE livre : mainMVC.getM().getListLivre()) {
            String auteur = "Inconnu";
            if (livre.getAuteur() != null) {
                auteur = livre.getAuteur().getPrenom() + " " + livre.getAuteur().getNom();
            }

            // String.format pour éviter les problèmes de précision des float
            String prix = String.format("%.2f €", livre.getPrix());

            String statut = (livre.getEmprunteur() == null) ? "Disponible" : "Emprunté";

            model.addRow(new Object[]{livre.getISBN(), livre.getTitre(), auteur, prix, statut});
        }

        table = new JTable(model);
        table.setRowHeight(25);
        table.setCellSelectionEnabled(true);

        // Raccourci Ctrl+C pour copier la cellule sélectionnée
        KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
        table.getInputMap().put(copy, "copy");
        table.getActionMap().put("copy", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                copySelectedCell();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 770, 300);

        JButton btnRetour = new JButton("Retour");
        btnRetour.setBounds(350, 320, 100, 30);
        btnRetour.addActionListener(e -> frame.dispose());

        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(scrollPane);
        frame.getContentPane().add(btnRetour);
    }

    // Copie la valeur de la cellule sélectionnée dans le presse-papiers
    private void copySelectedCell() {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        if (row >= 0 && col >= 0) {
            Object value = table.getValueAt(row, col);
            if (value != null) {
                StringSelection sel = new StringSelection(value.toString());
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
            }
        }
    }
}