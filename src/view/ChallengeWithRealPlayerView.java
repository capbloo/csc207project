// ChallengeWithRealPlayerView.java
package view;

import data_access.MakeMoveDataAccessObject;
import entity.Board;
import interface_adapter.CheckGameEnds.CheckGameEndsController;
import interface_adapter.CheckGameEnds.CheckGameEndsPresenter;
import interface_adapter.CheckGameEnds.CheckGameEndsViewModel;
import interface_adapter.HighlightSquare.HighlightController;
import interface_adapter.HighlightSquare.HighlightPresenter;
import interface_adapter.HighlightSquare.HighlightViewModel;
import interface_adapter.make_move.MakeMoveController;
import interface_adapter.make_move.MakeMovePresenter;
import interface_adapter.make_move.MakeMoveViewModel;
import use_case.CheckGameEnds.CheckGameEndsInteractor;
import use_case.HighlightSquare.HighlightInteractor;
import use_case.make_move.MakeMoveInteractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChallengeWithRealPlayerView implements MenuView {

    private JFrame frame;
    private JComboBox<String> colorComboBox;
    private JTextField playerNameTextField;

    public ChallengeWithRealPlayerView(JFrame frame) {
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

            // Create a JComBox for selecting difficulty levels0
            String[] colors = {"White - You Go First!", "Black", "Random"};
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
                        JOptionPane.showMessageDialog(frame, "Starting challenge with player: " + playerName);
                        close();
                        // This will be based on the api call now, setting it as white rn for testing
                        String usersColour = "black";
                        // Create chess board
                        Board board = new Board();
                        MakeMoveViewModel makeMoveViewModel = new MakeMoveViewModel();
                        MakeMovePresenter makeMovePresenter = new MakeMovePresenter(makeMoveViewModel);
                        MakeMoveDataAccessObject makeMoveDataAccessObject = new MakeMoveDataAccessObject("abcd");
                        MakeMoveInteractor makeMoveInteractor = new MakeMoveInteractor(makeMoveDataAccessObject, makeMovePresenter, board);
                        MakeMoveController makeMoveController = new MakeMoveController(makeMoveInteractor);

                        HighlightViewModel highlightViewModel = new HighlightViewModel();
                        HighlightPresenter highlightPresenter = new HighlightPresenter(highlightViewModel);
                        HighlightInteractor highlightInteractor = new HighlightInteractor(highlightPresenter, board);
                        HighlightController highlightController = new HighlightController(highlightInteractor);

                        CheckGameEndsViewModel checkGameEndsViewModel = new CheckGameEndsViewModel();
                        CheckGameEndsPresenter checkGameEndsPresenter = new CheckGameEndsPresenter(checkGameEndsViewModel);
                        CheckGameEndsInteractor checkGameEndsInteractor = new CheckGameEndsInteractor(checkGameEndsPresenter, board);
                        CheckGameEndsController checkGameEndsController = new CheckGameEndsController(checkGameEndsInteractor);

                        BoardView boardview = new BoardView(board, makeMoveController, makeMoveViewModel, highlightController, highlightViewModel
                        ,checkGameEndsController, checkGameEndsViewModel);
                        boardview.setVisible(true);

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
