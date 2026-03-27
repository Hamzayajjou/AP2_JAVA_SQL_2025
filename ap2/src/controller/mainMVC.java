package controller;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.model;
import view.View_Connexion;

public class mainMVC {


    private static model m;

    public static model getM() {
        return m;
    }

    public static void main(String[] args) {
        m = new model();

        // On charge toutes les données au démarrage pour ne pas faire de requêtes à chaque action
        try {
            m.getAll();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                null,
                "Impossible de se connecter à la base de données :\n" + e.getMessage(),
                "Erreur de connexion",
                JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }

       
        SwingUtilities.invokeLater(View_Connexion::new);
    }
}