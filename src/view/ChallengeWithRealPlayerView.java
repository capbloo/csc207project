// ChallengeWithRealPlayerView.java
package view;

import data_access.GetMoveDataAccessObject;
import data_access.MakeMoveDataAccessObject;
import entity.Board;
import interface_adapter.CheckGameEnds.CheckGameEndsController;
import interface_adapter.CheckGameEnds.CheckGameEndsPresenter;
import interface_adapter.CheckGameEnds.CheckGameEndsViewModel;
import interface_adapter.Get_move.GetMoveController;
import interface_adapter.Get_move.GetMovePresenter;
import interface_adapter.Get_move.GetMoveViewModel;
import interface_adapter.HighlightSquare.HighlightController;
import interface_adapter.HighlightSquare.HighlightPresenter;
import interface_adapter.HighlightSquare.HighlightViewModel;
import interface_adapter.challenge_player.ChallengePlayerController;
import interface_adapter.challenge_player.ChallengePlayerState;
import interface_adapter.challenge_player.ChallengePlayerViewModel;
import interface_adapter.make_move.MakeMoveController;
import interface_adapter.make_move.MakeMovePresenter;
import interface_adapter.make_move.MakeMoveViewModel;
import use_case.CheckGameEnds.CheckGameEndsInteractor;
import use_case.Get_move.GetMoveInteractor;
import use_case.HighlightSquare.HighlightInteractor;
import use_case.make_move.MakeMoveInteractor;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ChallengeWithRealPlayerView implements MenuView, ActionListener, PropertyChangeListener {

    private JFrame frame;
    private JComboBox<String> colorComboBox;
    private JTextField playerNameTextField;

    private ChallengePlayerViewModel challengePlayerViewModel;

    private ChallengePlayerController challengePlayerController;

    private String gameID;

    private String color2;

    public ChallengeWithRealPlayerView(JFrame frame, ChallengePlayerController challengePlayerController, ChallengePlayerViewModel challengePlayerViewModel) {
        this.frame = frame;
        this.challengePlayerViewModel = challengePlayerViewModel;
        this.challengePlayerController = challengePlayerController;
        challengePlayerViewModel.addPropertyChangeListener(this);
    }

    int color = 0;

    String name = "";
    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> {
            JPanel panel = new JPanel(new BorderLayout());

            // Title
            JLabel titleLabel = new JLabel("Welcome! Who are you challenging today?");
            titleLabel.setFont(new Font("Bell MT", Font.BOLD, 20));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(titleLabel, BorderLayout.NORTH);

            JPanel contentPanel = new JPanel(new GridLayout(0, 2, 0, 20));

            // Create a JComBox for selecting difficulty levels0
            String[] colors = {"White - You Go First!", "Black", "Random"};
            colorComboBox = new JComboBox<>(colors);
            contentPanel.add(new JLabel("Select Color: "));
            contentPanel.add(colorComboBox);
            // Set the custom renderer to change text color
            colorComboBox.setRenderer(new CustomComboBoxRenderer());
            colorComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    color = colorComboBox.getSelectedIndex();
                    System.out.println("Set color to" + color);
                }
            });

            // Create a JTextField for entering the player's name
            playerNameTextField = new JTextField(20);
            contentPanel.add(new JLabel("\nEnter Player's Name: "));
            contentPanel.add(playerNameTextField);
            // Set the custom renderer to change text color
            playerNameTextField.setForeground(Color.BLACK); // Set the text color
            playerNameTextField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    name = playerNameTextField.getText();
                    System.out.println("Set name to " + name);
                }
            });

            JButton startButton = new JButton("Start Challenge");
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Challenge "+ name);
                    challengePlayerController.execute(color, name);
                    String playerName = playerNameTextField.getText();
                    if (!playerName.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Starting challenge with player: " + playerName);
                        close();
                        // This will be based on the api call now, setting it as white rn for testing
                        String usersColour = color2;
                        // Create chess board
                        Board board = new Board();
                        MakeMoveViewModel makeMoveViewModel = new MakeMoveViewModel();
                        MakeMovePresenter makeMovePresenter = new MakeMovePresenter(makeMoveViewModel);
                        MakeMoveDataAccessObject makeMoveDataAccessObject = new MakeMoveDataAccessObject(gameID);
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

                        GetMoveViewModel getMoveViewModel = new GetMoveViewModel();
                        GetMovePresenter getMovePresenter = new GetMovePresenter(getMoveViewModel);
                        GetMoveDataAccessObject getMoveDataAccessObject = new GetMoveDataAccessObject(gameID);
                        GetMoveInteractor getMoveInteractor = new GetMoveInteractor(getMovePresenter, getMoveDataAccessObject, board);
                        GetMoveController getMoveController = new GetMoveController(getMoveInteractor);

                        BoardView boardview = new BoardView(board, makeMoveController, makeMoveViewModel, highlightController, highlightViewModel
                        ,checkGameEndsController, checkGameEndsViewModel, usersColour, getMoveViewModel, getMoveController);
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("ChallengePlayer")){
            ChallengePlayerState state = (ChallengePlayerState) evt.getNewValue();
            gameID = state.getGameID();
            color2 = state.getColor();
            System.out.println("Game Started");
        }

    }

    private class CustomComboBoxRenderer extends BasicComboBoxRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Set the text color to black
            setForeground(Color.BLACK);
            // Set the background color to white
            setBackground(Color.WHITE);
            // Set the custom font
            Font customFont = new Font("Bell MT", Font.BOLD, 16);
            setFont(customFont);

            return this;
        }
    }
}
