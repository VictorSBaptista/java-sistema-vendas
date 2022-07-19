package controller;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import javax.swing.UIManager;
import util.ProgressBar;
import view.LoginView;

/**
 * Classe principal responsável por executar o projeto
 *
 * @author Victor Baptista
 * @since 22/03/2021
 * @version 1.0
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            McWinLookAndFeel.setTheme("Default", "aaaaaaa", "Sistema");
            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");

        } catch (Exception e) {
            e.printStackTrace();
        }

        new ProgressBar().progressBar();

        new LoginView().setVisible(true);

    }
}
