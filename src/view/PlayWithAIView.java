// PracticeWithAIView.java
package view;

import entity.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayWithAIView implements MenuView {

    private JFrame frame;
    private JComboBox<String> colorComboBox;
    private JComboBox<String> difficultyComboBox;

    public PlayWithAIView(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> {
            JPanel panel = new JPanel(new BorderLayout());

            // Title
            JLabel titleLabel = new JLabel("Welcome! Let's match you with an AI!");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(titleLabel, BorderLayout.NORTH);

            JPanel contentPanel = new JPanel(new GridLayout(0, 2, 0, 20));

            // Create a JComBox for selecting difficulty levels
            String[] colors = {"White - You Go First!", "Black"};
            colorComboBox = new JComboBox<>(colors);
            contentPanel.add(new JLabel("Select Color: "));
            contentPanel.add(colorComboBox);

            // Create a JComboBox for selecting difficulty levels
            String[] difficultyLevels = {"LV. 1", "LV. 2", "LV. 3", "LV. 4", "LV. 5", "LV.6", "LV. 7", "LV. 8"};
            difficultyComboBox = new JComboBox<>(difficultyLevels);
            contentPanel.add(new JLabel("select Difficulty: "));
            contentPanel.add(difficultyComboBox);

            // Create a Button for starting the game
            JButton startButton = new JButton("Start Game");
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    close();

                    // Create chess board
                    JFrame application = new JFrame("Chess game");
                    application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    application.setVisible(true);
                    application.setSize(654,678);
                    application.setLocationRelativeTo(null);
                    Board board = new Board();
                    BoardView boardview = new BoardView(application, board);
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
