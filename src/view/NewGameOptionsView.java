// NewGameOptionsView.java
package view;

import data_access.APIChallengeDataAccessObject;
import data_access.APIEventStreamDataAccessObject;
import interface_adapter.challenge_ai.ChallengeAIController;
import interface_adapter.challenge_ai.ChallengeAIPresenter;
import interface_adapter.challenge_ai.ChallengeAIViewModel;
import interface_adapter.challenge_player.ChallengePlayerController;
import interface_adapter.challenge_player.ChallengePlayerPresenter;
import interface_adapter.challenge_player.ChallengePlayerState;
import interface_adapter.challenge_player.ChallengePlayerViewModel;
import use_case.challenge_ai.ChallengeAIInteractor;
import use_case.challenge_player.ChallengePlayerInteractor;

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

            ChallengeAIViewModel challengeAIViewModel = new ChallengeAIViewModel();
            ChallengeAIPresenter challengeAIPresenter = new ChallengeAIPresenter(challengeAIViewModel);
            APIChallengeDataAccessObject apiChallengeDataAccessObject = new APIChallengeDataAccessObject();
            APIEventStreamDataAccessObject apiEventStreamDataAccessObject = new APIEventStreamDataAccessObject();

            ChallengeAIInteractor challengeAIInteractor = new ChallengeAIInteractor(challengeAIPresenter, apiChallengeDataAccessObject, apiEventStreamDataAccessObject);
            ChallengeAIController challengeAIController = new ChallengeAIController(challengeAIInteractor);

            practiceWithAIButton.addActionListener(e -> new PracticeWithAIView(frame, challengeAIController, challengeAIViewModel).show());
            buttonsPanel.add(practiceWithAIButton);

            JButton challengeWithRealPlayerButton = new JButton("Challenge with Real Player");
            challengeWithRealPlayerButton.setPreferredSize(new Dimension(200, 50));

            ChallengePlayerViewModel challengePlayerViewModel = new ChallengePlayerViewModel();
            ChallengePlayerPresenter challengePlayerPresenter = new ChallengePlayerPresenter(challengePlayerViewModel);
            ChallengePlayerInteractor challengePlayerInteractor = new ChallengePlayerInteractor(challengePlayerPresenter, apiChallengeDataAccessObject, apiEventStreamDataAccessObject);
            ChallengePlayerController challengePlayerController = new ChallengePlayerController(challengePlayerInteractor);


            challengeWithRealPlayerButton.addActionListener(e -> new ChallengeWithRealPlayerView(frame, challengePlayerController, challengePlayerViewModel).show());
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
