// MainMenuView.java
package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenuView implements MenuView {

    private JFrame frame;

    public MainMenuView() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Chess Game - Main Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 530);
            frame.setLocationRelativeTo(null);

            show();
        });
    }

    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> {

            // Set the background image
            BufferedImage backgroundImage;
            try {
                backgroundImage = ImageIO.read(new File("src/backgroundImage.jpg"));
            } catch (IOException e) {
                throw new RuntimeException("Failed to load background image.", e);
            }
            // Create a custom JPanel with the background image
            JPanel panel = new JPanel(){
                @Override
                protected void paintComponent(Graphics g){
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            };

            panel.setLayout(new BorderLayout());
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

            JButton newGameButton = new JButton("New Game");
            newGameButton.setPreferredSize(new Dimension(150, 50));
            newGameButton.addActionListener(e -> new NewGameOptionsView(frame).show());
            buttonPanel.add(newGameButton);

            JButton rulesButton = new JButton("Rules");
            rulesButton.setPreferredSize(new Dimension(150, 50));
            rulesButton.addActionListener(e -> new RulesView(frame).show());
            buttonPanel.add(rulesButton);

            JButton exitButton = new JButton("Exit");
            exitButton.setPreferredSize(new Dimension(150, 50));
            exitButton.addActionListener(e -> close());
            buttonPanel.add(exitButton);

            panel.add(buttonPanel, BorderLayout.SOUTH);

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
