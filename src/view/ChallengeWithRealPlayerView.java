// ChallengeWithRealPlayerView.java
package view;

import javax.swing.*;
import java.awt.*;

public class ChallengeWithRealPlayerView implements MenuView {

    private JFrame frame;

    public ChallengeWithRealPlayerView(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> {
            JLabel messageLabel = new JLabel("Loading your game. Good Luck!");
            messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
            messageLabel.setHorizontalAlignment(JLabel.CENTER);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(messageLabel, BorderLayout.CENTER);
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
