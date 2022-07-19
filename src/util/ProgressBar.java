/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressBar {

    final int MAX = 100;
    final JFrame progressBar = new JFrame("Carregando Sistema...");

    // creates progress bar
    final JProgressBar pb = new JProgressBar();

    public void progressBar() {
        pb.setMinimum(0);
        pb.setMaximum(MAX);
        pb.setStringPainted(true);
        pb.setOrientation(JProgressBar.HORIZONTAL);

        // add progress bar
        progressBar.setLayout(new FlowLayout());
        progressBar.getContentPane().add(pb);

        progressBar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        progressBar.setSize(240, 60);
        progressBar.setVisible(true);
        progressBar.setLocationRelativeTo(null);
        progressBar.setResizable(false);
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/sistema_60.png"));
        progressBar.setIconImage(icon.getImage());

        // update progressbar
        for (int i = 0; i <= MAX; i++) {
            final int currentValue = i;
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        pb.setValue(currentValue);
                    }
                });
                java.lang.Thread.sleep(25);

            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(progressBar, e.getMessage());
            }
        }

        progressBar.setVisible(false);
    }
}
