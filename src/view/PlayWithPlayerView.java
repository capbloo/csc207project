// ChallengeWithRealPlayerView.java
package view;

import entity.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayWithPlayerView implements MenuView {

    private JFrame frame;
    private JComboBox<String> colorComboBox;
    private JTextField playerNameTextField;

    public PlayWithPlayerView(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> {
            JPanel panel = new JPanel(new BorderLayout());

            // Title
            JLabel titleLabel = new JLabel("Welcome! Who are you challenging today?");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(titleLabel, BorderLayout.NORTH);

            JPanel contentPanel = new JPanel(new GridLayout(0, 2, 0, 20));

            // Create a JComBox for selecting difficulty levels
            String[] colors = {"White - You Go First!", "Black"};
            colorComboBox = new JComboBox<>(colors);
            contentPanel.add(new JLabel("Select Color: "));
            contentPanel.add(colorComboBox);

            // Create a JTextField for entering the player's name
            playerNameTextField = new JTextField(20);
            contentPanel.add(new JLabel("\nEnter Player's Name: "));
            contentPanel.add(playerNameTextField);

            JButton startButton = new JButton("Start Challenge");
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String playerName = playerNameTextField.getText();
                    if (!playerName.isEmpty()) {

                        // Create chess board
                        JOptionPane.showMessageDialog(frame, "Starting challenge with player: " + playerName);
                        close();
                        JFrame application = new JFrame("Chess game");
                        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        application.setVisible(true);
                        application.setSize(654,678);
                        application.setLocationRelativeTo(null);
                        Board board = new Board();
                        BoardView boardview = new BoardView(application, board);

                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a player's name.");
                    }
                }
            });
            contentPanel.add(startButton);

            // Go Back Title
            JButton goBackButton = new JButton("Go Back to Main Menu");
            goBackButton.setPreferredSize(new Dimension(200, 50));
            goBackButton.addActionListener(e -> {
                close();
                new MainMenuView().show();
            });
            panel.add(goBackButton, BorderLayout.SOUTH);

            panel.add(contentPanel, BorderLayout.CENTER);

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
