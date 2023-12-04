package view;

import view.MenuView;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class RulesView implements MenuView {
    private JFrame frame;

    public RulesView(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> {
            // Rules
            JTextArea rulesTextArea = new JTextArea();
            rulesTextArea.setText("Welcome! Here are the rules of chess...\n\n" +
                    "1. Each player starts with 16 pieces: one king, one queen, two rooks, two knights, " +
                    "two bishops, and eight pawns.\n" +
                    "2. The goal is to checkmate the opponent's king, putting it in a position where it cannot escape capture.\n" +
                    "3. The game can end in a draw in various ways, such as stalemate or insufficient material.\n" +
                    "4. Pawns move forward but capture diagonally. They have a special initial two-square move option.\n" +
                    "5. Knights move in an L-shape: two squares in one direction and then one square perpendicular to that.\n" +
                    "6. Bishops move diagonally any number of squares.\n" +
                    "7. Rooks move horizontally or vertically any number of squares.\n" +
                    "8. Queens can move horizontally, vertically, or diagonally any number of squares.\n" +
                    "9. Kings move one square in any direction.\n" +
                    "10. Castling is a special move involving the king and one of the rooks.\n\n" +
                    "These are just basic rules. Enjoy your game!");

            rulesTextArea.setEditable(false);
            rulesTextArea.setBackground(new Color(18, 30, 49)); // Set the background color
            rulesTextArea.setForeground(Color.WHITE); // Set the text color
            JScrollPane rulesScrollPane = new JScrollPane(rulesTextArea);
            rulesScrollPane.setPreferredSize(new Dimension(600, 250)); // Set the preferred size here

            // YouTube Tutorial Link
            JEditorPane youtubeLinkPane = new JEditorPane("text/html", "<html><a href=\"https://www.youtube.com/watch?v=6Pqd7UFWr7M\">Chess Tutorial from wikiHow</a></html>");
            youtubeLinkPane.setEditable(false);
            youtubeLinkPane.addHyperlinkListener(e -> {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    // Open the link in the default browser
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            youtubeLinkPane.setPreferredSize(new Dimension(200, 50)); // Set the preferred size here

            // Main panel with GridBagLayout
            JPanel mainPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weighty = 1; // Fill vertical space
            mainPanel.add(rulesScrollPane, gbc);

            gbc.gridy = 1;
            gbc.insets = new Insets(20, 0, 0, 0); // Add top margin
            mainPanel.add(youtubeLinkPane, gbc);

            // Buttons panel
            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
            JButton goBackButton = new JButton("Understood! Let's Play!");
            goBackButton.setPreferredSize(new Dimension(300, 50));
            goBackButton.addActionListener(e -> {
                close();
                new MainMenuView().show();
            });
            buttonsPanel.add(goBackButton);

            // Frame setup
            frame.getContentPane().removeAll();
            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
            frame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
            frame.setSize(700, 530);
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
