// MainMenuView.java
package view;

import javax.swing.*;
import java.awt.*;

public class MainMenuView implements MenuView {

    private JFrame frame;

    public MainMenuView() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Chess Game - Main Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            show();
        });
    }

    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));

            JButton newGameButton = new JButton("New Game");
            newGameButton.setPreferredSize(new Dimension(150, 50));
            newGameButton.addActionListener(e -> new NewGameOptionsView(frame).show());
            panel.add(newGameButton);

            JButton rulesButton = new JButton("Rules");
            rulesButton.setPreferredSize(new Dimension(150, 50));
            rulesButton.addActionListener(e -> new RulesView(frame).show());
            panel.add(rulesButton);

            JButton exitButton = new JButton("Exit");
            exitButton.setPreferredSize(new Dimension(150, 50));
            exitButton.addActionListener(e -> close());
            panel.add(exitButton);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(panel);
            frame.revalidate();
            frame.repaint();
            frame.setVisible(true);
        });
    }

    @Override
    public void close() {
        SwingUtilities.invokeLater(() -> frame.dispose());
    }
}
