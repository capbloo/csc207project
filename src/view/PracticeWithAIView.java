// PracticeWithAIView.java
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
import interface_adapter.challenge_ai.ChallengeAIController;
import interface_adapter.challenge_ai.ChallengeAIState;
import interface_adapter.challenge_ai.ChallengeAIViewModel;
import interface_adapter.make_move.MakeMoveController;
import interface_adapter.make_move.MakeMovePresenter;
import interface_adapter.make_move.MakeMoveViewModel;
import use_case.CheckGameEnds.CheckGameEndsInteractor;
import use_case.Get_move.GetMoveInteractor;
import use_case.HighlightSquare.HighlightInteractor;
import use_case.make_move.MakeMoveInteractor;
import view.MenuView;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PracticeWithAIView implements MenuView, ActionListener, PropertyChangeListener {

    private JFrame frame;
    private JComboBox<String> colorComboBox;
    private JComboBox<String> difficultyComboBox;

    private ChallengeAIViewModel challengeAIViewModel;

    private ChallengeAIController challengeAIController;

    private String gameID;

    private String color2;


    public PracticeWithAIView(JFrame frame, ChallengeAIController challengeAIController, ChallengeAIViewModel challengeAIViewModel) {
        this.frame = frame;
        this.challengeAIViewModel = challengeAIViewModel;
        this.challengeAIController = challengeAIController;
        challengeAIViewModel.addPropertyChangeListener(this);
    }
    int level = 1;
    int color = 0;

    @Override
    public void show() {

        SwingUtilities.invokeLater(() -> {
            JPanel panel = new JPanel(new BorderLayout());

            // Title
            JLabel titleLabel = new JLabel("Welcome! Let's match you with an AI!");
            titleLabel.setFont(new Font("Bell MT", Font.BOLD, 20));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(titleLabel, BorderLayout.NORTH);

            JPanel contentPanel = new JPanel(new GridLayout(0, 2, 0, 20));

            // Create a JComBox for selecting difficulty levels
            String[] colors = {"White - You Go First!", "Black", "Random"};
            colorComboBox = new JComboBox<>(colors);
            contentPanel.add(new JLabel("Select Color: "));
            contentPanel.add(colorComboBox);
            colorComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    color = colorComboBox.getSelectedIndex();
                    System.out.println("Set color to" + color);
                }
            });

            // Create a JComboBox for selecting difficulty levels
            String[] difficultyLevels = {"LV. 1", "LV. 2", "LV. 3", "LV. 4", "LV. 5", "LV.6", "LV. 7", "LV. 8"};
            difficultyComboBox = new JComboBox<>(difficultyLevels);
            contentPanel.add(new JLabel("Select Difficulty: "));
            contentPanel.add(difficultyComboBox);
            difficultyComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    level = difficultyComboBox.getSelectedIndex() + 1;
                    System.out.println("level set to " + level);
                }
            });

            // Create a Button for starting the game
            JButton startButton = new JButton("Start Game");
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("level is " + level);
                    challengeAIController.execute(color, level);

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
        if (evt.getPropertyName().equals("ChallengeAI")){
            ChallengeAIState state = (ChallengeAIState) evt.getNewValue();
            gameID = state.getGameID();
            color2 = state.getColor();
            System.out.println("Game Started");
        }
    }
}
