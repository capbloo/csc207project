package app;

import view.MainMenuView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Set Nimbus look and feel
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            // Set dark mode colors
            UIManager.put("nimbusBase", new Color(18, 30, 49));
            UIManager.put("nimbusBlueGrey", new Color(18, 30, 49));
            UIManager.put("control", new Color(30, 30, 30));
            UIManager.put("text", Color.WHITE);

        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Customize the default font for Swing components
        Font customFont = new Font("Bell MT", Font.BOLD, 16);
        UIManager.put("Button.font", customFont);
        UIManager.put("Label.font", customFont);
        UIManager.put("TextField.font", customFont);
        UIManager.put("TextArea.font", customFont);

        new MainMenuView().show();
    }
}
