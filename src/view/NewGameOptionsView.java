// NewGameOptionsView.java
package view;

import javax.swing.*;
import java.awt.*;

public class NewGameOptionsView implements MenuView {

    private JFrame frame;

    public NewGameOptionsView(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> {
            JPanel panel = new JPanel(new BorderLayout());

            JLabel selectModeLabel = new JLabel("Select Mode");
            selectModeLabel.setFont(new Font("Arial", Font.BOLD, 20));
            selectModeLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(selectModeLabel, BorderLayout.NORTH);

            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));

            JButton practiceWithAIButton = new JButton("Practice with AI");
            practiceWithAIButton.setPreferredSize(new Dimension(150, 50));
            practiceWithAIButton.addActionListener(e -> new PracticeWithAIView(frame).show());
            buttonsPanel.add(practiceWithAIButton);

            JButton challengeWithRealPlayerButton = new JButton("Challenge with Real Player");
            challengeWithRealPlayerButton.setPreferredSize(new Dimension(200, 50));
            challengeWithRealPlayerButton.addActionListener(e -> new ChallengeWithRealPlayerView(frame).show());
            buttonsPanel.add(challengeWithRealPlayerButton);

            panel.add(buttonsPanel, BorderLayout.CENTER);

            JButton goBackButton = new JButton("Go Back to Main Menu");
            goBackButton.setPreferredSize(new Dimension(200, 50));
            goBackButton.addActionListener(e -> {
                close();
                new MainMenuView().show();
            });
            panel.add(goBackButton, BorderLayout.SOUTH);

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
