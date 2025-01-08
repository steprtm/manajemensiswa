package ManajemenSiswaGradle;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainForm mainForm = new MainForm();
            mainForm.setVisible(true);
        });
    }
}