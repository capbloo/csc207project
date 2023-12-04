package view;

import interface_adapter.challenge_ai.ChallengeAIViewModel;
import use_case.challenge_ai.ChallengeAIInteractor;
import use_case.challenge_ai.ChallengeAIOutputBoundary;

import javax.swing.*;

import java.awt.*;

import static org.junit.Assert.*;

public class ViewsTest {
    public JFrame getFrame() {
        JFrame app = null;

        Window[] windows = Window.getWindows();

        for (Window window : windows) {
            if (window instanceof JFrame) {
                app = (JFrame) window;
            }
        }

        assertNotNull(app);

        return app;
    }

    @org.junit.Test
    public void testRulesView() {
        JFrame frame = new JFrame();
        RulesView rulesView = new RulesView(frame);
        rulesView.show();

        sleep();

        assertTrue(frame.isVisible());

        rulesView.close();

        sleep();

        assertFalse(frame.isVisible());
    }

    @org.junit.Test
    public void testMainView() {
        MainMenuView mainMenuView = new MainMenuView();

        mainMenuView.show();

        sleep();

        JFrame frame = getFrame();

        // Validate that the frame is visible
        assertTrue(frame.isVisible());

        mainMenuView.close();
    }

    @org.junit.Test
    public void testNewGameView() {
        MainMenuView mainMenuView = new MainMenuView();

        mainMenuView.show();

        sleep();

        JFrame mainMenuFrame = getFrame();

        assertTrue(mainMenuFrame.isVisible());

        mainMenuView.close();

        sleep();

        assertFalse(mainMenuFrame.isVisible());

        NewGameOptionsView newGameOptionsView = new NewGameOptionsView(mainMenuFrame);

        newGameOptionsView.show();

        sleep();

        JFrame newGameFrame = getFrame();

        assertTrue(newGameFrame.isVisible());

        newGameOptionsView.close();

        sleep();

        assertFalse(newGameFrame.isVisible());
    }

    @org.junit.Test
    public void testPlayWithAIView() {
        MainMenuView mainMenuView = new MainMenuView();

        mainMenuView.show();

        sleep();

        JFrame mainMenuFrame = getFrame();

        assertTrue(mainMenuFrame.isVisible());

        mainMenuView.close();

        sleep();

        assertFalse(mainMenuFrame.isVisible());

        NewGameOptionsView newGameOptionsView = new NewGameOptionsView(mainMenuFrame);

        newGameOptionsView.show();

        sleep();

        JFrame newGameFrame = getFrame();

        assertTrue(newGameFrame.isVisible());

        newGameOptionsView.close();

        sleep();

        assertFalse(newGameFrame.isVisible());

        PracticeWithAIView practiceWithAIView = new PracticeWithAIView(newGameFrame, null, new ChallengeAIViewModel());

        practiceWithAIView.show();

        sleep();

        JFrame aiView = getFrame();

        assertTrue(aiView.isVisible());

        assertThrows(Exception.class, () -> {practiceWithAIView.getStartButton().doClick();});
    }

    private static void sleep() {
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}