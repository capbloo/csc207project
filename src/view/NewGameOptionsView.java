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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NewGameOptionsView implements MenuView {

    private JFrame frame;

    public NewGameOptionsView(JFrame frame) {
        this.frame = frame;
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
            // create a panel to store text and buttons
            JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            // Text message
            JLabel selectModeLabel = new JLabel("Select Mode");
            selectModeLabel.setFont(new Font("Bell MT", Font.BOLD, 20));
            selectModeLabel.setHorizontalAlignment(JLabel.LEFT);
            contentPanel.add(selectModeLabel);
            // play with AI button
            JButton practiceWithAIButton = new JButton("Play with AI");
            practiceWithAIButton.setPreferredSize(new Dimension(150, 50));

            ChallengeAIViewModel challengeAIViewModel = new ChallengeAIViewModel();
            ChallengeAIPresenter challengeAIPresenter = new ChallengeAIPresenter(challengeAIViewModel);
            APIChallengeDataAccessObject apiChallengeDataAccessObject = new APIChallengeDataAccessObject();
            APIEventStreamDataAccessObject apiEventStreamDataAccessObject = new APIEventStreamDataAccessObject();

            ChallengeAIInteractor challengeAIInteractor = new ChallengeAIInteractor(challengeAIPresenter, apiChallengeDataAccessObject, apiEventStreamDataAccessObject);
            ChallengeAIController challengeAIController = new ChallengeAIController(challengeAIInteractor);

            practiceWithAIButton.addActionListener(e -> new PracticeWithAIView(frame, challengeAIController, challengeAIViewModel).show());
            contentPanel.add(practiceWithAIButton);
            // play with player button
            JButton challengeWithRealPlayerButton = new JButton("Play with Player");
            challengeWithRealPlayerButton.setPreferredSize(new Dimension(150, 50));

            ChallengePlayerViewModel challengePlayerViewModel = new ChallengePlayerViewModel();
            ChallengePlayerPresenter challengePlayerPresenter = new ChallengePlayerPresenter(challengePlayerViewModel);
            ChallengePlayerInteractor challengePlayerInteractor = new ChallengePlayerInteractor(challengePlayerPresenter, apiChallengeDataAccessObject, apiEventStreamDataAccessObject);
            ChallengePlayerController challengePlayerController = new ChallengePlayerController(challengePlayerInteractor);


            challengeWithRealPlayerButton.addActionListener(e -> new ChallengeWithRealPlayerView(frame, challengePlayerController, challengePlayerViewModel).show());
            contentPanel.add(challengeWithRealPlayerButton);

            JButton goBackButton = new JButton("Main Menu");
            goBackButton.setPreferredSize(new Dimension(150, 50));
            goBackButton.addActionListener(e -> {
                close();
                new MainMenuView().show();
            });

            contentPanel.add(goBackButton);

//            contentPanel.setPreferredSize(new Dimension(500, 200));

            panel.add(contentPanel, BorderLayout.SOUTH);

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
